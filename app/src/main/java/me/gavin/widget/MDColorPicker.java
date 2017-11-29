package me.gavin.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * MDColorPicker
 *
 * @author gavin.xiong 2017/11/29
 */
public class MDColorPicker extends View {

    private final int[] Red = {0xF44336, 0xFFEBEE, 0xFFCDD2, 0xEF9A9A, 0xE57373, 0xEF5350, 0xF44336, 0xE53935, 0xD32F2F, 0xC62828, 0xB71C1C, 0xFF8A80, 0xFF5252, 0xFF1744, 0xD50000};
    private final int[] Pink = {0xE91E63, 0xFCE4EC, 0xF8BBD0, 0xF48FB1, 0xF06292, 0xEC407A, 0xE91E63, 0xD81B60, 0xC2185B, 0xAD1457, 0x880E4F, 0xFF80AB, 0xFF4081, 0xF50057, 0xC51162};
    private final int[] Purple = {0x9C27B0, 0xF3E5F5, 0xE1BEE7, 0xCE93D8, 0xBA68C8, 0xAB47BC, 0x9C27B0, 0x8E24AA, 0x7B1FA2, 0x6A1B9A, 0x4A148C, 0xEA80FC, 0xE040FB, 0xD500F9, 0xAA00FF};
    private final int[] DeepPurple = {0x673AB7, 0xEDE7F6, 0xD1C4E9, 0xB39DDB, 0x9575CD, 0x7E57C2, 0x673AB7, 0x5E35B1, 0x512DA8, 0x4527A0, 0x311B92, 0xB388FF, 0x7C4DFF, 0x651FFF, 0x6200EA};
    private final int[] Indigo = {0x3F51B5, 0xE8EAF6, 0xC5CAE9, 0x9FA8DA, 0x7986CB, 0x5C6BC0, 0x3F51B5, 0x3949AB, 0x303F9F, 0x283593, 0x1A237E, 0x8C9EFF, 0x536DFE, 0x3D5AFE, 0x304FFE};
    private final int[] Blue = {0x2196F3, 0xE3F2FD, 0xBBDEFB, 0x90CAF9, 0x64B5F6, 0x42A5F5, 0x2196F3, 0x1E88E5, 0x1976D2, 0x1565C0, 0x0D47A1, 0x82B1FF, 0x448AFF, 0x2979FF, 0x2962FF};
    private final int[] LightBlue = {0x03A9F4, 0xE1F5FE, 0xB3E5FC, 0x81D4FA, 0x4FC3F7, 0x29B6F6, 0x03A9F4, 0x039BE5, 0x0288D1, 0x0277BD, 0x01579B, 0x80D8FF, 0x40C4FF, 0x00B0FF, 0x0091EA};
    private final int[] Cyan = {0x00BCD4, 0xE0F7FA, 0xB2EBF2, 0x80DEEA, 0x4DD0E1, 0x26C6DA, 0x00BCD4, 0x00ACC1, 0x0097A7, 0x00838F, 0x006064, 0x84FFFF, 0x18FFFF, 0x00E5FF, 0x00B8D4};
    private final int[] Teal = {0x009688, 0xE0F2F1, 0xB2DFDB, 0x80CBC4, 0x4DB6AC, 0x26A69A, 0x009688, 0x00897B, 0x00796B, 0x00695C, 0x004D40, 0xA7FFEB, 0x64FFDA, 0x1DE9B6, 0x00BFA5};
    private final int[] Green = {0x4CAF50, 0xE8F5E9, 0xC8E6C9, 0xA5D6A7, 0x81C784, 0x66BB6A, 0x4CAF50, 0x43A047, 0x388E3C, 0x2E7D32, 0x1B5E20, 0xB9F6CA, 0x69F0AE, 0x00E676, 0x00C853};
    private final int[] LightGreen = {0x8BC34A, 0xF1F8E9, 0xDCEDC8, 0xC5E1A5, 0xAED581, 0x9CCC65, 0x8BC34A, 0x7CB342, 0x689F38, 0x558B2F, 0x33691E, 0xCCFF90, 0xB2FF59, 0x76FF03, 0x64DD17};
    private final int[] Lime = {0xCDDC39, 0xF9FBE7, 0xF0F4C3, 0xE6EE9C, 0xDCE775, 0xD4E157, 0xCDDC39, 0xC0CA33, 0xAFB42B, 0x9E9D24, 0x827717, 0xF4FF81, 0xEEFF41, 0xC6FF00, 0xAEEA00};
    private final int[] Yellow = {0xFFEB3B, 0xFFFDE7, 0xFFF9C4, 0xFFF59D, 0xFFF176, 0xFFEE58, 0xFFEB3B, 0xFDD835, 0xFBC02D, 0xF9A825, 0xF57F17, 0xFFFF8D, 0xFFFF00, 0xFFEA00, 0xFFD600};
    private final int[] Amber = {0xFFC107, 0xFFF8E1, 0xFFECB3, 0xFFE082, 0xFFD54F, 0xFFCA28, 0xFFC107, 0xFFB300, 0xFFA000, 0xFF8F00, 0xFF6F00, 0xFFE57F, 0xFFD740, 0xFFC400, 0xFFAB00};
    private final int[] Orange = {0xFF9800, 0xFFF3E0, 0xFFE0B2, 0xFFCC80, 0xFFB74D, 0xFFA726, 0xFF9800, 0xFB8C00, 0xF57C00, 0xEF6C00, 0xE65100, 0xFFD180, 0xFFAB40, 0xFF9100, 0xFF6D00};
    private final int[] DeepOrange = {0xFF5722, 0xFBE9E7, 0xFFCCBC, 0xFFAB91, 0xFF8A65, 0xFF7043, 0xFF5722, 0xF4511E, 0xE64A19, 0xD84315, 0xBF360C, 0xFF9E80, 0xFF6E40, 0xFF3D00, 0xDD2C00};
    private final int[] Brown = {0x795548, 0xEFEBE9, 0xD7CCC8, 0xBCAAA4, 0xA1887F, 0x8D6E63, 0x795548, 0x6D4C41, 0x5D4037, 0x4E342E, 0x3E2723};
    private final int[] Grey = {0x9E9E9E, 0xFAFAFA, 0xF5F5F5, 0xEEEEEE, 0xE0E0E0, 0xBDBDBD, 0x9E9E9E, 0x757575, 0x616161, 0x424242, 0x212121};
    private final int[] BlueGrey = {0x607D8B, 0xECEFF1, 0xCFD8DC, 0xB0BEC5, 0x90A4AE, 0x78909C, 0x607D8B, 0x546E7A, 0x455A64, 0x37474F, 0x263238};
    private final int[] BlackWhite = {0x000000, 0xFFFFFF, 0x000000};

    private final int[][] Colors = {Red, Pink, Purple, DeepPurple, Indigo, Blue, LightBlue, Cyan, Teal, Green, LightGreen, Lime, Yellow, Amber, Orange, DeepOrange, Brown, Grey, BlueGrey};

    private final Paint mPaint;

    public MDColorPicker(Context context) {
        this(context, null);
    }

    public MDColorPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MDColorPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float cw = getWidth() / 15f;
        for (int i = 0; i < Colors.length; i++) {
            for (int j = 0; j < Colors[i].length; j++) {
                mPaint.setColor(Colors[i][j] | 0xFF000000);
                canvas.drawRect(cw * j, cw * i, cw * j + cw, cw * i + cw, mPaint);
            }
        }
//        float cw = getMeasuredWidth() / 2f / 14;
//        float ca = 360 / 16f;
//        mPaint.setStrokeWidth(cw);
//        for (int i = 0; i < 16; i++) {
//            for (int j = 0; j < 14; j++) {
//                mPaint.setColor(Colors[i][j == 0 ? 1 : j] | 0xFF000000);
//                float lt = cw * (j + 1);
//                canvas.drawArc(getMeasuredWidth() / 2f - lt + cw / 2f - 1,
//                        getMeasuredWidth() / 2f - lt + cw / 2f - 1,
//                        getMeasuredWidth() / 2f + lt - cw / 2f + 1,
//                        getMeasuredWidth() / 2f + lt - cw / 2f + 1,
//                        ca * i, ca + 1f, false, mPaint);
//            }
//        }
    }
}