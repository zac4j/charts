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

    private static final String PERCENTAGE_PERFECT = "100.00%";

    private Paint mBarPaint;
    private Paint mAxisPaint;
    private Paint mGuidelinePaint;
    private Paint mXAxisTextPaint;
    private Paint mYAxisTextPaint;

    private float mPadding;
    private float mBarGap;
    private float mXAxisLabelSize;
    private float mYAxisLabelSize;
    private float mLabelGap;
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
        mPadding = a.getDimensionPixelSize(R.styleable.BarChartView_android_padding, 4);
        mBarGap = a.getDimensionPixelSize(R.styleable.BarChartView_barGap, 8);
        mLabelGap = a.getDimensionPixelSize(R.styleable.BarChartView_labelGap, 4);
        mXAxisLabelSize = a.getDimension(R.styleable.BarChartView_xAxisLabelSize, spToPx(12));
        mYAxisLabelSize = a.getDimension(R.styleable.BarChartView_yAxisLabelSize, spToPx(12));

        a.recycle();

        createPaints();
    }

    private void createPaints() {
        mBarPaint = new Paint();
        mBarPaint.setStyle(Paint.Style.FILL);
        mBarPaint.setColor(Color.CYAN);

        mAxisPaint = new Paint();
        mAxisPaint.setStyle(Paint.Style.STROKE);
        mAxisPaint.setColor(Color.BLACK);
        mAxisPaint.setStrokeWidth(2);

        mGuidelinePaint = new Paint();
        mGuidelinePaint.setStyle(Paint.Style.STROKE);
        mGuidelinePaint.setColor(Color.LTGRAY);
        mGuidelinePaint.setStrokeWidth(2);

        mXAxisTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mXAxisTextPaint.setTextAlign(Paint.Align.LEFT);
        mXAxisTextPaint.setColor(Color.BLACK);
        mXAxisTextPaint.setTextSize(mXAxisLabelSize);

        mYAxisTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mYAxisTextPaint.setTextAlign(Paint.Align.LEFT);
        mYAxisTextPaint.setColor(Color.BLACK);
        mYAxisTextPaint.setTextSize(mYAxisLabelSize);
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

        final float startX = mPadding;
        final float stopX = width - mPadding;
        final float startY = mPadding;
        final float stopY = height - mPadding;

        final float gridLeft = startX + getYAxisLabelWidth() + mLabelGap;
        final float gridBottom = stopY - ViewUtils.getTextHeight(mXAxisTextPaint, "A");
        final float gridTop = startY;
        final float gridRight = stopX;

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
            canvas.drawText(formatter.format((10 - i) * 0.1f), startX,
                y + getYAxisLabelHeight() / 2.f, mYAxisTextPaint);
        }
        // draw 0
        canvas.drawText("0", getYAxisLabelWidth() - mYAxisLabelSize, gridBottom, mYAxisTextPaint);

        // Draw bar
        int barCount = mBarData.size();
        float totalBarGap = mBarGap * (barCount + 1);
        float barWidth = (gridRight - gridLeft - totalBarGap) / barCount;
        float barLeft = gridLeft + mBarGap;
        float barRight = barLeft + barWidth;
        float barBottom = gridBottom - mBarGap;
        for (int i = 0; i < barCount; i++) {
            // Calculate top of bar base on percentage
            Bar bar = mBarData.get(i);
            float top = gridTop + gridBottom * (1.0f - bar.getPercentage());
            top = top > barBottom ? barBottom : top;
            canvas.drawRect(barLeft, top, barRight, barBottom, mBarPaint);

            // Draw X axis labels
            float barCenter = (barLeft + barRight) / 2.f - mBarGap;
            float xAxisLabelBottom = stopY + mLabelGap;
            canvas.drawText(bar.getLetter(), barCenter, xAxisLabelBottom, mXAxisTextPaint);

            // Shift over left/right bar bounds.
            barLeft = barRight + mBarGap;
            barRight = barLeft + barWidth;
        }
    }

    private float getYAxisLabelWidth() {
        return ViewUtils.getTextWidth(mYAxisTextPaint, PERCENTAGE_PERFECT);
    }

    private float getYAxisLabelHeight() {
        return ViewUtils.getTextHeight(mYAxisTextPaint, PERCENTAGE_PERFECT);
    }
}
