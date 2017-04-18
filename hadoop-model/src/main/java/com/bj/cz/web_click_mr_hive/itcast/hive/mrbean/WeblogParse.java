package com.bj.cz.web_click_mr_hive.itcast.hive.mrbean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Set;

/**
 * Created by Administrator on 2017/4/19.
 */
public class WeblogParse {

    public static SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss", Locale.US);
    public static SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
    //日志格式:194.237.142.21 - - [18/Sep/2013:06:49:18 +0000]
    // "GET /wp-content/uploads/2013/07/rstudio-git3.png HTTP/1.1" 304 0 "-" "Mozilla/4.0 (compatible;)"
    public static WeblogBean parse(String line) {
       WeblogBean weblogBean = new WeblogBean();
        String[] arr = line.split(" ");
        if(arr.length>11){
            weblogBean.setRemote_addr(arr[0]);
            weblogBean.setRemote_user(arr[1]);
            String time_local = formatDate(arr[3].substring(1));
            if(null == time_local)time_local="-invalid_time-";
            weblogBean.setTime_local(time_local);
            weblogBean.setRequest(arr[6]);
            weblogBean.setStatus(arr[8]);
            weblogBean.setBoty_bytes_sent(arr[9]);
            weblogBean.setHttp_referer(arr[10]);
            if(arr.length>12){
                StringBuilder sb = new StringBuilder();
                for(int i=11;i<arr.length;i++){
                    sb.append(arr[i]);
                }
             weblogBean.setHttp_user_agent(sb.toString());
            }else{
             weblogBean.setHttp_user_agent(arr[11]);
            }

            if(Integer.parseInt(weblogBean.getStatus())>=400)weblogBean.setValid(false);
            if("-invalid_time".equals(weblogBean.getTime_local()))weblogBean.setValid(false);
        }else weblogBean.setValid(false);;

        return weblogBean;
    }

    private static String formatDate(String time_local) {
        try {
            return df2.format(df1.parse(time_local));
        } catch (ParseException e) {
            return null;
        }
    }

    public static void filterStaticResource(WeblogBean weblogBean, Set<String> pages) {
        if(!pages.contains(weblogBean.getRequest())){
            weblogBean.setValid(false);
        }
    }
}
