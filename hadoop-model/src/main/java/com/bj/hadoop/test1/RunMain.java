package com.bj.hadoop.test1;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by user on 2017/4/11.
 */
public class RunMain {
    public static void main(String[] args) {
        try {
            if(args.length!=2){
                System.out.printf("参数应该为两个");
                System.exit(-1);
            }
            Job job = Job.getInstance();
            job.setJobName("word count");

            FileInputFormat.addInputPath(job,new Path(args[0]));
            FileOutputFormat.setOutputPath(job,new Path(args[1]));

            job.setMapperClass(HelloMap.class);
            job.setReducerClass(HelloReduce.class);

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);
            job.waitForCompletion(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
