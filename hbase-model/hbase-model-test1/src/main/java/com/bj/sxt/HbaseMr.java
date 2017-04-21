package com.bj.sxt;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by user on 2017/4/21.
 */
public class HbaseMr {
    /**
     * hbase 配置
     */
    static Configuration conf=null;
    static {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum","64centos1,64centos2,64centos3");
        conf.set("hbase.zookeeper.property.clientPort","2181");
    }

    private static final String tableName="word";//表名
    private static final String colf = "content";//列族
    private static final String col = "info";//列
    private static final String tableName2 = "stat";//表名2

    public static void initTB(){
        HBaseAdmin admin=null;
        HTable table = null;
        try {
             admin= new HBaseAdmin(conf);
            //如果存在直接删除表
             if(admin.tableExists(tableName) || admin.tableExists(tableName2)){
                 System.out.printf("table alread exist");
                 admin.disableTable(tableName);
                 admin.deleteTable(tableName);
                 admin.disableTable(tableName2);
                 admin.deleteTable(tableName2);
             }
          //创建表
            HTableDescriptor desc = new HTableDescriptor(tableName);
            HColumnDescriptor family = new HColumnDescriptor(colf);
            desc.addFamily(family);
            admin.createTable(desc);

            HTableDescriptor descriptor = new HTableDescriptor(tableName2);
            HColumnDescriptor family2 = new HColumnDescriptor(colf);
            descriptor.addFamily(family2);
            admin.createTable(descriptor);
            //插入数据
            table= new HTable(conf, tableName);
            table.setAutoFlush(false);
            table.setWriteBufferSize(500);
            ArrayList<Put> puts = new ArrayList<Put>();
            Put  p1 = new Put(Bytes.toBytes("1"));
            p1.add(Bytes.toBytes(colf),Bytes.toBytes(col),Bytes.toBytes("The Apache Hadoop software library is a framework"));
            puts.add(p1);
            Put p2 = new Put(Bytes.toBytes("2"));
            p2.add(Bytes.toBytes(colf),Bytes.toBytes(col),Bytes.toBytes("The common utilities that support the other Hadoop modules"));
            puts.add(p2);
            Put p3 = new Put(Bytes.toBytes("3"));
            p3.add(Bytes.toBytes(colf),Bytes.toBytes(col),Bytes.toBytes("Hadoop by reading the documentation"));
            puts.add(p3);
            table.put(puts);
            table.flushCommits();
            puts.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(table!=null)table.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static class MyMapper extends TableMapper<Text,IntWritable> {
        private IntWritable one = new IntWritable(1);
        private Text text = new Text();
        @Override
        protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
            String line = Bytes.toString(value.getValue(Bytes.toBytes(colf), Bytes.toBytes(col)));
            String[] words = line.split(" ");
            for (String word : words) {
                text.set(word);
                context.write(text,one);
            }
        }
    }

    public static class MyReducer extends TableReducer<Text,IntWritable,ImmutableBytesWritable>{

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum=0;
            for (IntWritable value : values) {
                final int i = value.get();
                sum+=i;
            }
            Put put = new Put(key.toString().getBytes());
            put.add(Bytes.toBytes(colf),Bytes.toBytes(col),Bytes.toBytes(String.valueOf(sum)));
            context.write(new ImmutableBytesWritable(Bytes.toBytes(key.toString())),put);
        }

        public static void main(String[] args)throws Exception {
            conf.set("df.default.name","hdfs://master:9000/");
            conf.set("hadoop.job.ugi","root,root");
            conf.set("mapred.job.tracker","master:9001");
            //初始化表
            initTB();
            //创建job
            Job job = new Job(conf, "hbaseMr");
             job.setJarByClass(HbaseMr.class);

            //创建scan
            Scan scan = new Scan();
            scan.addColumn(Bytes.toBytes(colf),Bytes.toBytes(col));
            TableMapReduceUtil.initTableMapperJob(tableName,scan,MyMapper.class,Text.class,IntWritable.class,job);
            TableMapReduceUtil.initTableReducerJob(tableName2,MyReducer.class,job);
            job.waitForCompletion(true);
        }
    }

}
