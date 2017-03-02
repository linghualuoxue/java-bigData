package com.kafkaDemo;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * Created by Administrator on 2017/3/2.
 */
public class KafkaProducer {
    public static void main(String[] args) {
        Properties pp = new Properties();
        pp.put("zk.connect","64centos1:2181,64centos2:2181,64centos3:2181");
        pp.put("metadata.broker.list","64centos4:9092,64centos5:9092,64centos6");
        pp.put("serializer.class","kafka.serializer.StringEncoder");
        ProducerConfig config = new ProducerConfig(pp);
        Producer<String,String> producer = new Producer<String, String>(config);
        for(int i=0;i<100;i++){
            producer.send(new KeyedMessage("spark","itcast"+i));
        }
    }
}
