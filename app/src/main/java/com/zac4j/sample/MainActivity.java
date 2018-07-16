package com.zac4j.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.zac4j.chart.BarChartView;
import com.zac4j.chart.model.Bar;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.zac4j.sample.Constant.ALPHABET;

public class MainActivity extends AppCompatActivity {

    private BarChartView mBarChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBarChartView = findViewById(R.id.main_barchart);
        setupData();
    }

    private void setupData() {
        List<Bar> data = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 26; i++) {
            float percentage = random.nextInt(100) * 0.01f;
            Bar bar = new Bar(ALPHABET[i], percentage);
            data.add(bar);
        }
        mBarChartView.setBarData(data);
    }
}
