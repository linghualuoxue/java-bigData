package com.bj.hadoop.test1;


import com.sun.deploy.uitoolkit.impl.text.TextWindow;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by user on 2017/4/7.
 */
public class HelloMap extends Mapper<LongWritable,Text,Text,IntWritable>{

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);//运行map前运行一次
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] words = line.split(" ");
        for (String word : words) {
            context.write(new Text(word),new IntWritable(1));
        }
    }
}
