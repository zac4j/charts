package com.zac4j.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.zac4j.chart.LineChartView;
import com.zac4j.chart.algs.SortByDate;
import com.zac4j.chart.model.Stock;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by zac on 2018/7/31
 * Email:zac_ju@163.com
 * Description:Line chart ui
 */
public class LineChartActivity extends AppCompatActivity {

    private static final String TAG = "LineChartActivity";

    private LineChartView mLineChartView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        mLineChartView = findViewById(R.id.main_line_chart);
        setDatum();
    }

    private void setDatum() {
        String dataStr = "1-May-12,58.13;"
            + "30-Apr-12,53.98;"
            + "27-Apr-12,67.00;"
            + "26-Apr-12,89.70;"
            + "25-Apr-12,99.00;"
            + "24-Apr-12,130.28;"
            + "23-Apr-12,166.70;"
            + "20-Apr-12,234.98;"
            + "19-Apr-12,345.44;"
            + "18-Apr-12,443.34;"
            + "17-Apr-12,543.70;"
            + "16-Apr-12,580.13;"
            + "13-Apr-12,605.23;"
            + "12-Apr-12,622.77;"
            + "11-Apr-12,626.20;"
            + "10-Apr-12,628.44;"
            + "9-Apr-12,636.23;"
            + "5-Apr-12,633.68;"
            + "4-Apr-12,624.31;"
            + "3-Apr-12,629.32;"
            + "2-Apr-12,618.63;"
            + "30-Mar-12,599.55;"
            + "29-Mar-12,609.86;"
            + "28-Mar-12,617.62;"
            + "27-Mar-12,614.48;"
            + "26-Mar-12,606.98";

        List<Stock> stocks = new ArrayList<>();
        String[] data = dataStr.split(";");
        DateFormat format = new SimpleDateFormat("d-MMM-yy", Locale.US);
        for (String datumStr : data) {
            String[] datum = datumStr.split(",");
            if (datum.length == 2) {
                try {
                    Date date = format.parse(datum[0]);
                    Double price = Double.valueOf(datum[1]);
                    Stock stock = new Stock(date, price);
                    stocks.add(stock);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.e(TAG, "there is an error in data operation: " + e.getMessage());
                }
            }
        }


        if (stocks.size() == 0) {
            return;
        }

        Collections.sort(stocks, new SortByDate());
        mLineChartView.setDatum(stocks);
    }
}
