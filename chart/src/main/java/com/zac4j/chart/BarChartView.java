package com.zac4j.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import com.zac4j.chart.model.Bar;
import java.text.NumberFormat;
import java.util.List;

import static com.zac4j.chart.ViewUtils.isEmpty;
import static com.zac4j.chart.ViewUtils.spToPx;

/**
 * This view describes the bar chart, a two-axis chart with rectangular bars.
 */
public class BarChartView extends View {

    private Paint mBarPaint;
    private Paint mAxisPaint;
    private Paint mGuidelinePaint;
    private Paint mXAxisTextPaint;
    private Paint mYAxisTextPaint;

    private float mPadding;
    private float mBarGap;
    private float mBarCount;
    private float mXAxisLabelSize;
    private float mYAxisLabelSize;
    private List<Bar> mBarData;

    public BarChartView(Context context) {
        super(context);
        init(null, 0);
    }

    public BarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public BarChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a =
            getContext().obtainStyledAttributes(attrs, R.styleable.BarChartView, defStyle, 0);

        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mPadding = a.getDimension(R.styleable.BarChartView_android_padding, 4);
        mBarGap = a.getDimension(R.styleable.BarChartView_barGap, 8);
        mBarCount = a.getDimension(R.styleable.BarChartView_barCount, 6);
        mXAxisLabelSize = a.getDimension(R.styleable.BarChartView_xAxisLabelSize, spToPx(14));
        mYAxisLabelSize = a.getDimension(R.styleable.BarChartView_yAxisLabelSize, spToPx(14));

        a.recycle();

        createPaints();
    }

    private void createPaints() {
        mBarPaint = new Paint();
        mBarPaint.setStyle(Paint.Style.FILL);
        mBarPaint.setColor(Color.CYAN);

        mAxisPaint = new Paint();
        mAxisPaint.setStyle(Paint.Style.STROKE);
        mAxisPaint.setColor(Color.RED);
        mAxisPaint.setStrokeWidth(2);

        mGuidelinePaint = new Paint();
        mGuidelinePaint.setStyle(Paint.Style.STROKE);
        mGuidelinePaint.setColor(Color.BLACK);
        mGuidelinePaint.setStrokeWidth(2);

        mXAxisTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mXAxisTextPaint.setTextAlign(Paint.Align.LEFT);
        mXAxisTextPaint.setColor(Color.BLACK);
        mXAxisTextPaint.setTextSize(spToPx(16.f));

        mYAxisTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mYAxisTextPaint.setTextAlign(Paint.Align.LEFT);
        mYAxisTextPaint.setColor(Color.BLACK);
        mYAxisTextPaint.setTextSize(spToPx(16.f));
    }

    public void setBarData(List<Bar> barData) {
        mBarData = barData;
        invalidate();
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
        canvas.drawLine(gridLeft, gridBottom, gridRight, gridBottom, mAxisPaint);
        canvas.drawLine(gridLeft, gridBottom, gridLeft, gridTop, mAxisPaint);

        if (isEmpty(mBarData)) {
            return;
        }

        // Draw guideline
        float guidelineSpacing = (gridBottom - gridTop) / 10;
        float y;
        NumberFormat formatter = NumberFormat.getPercentInstance();
        formatter.setMinimumFractionDigits(2);
        for (int i = 0; i < 10; i++) {
            y = gridTop + i * guidelineSpacing;
            canvas.drawLine(gridLeft, y, gridRight, y, mGuidelinePaint);
            canvas.drawText(formatter.format(i * 10), gridLeft, y, mYAxisTextPaint);
        }

        // Draw bar
        float totalBarGap = mBarGap * (mBarCount + 1);
        float barWidth = (gridRight - gridLeft - totalBarGap) / mBarCount;
        float barLeft = gridLeft + mBarGap;
        float barRight = barLeft + barWidth;
        for (Bar bar : mBarData) {
            // Calculate top of bar base on percentage
            float top = gridTop + gridBottom * (1.f - bar.getPercentage());
            canvas.drawRect(barLeft, top, barRight, gridBottom, mBarPaint);

            // Shift over left/right bar bounds.
            barLeft = barRight + mBarGap;
            barRight = barLeft + barWidth;
        }
    }
}
