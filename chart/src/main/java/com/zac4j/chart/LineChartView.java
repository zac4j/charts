package com.zac4j.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import static com.zac4j.chart.ViewUtils.spToPx;

/**
 * @author Zac
 *
 * This view describes a simple line chart.
 */
public class LineChartView extends View {

    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    private float mPadding;
    // Title of x axis labels.
    private String mXAxisTitle;
    // Title of y axis labels.
    private String mYAxisTitle;
    // The text size of text title for x axis.
    private float mXAxisTitleSize;
    // The text size of text title for y axis.
    private float mYAxisTitleSize;
    // The text size of text label for x axis.
    private float mXAxisLabelSize;
    // The text size of text label for y axis.
    private float mYAxisLabelSize;
    // The width of line's stroke.
    private float mLineWidth;
    // The color of line 's stroke.
    private int mLineColor = Color.DKGRAY;

    // Paints
    private Paint mAxisPaint;
    private Paint mLinePaint;
    private TextPaint mXAxisTextPaint;
    private TextPaint mYAxisTextPaint;

    public LineChartView(Context context) {
        super(context);
        init(null, 0);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a =
            getContext().obtainStyledAttributes(attrs, R.styleable.LineChartView, defStyle, 0);

        mExampleString = a.getString(R.styleable.LineChartView_exampleString);
        mExampleColor = a.getColor(R.styleable.LineChartView_exampleColor, mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension =
            a.getDimension(R.styleable.LineChartView_exampleDimension, mExampleDimension);

        if (a.hasValue(R.styleable.LineChartView_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(R.styleable.LineChartView_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();

        mPadding = a.getDimensionPixelSize(R.styleable.BarChartView_android_padding, 4);
        mXAxisTitle = a.getString(R.styleable.BarChartView_xAxisTitleSize);
        mYAxisTitle = a.getString(R.styleable.BarChartView_yAxisTitleSize);
        mXAxisTitleSize = a.getDimension(R.styleable.BarChartView_xAxisTitleSize, spToPx(12));
        mYAxisTitleSize = a.getDimension(R.styleable.BarChartView_yAxisTitleSize, spToPx(12));
        mXAxisLabelSize = a.getDimension(R.styleable.BarChartView_xAxisLabelSize, spToPx(12));
        mYAxisLabelSize = a.getDimension(R.styleable.BarChartView_yAxisLabelSize, spToPx(12));

        mLineColor = a.getColor(R.styleable.LineChartView_lineColor, mLineColor);
        mLineWidth = a.getDimension(R.styleable.LineChartView_lineWidth, mLineWidth);

        a.recycle();

        createChartPaints();
    }

    private void createChartPaints() {
        Paint mAxisPaint = new Paint();
        mAxisPaint.setStyle(Paint.Style.STROKE);
        mAxisPaint.setColor(Color.BLACK);
        mAxisPaint.setStrokeWidth(2);

        Paint mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(mLineWidth);
        mLinePaint.setColor(mLineColor);

        TextPaint mXAxisTextPaint = new TextPaint();

        TextPaint mYAxisTextPaint = new TextPaint();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mExampleDimension);
        mTextPaint.setColor(mExampleColor);
        mTextWidth = mTextPaint.measureText(mExampleString);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Draw the text.
        canvas.drawText(mExampleString, paddingLeft + (contentWidth - mTextWidth) / 2,
            paddingTop + (contentHeight + mTextHeight) / 2, mTextPaint);

        // Draw the example drawable on top of the text.
        if (mExampleDrawable != null) {
            mExampleDrawable.setBounds(paddingLeft, paddingTop, paddingLeft + contentWidth,
                paddingTop + contentHeight);
            mExampleDrawable.draw(canvas);
        }
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }
}
