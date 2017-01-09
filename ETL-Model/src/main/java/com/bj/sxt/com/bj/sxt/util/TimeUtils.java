package com.bj.sxt.com.bj.sxt.util;


import org.apache.commons.lang.StringUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by user on 2017/1/9.
 */
public class TimeUtils {

    //将nginx服务器时间转化为时间戳，转化失败返回-1
    public static Long parseNginxTime2Long(String input) {
        Date date = parseNginxServerTime2Date(input);
        return date==null? -1L :date.getTime();
    }

    private static Date parseNginxServerTime2Date(String input) {
        if(StringUtils.isNotBlank(input)) {
            try {
                long longValue = Double.valueOf(Double.valueOf(StringUtils.trim(input)) * 1000).longValue();
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(longValue);
                return calendar.getTime();
            } catch (NumberFormatException e) {

            }
        }
        return null;
    }

}
