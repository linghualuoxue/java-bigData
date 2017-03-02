package com.bj.sxt;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on 2017/3/2.
 */
public class KafkaConsumer {
    public static void main(String[] args) {
        Properties pp = new Properties();
        pp.put("zookeeper.connect","64centos1:2181,192.168.121.112:2181,192.168.121.113:2181");
        pp.put("group.id","vvvv");
        pp.put("auto.offset.reset","smallest");
        ConsumerConfig conf = new ConsumerConfig(pp);
        kafka.javaapi.consumer.ConsumerConnector consumer = Consumer.createJavaConsumerConnector(conf);
        Map<String,Integer> topicCount = new HashMap<String,Integer>();
        topicCount.put("spark",2);
        Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumer.createMessageStreams(topicCount);
        List<KafkaStream<byte[], byte[]>> straeams = messageStreams.get("spark");
        for (final KafkaStream<byte[], byte[]> straeam : straeams) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (MessageAndMetadata<byte[], byte[]> messageAndMetadata : straeam) {
                        System.out.println(new String(messageAndMetadata.message()));
                    }
                }
            }).start();
        }
    }
}
