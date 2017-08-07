package com.bj.xnbb.util;

import com.bj.xnbb.es_search.constant.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by hasee on 2017/7/25.
 */
public final class BasicConfiguration {

    private Properties pp;
    private BasicConfiguration() {
        readConfig();
    }



    private static  class BasicConfigurationHandler{
        private static final BasicConfiguration configuration = new BasicConfiguration();
    }

    public static BasicConfiguration getInstance(){
        return BasicConfigurationHandler.configuration;
    }

    public String getValue(String key){
        return pp.getProperty(key);
    }

    private void readConfig() {
        pp = new Properties();
        InputStream is = null;
        try {
            is = BasicConfiguration.class.getClassLoader().getResourceAsStream(Constant.PROPERTIES_NAME);
            pp.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
