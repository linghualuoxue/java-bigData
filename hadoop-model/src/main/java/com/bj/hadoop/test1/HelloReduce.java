package com.bj.hadoop.test1;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by user on 2017/4/11.
 */
public class HelloReduce extends Reducer<Text,IntWritable,Text,IntWritable> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        Iterator<IntWritable> iterator = values.iterator();
        int i = 0;
        for (IntWritable value : values) {
            i+=value.get();
        }
        context.write(key,new IntWritable(i));
    }
}
