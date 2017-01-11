package com.bj.sxt.com.bj.sxt.util;

import com.bj.sxt.common.EventLogConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/8.
 */
public class LoggerUtil {
    private final static Logger log = Logger.getLogger(LoggerUtil.class);
    private final static IPSeeker ipSeeker = new IPSeeker();
    public static Map<String,String> handleLog(String logText) {

        Map<String,String> clientInfo = new HashMap<String, String>();
        if(StringUtils.isNotBlank(logText)){
            String[] splits = logText.split(EventLogConstants.LOG_SEPARTION);
            if(splits.length ==4){
                //日志格式：ip^A服务器时间^Ahost^A请求参数
                clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_IP,StringUtils.trim(splits[0]));
                //设置服务器时间
                clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_SERVER_TIME,String.valueOf(TimeUtils.parseNginxTime2Long(StringUtils.trim(splits[1]))));
                int index = splits[3].indexOf("?");
                if(index>-1){

                    String requestBody = splits[3].substring(index + 1);
                    //处理请求参数
                    handleRequestBody(requestBody,clientInfo);
                    //处理useragent
                    handleUserAgent(clientInfo);
                    //处理ip
                    handleIp(clientInfo);
                }else{
                    //数据格式异常
                    clientInfo.clear();
                }
            }
        }
        return clientInfo;
    }

    private static void handleIp(Map<String, String> clientInfo) {
        if(clientInfo.containsKey(EventLogConstants.LOG_COLUMN_NAME_IP)){
            String ip = clientInfo.get(EventLogConstants.LOG_COLUMN_NAME_IP);
            RegionInfo region = IPSeeker.analysisIp(ip);
            if(region!=null) {
                clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_COUNTRY, region.getCountry());
                clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_CITY, region.getCity());
                clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_PROVINEC, region.getProvice());
            }
        }
    }

    /**
     * 处理浏览器的userAgent信息
     * @param clientInfo
     */
    private static void handleUserAgent(Map<String, String> clientInfo) {
         if(clientInfo.containsKey(EventLogConstants.LOG_COLUMN_NAME_USER_AGENT)){
             UserAgentUtil.UserAgentInfo userAgentInfo = UserAgentUtil.anlysisAgentUser(clientInfo.get(EventLogConstants.LOG_COLUMN_NAME_USER_AGENT));
             if(userAgentInfo!=null){
                clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_BROWSER_NAME,userAgentInfo.getBrowserName());
                 clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_BROWSER_VERSION,userAgentInfo.getBrowserVersion());
                 clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_OS_NAME,userAgentInfo.getOsName());
                 clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_OS_VERSION,userAgentInfo.getOsVersion());
             }
         }

    }

    private static void handleRequestBody(String requestBody, Map<String, String> clientInfo) {
       if(StringUtils.isNotBlank(requestBody)){
           String[] splits = requestBody.split("&");
           for (String split : splits) {
               if(StringUtils.isNotBlank(split)){
                   int index = split.indexOf("=");
                   if(index<0){
                        log.warn("不能解析参数："+split+",请求参数为："+requestBody);
                       continue;
                   }
                   String key = null,value = null;
                   try {
                       key = split.substring(0,index);
                       value = URLDecoder.decode(split.substring(index+1),"UTF-8");
                       if(StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)){
                           clientInfo.put(key,value);
                       }
                   } catch (UnsupportedEncodingException e) {
                       log.error("解码操作发生异常："+split+",请求参数为："+requestBody,e);
                   }
               }
           }
       }
    }
}
