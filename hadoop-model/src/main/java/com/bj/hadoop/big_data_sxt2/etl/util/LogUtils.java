package com.bj.hadoop.big_data_sxt2.etl.util;


import com.bj.hadoop.big_data_sxt2.common.LogCommon;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2017/4/12.
 */
public class LogUtils {
    public static Map<String,String> handlelog(String line) {
         Map<String,String> map = new HashMap<String,String>();
        if(StringUtils.isNotEmpty(line)){
            String[] split = line.trim().split(LogCommon.LOG_SEPARTIOR);
            // 日志格式为: ip^A服务器时间^Ahost^A请求参数
            if(split.length==4){
                 //设置ip
                map.put(LogCommon.LOG_COLUMN_NAME_IP,split[0].trim());
                //设置服务器时间
                map.put(LogCommon.LOG_COLUMN_NAME_SERVER_TIME,String.valueOf(TimeUtils.parseNginxTime2Long(split[1].trim())));
                int index = split[3].indexOf("?");
                if(index>-1){
                    String requestBody = split[3].substring(index + 1);
                    //处理请求参数
                    handleRequesBody(map,requestBody);
                    //处理用户userAgent
                    handleUserAgeng(map);
                    //处理ip
                    handleIp(map);
                }else{
                    map.clear();
                }
            }

        }
        return map;
    }

    private static void handleRequesBody(Map<String, String> map, String requestBody) {

        if(StringUtils.isNotEmpty(requestBody)){

        }

    }
    public static void  handleUserAgeng(Map<String, String> map){
        if(map.containsKey(LogCommon.LOG_COLUMN_NAME_USER_AGENT)){
            UserAgentInfo info = UserAgentUtil.analycUserAgent(map.get(LogCommon.LOG_COLUMN_NAME_USER_AGENT));
            if(info!=null){

            }
        }

    }
    public static void  handleIp(Map<String, String> map){

    }
}
