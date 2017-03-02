package com.bj.sxt;

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
        pp.put("zk.connect","192.168.121.111:2181,192.168.121.112:2181192.168.121.113:2181");
        pp.put("metadata.broker.list","192.168.121.114:9092,192.168.121.115:9092,192.168.121.116:9092");
        pp.put("serializer.class","kafka.serializer.StringEncoder");
        ProducerConfig config = new ProducerConfig(pp);
        Producer<String,String> producer = new Producer<String, String>(config);
        for(int i=0;i<100;i++){
            producer.send(new KeyedMessage("spark","itcast"+i));
        }
    }
}
