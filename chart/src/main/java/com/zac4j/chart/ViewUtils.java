package com.zac4j.chart;

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

}