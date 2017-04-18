package com.bj.cz.web_click_mr_hive.itcast.hive.mr.pre;

import com.bj.cz.web_click_mr_hive.itcast.hive.mrbean.WeblogBean;
import com.bj.cz.web_click_mr_hive.itcast.hive.mrbean.WeblogParse;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/4/19.
 */
public class WeblogPreprocess extends Mapper<LongWritable,Text,Text,NullWritable>{

    Set<String> pages = new HashSet<String>();
    Text k = new Text();
    NullWritable v = NullWritable.get();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        pages.add("/about");
        pages.add("/black-ip-list/");
        pages.add("/cassandra-clustor/");
        pages.add("/finance-rhive-repurchase");
        pages.add("/hadoop-family-roadmap/");
        pages.add("/hadoop-hive-intro/");
        pages.add("/hadoop-zookeeper-intro/");
        pages.add("/hadoop-mahout-roadmap/");
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        WeblogBean weblogBean = WeblogParse.parse(line);
        //过滤js/图片/css等静态资源
        WeblogParse.filterStaticResource(weblogBean,pages);
        k.set(weblogBean.toString());
        context.write(k,v);
    }
}
