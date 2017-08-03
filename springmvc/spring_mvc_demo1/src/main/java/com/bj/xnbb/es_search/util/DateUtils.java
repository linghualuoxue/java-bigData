package com.bj.xnbb.es_search.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hasee on 2017/7/31.
 */
public class DateUtils {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getDate(){
        return sdf.format(new Date());
    }


}
