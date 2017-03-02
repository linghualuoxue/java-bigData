package com.day3

import java.util

import org.apache.spark.{HashPartitioner, SparkConf}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by Administrator on 2017/3/2.
  */
class KafkaWordCount {

}
object KafkaWordCount{
  //updateFunc: (Iterator[(K, Seq[V], Option[S])]) => Iterator[(K, S)],
  val updateFunc = (iter:Iterator[(String,Seq[Int],Option[Int])]) =>{
    iter.map(x => (x._1,x._2.sum + x._3.getOrElse(0)))
    //ter.map(t=>(t._1,t._2.sum+t._3.getOrElse(0)))
  }
  def main(args: Array[String]): Unit = {
    LoggerLevels.setStreamingLogLevels()
    val zkAddr = "192.168.121.111:2181,192.168.121.112:2181,192.168.121.113:2181"
     val topics = "spark"
   val  numThreads = "2"
    val groupId = "gs"
    val conf = new SparkConf().setAppName("kafkaWordCount").setMaster("local[2]")
    val ssc = new StreamingContext(conf,Seconds(5))
    ssc.checkpoint("C://kafkaWordCount")
    val topMap = topics.split(",").map((_,numThreads.toInt)).toMap
    val data = KafkaUtils.createStream(ssc,zkAddr,groupId,topMap,StorageLevel.MEMORY_AND_DISK_SER_2)
    val words = data.map(_._2).flatMap(_.split(" "))
    val wordsCount = words.map((_,1)).updateStateByKey(updateFunc,new HashPartitioner(ssc.sparkContext.defaultParallelism),true)
    wordsCount.print()
    ssc.start()
    ssc.awaitTermination()
  }
}