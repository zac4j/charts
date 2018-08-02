package com.zac4j.chart.algs;

import com.zac4j.chart.model.Stock;
import java.util.Comparator;

/**
 * Created by Zaccc on 8/2/2018.
 * Email: zac_ju@163.com
 * Description:Chronologically sort algorithm.
 */
public class SortByDate implements Comparator<Stock> {

    @Override public int compare(Stock o1, Stock o2) {
        return (int) (o2.getDate().getTime() - o1.getDate().getTime());
    }
}
