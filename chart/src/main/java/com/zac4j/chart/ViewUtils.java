package com.zac4j.chart;

import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import java.util.List;
import java.util.Map;

/**
 * Created by Zaccc on 7/8/2018.
 * Email: zac_ju@163.com
 * Description:Utilities class for custom views.
 */
public class ViewUtils {

    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float dpToPx(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static int spToPx(float sp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, metrics);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @return A float value to represent dp equivalent to px value
     */
    public static float pxToDp(float px) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * This method get the width of given string of text.
     *
     * @param textPaint The paint object for string of text.
     * @param text The text for measure width.
     * @return the width of given text.
     */
    public static float getTextWidth(Paint textPaint, String text) {

        if (TextUtils.isEmpty(text)) {
            return 0.f;
        }

        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.width();
    }

    /**
     * This method get the height of given string of text.
     *
     * @param textPaint The paint object for string of text.
     * @param text The text for measure height.
     * @return the height of given text.
     */
    public static float getTextHeight(Paint textPaint, String text) {

        if (TextUtils.isEmpty(text)) {
            return 0.f;
        }

        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.height();
    }
}