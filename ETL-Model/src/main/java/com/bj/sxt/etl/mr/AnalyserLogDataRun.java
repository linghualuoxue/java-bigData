package com.bj.sxt.etl.mr;

import com.bj.sxt.common.EventLogConstants;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
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
    public int run(String[] args) throws Exception {
        Configuration conf =this.getConf();
        this.processArgs(conf,args);
        Job job = Job.getInstance(conf,"analyser_data");
        job.setJarByClass(AnalyserLogDataRun.class);
        job.setMapperClass(AnalyserLogDataMapper.class);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Put.class);
        TableMapReduceUtil.initTableReducerJob(EventLogConstants.HBASE_NAME_EVENT_LOGS,null,job,null,null,null,null,false);
        job.setNumReduceTasks(0);
        this.setJobInputPath(job);

        return job.waitForCompletion(true)?0:-1;
    }

    @Override
    public void setConf(Configuration conf) {
        conf.set("fs.defaultFS","http://64centos1:9000");
        conf.set("yarn.resourcemanager.hostname","64centos1");
        conf.set("hbase.zookeeper.quorum","64centos1,64centos2,64centos3");
        this.conf = HBaseConfiguration.create(conf);
    }

    @Override
    public Configuration getConf() {
        return this.conf;
    }
}
