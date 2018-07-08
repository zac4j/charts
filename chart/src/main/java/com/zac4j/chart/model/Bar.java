package com.zac4j.chart.model;

import com.zac4j.chart.BarChartView;
import java.util.Comparator;

/**
 * Created by Zaccc on 7/8/2018.
 * Email: zac_ju@163.com
 * Description:Data model for {@link BarChartView}
 */
public class Bar {
    private String letter;
    private float percentage;

    public Bar() {
    }

    public Bar(String letter, float percentage) {
        this.letter = letter;
        this.percentage = percentage;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }
}
