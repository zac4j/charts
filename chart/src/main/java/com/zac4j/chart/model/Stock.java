package com.zac4j.chart.model;

import com.zac4j.chart.LineChartView;
import java.util.Date;

/**
 * Created by Zaccc on 8/2/2018.
 * Email: zac_ju@163.com
 * Description:Stock model for {@link LineChartView}
 */
public class Stock{

    private Date date;
    private Double price;

    public Stock(Date date, Double price) {
        this.date = date;
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
