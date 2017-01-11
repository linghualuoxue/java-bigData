package com.bj.sxt.etl.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

/**
 * Created by user on 2017/1/10.
 */
public class AnalyserLogDataRun implements Tool{

    private static final Logger log = Logger.getLogger(AnalyserLogDataRun.class);
    private Configuration conf = null;

    public static void main(String[] args) {
        try {
            ToolRunner.run(new Configuration(),new AnalyserLogDataRun(),args);
        } catch (Exception e) {
            log.error("执行日志解析异常",e);
            throw new RuntimeException(e);
        }
    }
    @Override
    public int run(String[] strings) throws Exception {
        return 0;
    }

    @Override
    public void setConf(Configuration configuration) {

    }

    @Override
    public Configuration getConf() {
        return null;
    }
}
