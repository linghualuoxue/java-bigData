package com.bj.sxt.com.bj.sxt.util;

import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;
import cz.mallat.uasparser.UserAgentInfo;

import java.io.IOException;

/**
 * Created by user on 2017/1/9.
 */
public class UserAgentUtil {

    private static UASparser uASparser = null;
    static {
        try {
            uASparser = new UASparser(OnlineUpdater.getVendoredInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static UserAgentInfo anlysisAgentUser(String browserInfo) {

        UserAgentInfo result = null;
        if(browserInfo!=null && !browserInfo.trim().isEmpty()){
            try {
                cz.mallat.uasparser.UserAgentInfo info = uASparser.parse(browserInfo);
                result.setBrowserName(info.getUaFamily());
                result.setBrowserVersion(info.getBrowserVersionInfo());
                result.setOsName(info.getOsFamily());
                result.setOsVersion(info.getOsName());
            } catch (IOException e) {
                result = null;
            }
        }
        return result;
    }

    public static class UserAgentInfo{
        //浏览器名称
        private String browserName;
        //浏览器版本
        private String browserVersion;
        //操作系统名称
        private String osName;
        //操作系统版本
        private String osVersion;

        public String getBrowserName() {
            return browserName;
        }

        public void setBrowserName(String browserName) {
            this.browserName = browserName;
        }

        public String getBrowserVersion() {
            return browserVersion;
        }

        public void setBrowserVersion(String browserVersion) {
            this.browserVersion = browserVersion;
        }

        public String getOsName() {
            return osName;
        }

        public void setOsName(String osName) {
            this.osName = osName;
        }

        public String getOsVersion() {
            return osVersion;
        }

        public void setOsVersion(String osVersion) {
            this.osVersion = osVersion;
        }
    }
}
