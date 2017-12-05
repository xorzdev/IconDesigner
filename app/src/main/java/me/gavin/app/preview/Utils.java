package me.gavin.app.preview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import me.gavin.svg.model.SVG;

/**
 * Utils
 *
 * @author gavin.xiong 2017/12/5
 */
public class Utils {

    static Path getBgPath(int bgShape, int mSize, float bgCorner) {
        Path mBgPath = new Path();
        float half = mSize / 2f;
        float corner = half * bgCorner;
        if (bgShape == 0) {
            float hs = half * Icon.BG_M_RATIO;
            mBgPath.addRoundRect(half - hs, half - hs, half + hs, half + hs,
                    corner, corner, Path.Direction.CCW);
        } else if (bgShape == 1) {
            mBgPath.addCircle(half, half, half * Icon.BG_L_RATIO, Path.Direction.CCW);
        } else if (bgShape == 2) {
            float hhs = half * Icon.BG_L_RATIO;
            float hvs = half * Icon.BG_S_RATIO;
            mBgPath.addRoundRect(half - hhs, half - hvs, half + hhs, half + hvs,
                    corner, corner, Path.Direction.CCW);
        } else if (bgShape == 3) {
            float hhs = half * Icon.BG_S_RATIO;
            float hvs = half * Icon.BG_L_RATIO;
            mBgPath.addRoundRect(half - hhs, half - hvs, half + hhs, half + hvs,
                    corner, corner, Path.Direction.CCW);
        }
        return mBgPath;
    }

    static Bitmap SVGToBitmap(@NonNull SVG mSvg, int mSize, float iconScale) {
        Bitmap mIconBitmap = Bitmap.createBitmap(mSize, mSize, Bitmap.Config.ARGB_8888);
        Canvas iconCanvas = new Canvas(mIconBitmap);

        Matrix mMatrix = new Matrix();
        float mInherentScale = mSvg.getInherentScale();
        // 比例同步
        mMatrix.postScale(mInherentScale, mInherentScale);
        mMatrix.postTranslate((mSize - mSvg.width) / 2f, (mSize - mSvg.height) / 2f);
        float scale = Math.min(mSize / mSvg.width, mSize / mSvg.height);
        mMatrix.postScale(scale, scale, mSize / 2f, mSize / 2f);
        // 当前缩放比
        mMatrix.postScale(iconScale, iconScale, mSize / 2f, mSize / 2f);
        iconCanvas.setMatrix(mMatrix);

        if (mSvg.width / mSvg.height != mSvg.viewBox.width / mSvg.viewBox.height) {
            iconCanvas.translate(
                    mSvg.width / mSvg.height > mSvg.viewBox.width / mSvg.viewBox.height
                            ? (mSvg.width - mSvg.viewBox.width / mSvg.viewBox.height * mSvg.height) / 2 / mInherentScale : 0,
                    mSvg.width / mSvg.height > mSvg.viewBox.width / mSvg.viewBox.height
                            ? 0 : (mSvg.height - mSvg.viewBox.height / mSvg.viewBox.width * mSvg.width) / 2 / mInherentScale);
        }

        for (int i = 0; i < mSvg.paths.size(); i++) {
            if (mSvg.drawables.get(i).getFillPaint().getColor() != 0) {
                iconCanvas.drawPath(mSvg.paths.get(i), mSvg.drawables.get(i).getFillPaint());
            }
        }
        return mIconBitmap;
    }

    static Bitmap getShadow(@NonNull Bitmap mIconBitmap, int mSize, @NonNull Path mBgPath, boolean preview) {
        Bitmap mShadowBitmap = Bitmap.createBitmap(mSize, mSize, Bitmap.Config.ARGB_8888);
        Canvas shadowCanvas = new Canvas(mShadowBitmap);
        shadowCanvas.clipPath(mBgPath);
        Paint mIconPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mIconPaint.setStyle(Paint.Style.FILL);
        for (int i = 1; i <= mSize; i += preview ? 2 : 1) {
            shadowCanvas.drawBitmap(mIconBitmap, i, i, mIconPaint);
        }
        return mShadowBitmap;
    }

    static Path getScorePath(int mSize, @NonNull Path mBgPath) {
        Path mScorePath = new Path();
        mScorePath.addRect(0, 0, mSize, mSize / 2f, Path.Direction.CCW);
        mScorePath.op(mBgPath, Path.Op.INTERSECT);
        return mScorePath;
    }

    static Bitmap getBitmap(SVG mSvg, Icon mIcon, int size, Paint mBgPaint, Paint mShadowPaint, Paint mIconPaint, Paint mScorePaint) {
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // TODO: 2017/12/5 bgPaint layer
        Path mBgPath = Utils.getBgPath(mIcon.bgShape, size, mIcon.bgCorner);
        canvas.drawPath(mBgPath, mBgPaint);

        Bitmap mIconBitmap = Utils.SVGToBitmap(mSvg, size, mIcon.iconScale);
        Bitmap mShadowBitmap = Utils.getShadow(mIconBitmap, size, mBgPath, false);
        canvas.drawBitmap(mShadowBitmap, 0, 0, mShadowPaint);
        canvas.drawBitmap(mIconBitmap, 0, 0, mIconPaint);
        mIconBitmap.recycle();
        mShadowBitmap.recycle();

        if (mIcon.effectScore) {
            Path mScorePath = Utils.getScorePath(size, mBgPath);
            canvas.drawPath(mScorePath, mScorePaint);
        }

        return bitmap;
    }

    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        Bitmap bitmap;
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    static Bitmap drawable2Bitmap(Drawable drawable, int size, float scale, Path mBgPath) {
        Bitmap result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.clipPath(mBgPath);
        int a = (int) (size * scale / 2f);
        drawable.setBounds(size / 2 - a, size / 2 - a, size / 2 + a, size / 2 + a);
        drawable.draw(canvas);
        return result;
    }

    static Bitmap bitmap2Bitmap(Bitmap bitmap, int size, float iconScale, Path mBgPath) {
        Bitmap result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.clipPath(mBgPath);
        Matrix matrix = new Matrix();
        matrix.postTranslate((size - bitmap.getWidth()) / 2f, (size - bitmap.getHeight()) / 2f);
        float scale = size * 1f / Math.min(bitmap.getWidth(), bitmap.getHeight());
        matrix.postScale(scale, scale, size / 2f, size / 2f);
        matrix.postScale(iconScale, iconScale, size / 2f, size / 2f);
        canvas.drawBitmap(bitmap, matrix, new Paint());
        return result;
    }
}
