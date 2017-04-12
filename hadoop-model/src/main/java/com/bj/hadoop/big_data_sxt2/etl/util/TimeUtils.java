package com.bj.hadoop.big_data_sxt2.etl.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by user on 2017/4/12.
 */
public class TimeUtils {
    public static Long parseNginxTime2Long(String nginxTime) {
       Date date =  pargeNginxTime2Date(nginxTime);
        return date==null?-1L:date.getTime();
    }

    private static Date pargeNginxTime2Date(String nginxTime) {
        long value = Double.valueOf(Double.valueOf(nginxTime.trim()) * 1000).longValue();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(value);
        return calendar.getTime();
    }
}
