package com.bj.sxt;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by user on 2017/1/4.
 */
public class LogDataReceive {
    private static final Logger log = Logger.getGlobal();
    private static final String ACCESSS_URL = "http://192.168.120.111/log.gif";
    private static final String PLAT_FORM = "java-server";
    private static final String SDK = "java";
    private static final String VERSION = "1";

    /**
     *
     * @param orderId 订单Id
     * @param memberId 会员Id
     * @return
     */
    public static boolean oneChangeSuccess(String orderId,String memberId) {

        try {
            if (isEmpty(orderId) || isEmpty(memberId)) {
                log.log(Level.WARNING, "订单Id或者会员Id不能为空！");
                return false;
            }
            Map<String,String> data = new HashMap<String,String>();
            data.put("pl",PLAT_FORM);
            data.put("sdk",SDK);
            data.put("version",VERSION);
            data.put("en","e_cs");
            data.put("u_mid",memberId);
            data.put("oid",orderId);
            data.put("c_time",String.valueOf(System.currentTimeMillis()));
            String url = buildUrl(data);
            SendDataMonitor.sendDataUrl(url);
            return true;
        } catch (Exception e) {
            log.log(Level.WARNING,"发送数据异常");
        }
        return false;
     }

    public static boolean oneChangeRefuse(String orderId,String memberId) {

        try {
            if (isEmpty(orderId) || isEmpty(memberId)) {
                log.log(Level.WARNING, "订单Id或者会员Id不能为空！");
                return false;
            }
            Map<String,String> data = new HashMap<String,String>();
            data.put("pl",PLAT_FORM);
            data.put("sdk",SDK);
            data.put("version",VERSION);
            data.put("en","e_cr");
            data.put("u_mid",memberId);
            data.put("oid",orderId);
            data.put("c_time",String.valueOf(System.currentTimeMillis()));
            String url = buildUrl(data);
            SendDataMonitor.sendDataUrl(url);
            return true;
        } catch (Exception e) {
            log.log(Level.WARNING,"发送数据异常");
        }
        return false;
    }
    private static String buildUrl(Map<String, String> data) {
        StringBuilder sb = new StringBuilder();
        sb.append(ACCESSS_URL).append("?");
        for (Map.Entry<String, String> entry : data.entrySet()) {
             if(isNotEmpty(entry.getKey()) && isNotEmpty(entry.getValue())){
                 sb.append(entry.getKey().trim()).append("=")
                         .append(entry.getValue().trim()).append("&");
             }
        }
        return sb.substring(0,sb.length()-1);

    }

    private static boolean isNotEmpty(String key) {
        return !isEmpty(key);
    }


    public static boolean isEmpty(String value){
          return value == null || value.trim().isEmpty();
      }
   }
