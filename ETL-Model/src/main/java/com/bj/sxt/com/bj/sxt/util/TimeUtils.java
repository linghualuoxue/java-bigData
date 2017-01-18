package com.bj.sxt.com.bj.sxt.util;


import com.bj.sxt.common.DateEnum;
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

    public static int getDateInfo(long time, DateEnum type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        if(DateEnum.YEAR.equals(type)){
           return calendar.get(Calendar.YEAR);
        }else if(DateEnum.SEASON.equals(type)){
            int month = calendar.get(Calendar.MONTH) + 1;
            if(month%3==0){
                return month%3;
            }
            return month%3+1;
        }else if(DateEnum.){

        }

    }
}
