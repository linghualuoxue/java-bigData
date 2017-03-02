package com.day3

import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by Administrator on 2017/3/1.
  */
object StreamingWordCount {
  def main(args: Array[String]): Unit = {
    LoggerLevels.setStreamingLogLevels()
    val conf = new SparkConf().setAppName("streamingWordCount").setMaster("local[2]")
    val ssc = new StreamingContext(new SparkContext(conf),Seconds(5))
    val ds = ssc.socketTextStream("64centos2",8888,StorageLevel.MEMORY_AND_DISK)
    val result = ds.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)
    result.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
