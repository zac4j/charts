package com.zac4j.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import java.util.List;

/**
 * Created by zac on 2018/7/5.
 * Description:
 */
public class BarChart extends View {

    private Paint mBarPaint;
    private Paint mGridPaint;
    private Paint mGuidelinePaint;

    private int mPadding;
    private int mBarGap;
    private int mBarCount;
    private List<Integer> mBarData;

    public BarChart(Context context) {
        super(context);
    }

    public BarChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BarChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void createPaints() {
        mBarPaint = new Paint();
        mBarPaint.setStyle(Paint.Style.FILL);
        mBarPaint.setColor(Color.CYAN);

        mGridPaint = new Paint();
        mGridPaint.setStyle(Paint.Style.STROKE);
        mGridPaint.setColor(Color.RED);
        mGridPaint.setStrokeWidth(8);

        mGuidelinePaint = new Paint();
        mGuidelinePaint.setStyle(Paint.Style.STROKE);
        mGuidelinePaint.setColor(Color.BLACK);
        mGuidelinePaint.setStrokeWidth(8);
    }

    public void setBarData(List<Integer> barData) {
        mBarData = barData;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawAxesLine(canvas);
    }

    private void drawAxesLine(Canvas canvas) {
        final int height = getHeight();
        final int width = getWidth();
        final float gridLeft = mPadding;
        final float gridBottom = height - mPadding;
        final float gridTop = mPadding;
        final float gridRight = width - mPadding;

        // Draw X/Y Axis
        canvas.drawLine(gridLeft, gridBottom, gridRight, gridBottom, mGridPaint);
        canvas.drawLine(gridLeft, gridBottom, gridLeft, gridTop, mGridPaint);

        // Draw guideline
        float guidelineSpacing = (gridBottom - gridTop) / 10.f;
        float y;
        for (int i = 0; i < 10; i++) {
            y = gridTop + i * guidelineSpacing;
            canvas.drawLine(gridLeft, y, gridRight, y, mGuidelinePaint);
        }

        // Draw bar
        float totalBarGap = mBarGap * (mBarCount + 1);
        float barWidth = (gridRight - gridLeft - totalBarGap) / mBarCount;
        float barLeft = gridLeft + mBarGap;
        float barRight = barLeft + barWidth;
        for (float percentage : mBarData) {
            // Calculate top of bar base on percentage
            float top = gridTop + gridBottom * ( 1.f - percentage);
            canvas.drawRect(barLeft, top, barRight, gridBottom, mBarPaint);

            // Shift over left/right bar bounds.
            barLeft = barRight + mBarGap;
            barRight = barLeft + barWidth;
        }
    }
}
