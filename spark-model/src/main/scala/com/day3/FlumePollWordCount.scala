package com.day3


import java.net.InetSocketAddress

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by Administrator on 2017/3/1.
  */
object FlumePollWordCount {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("flumePollWordCount").setMaster("local[2]")
    val ssc = new StreamingContext(new SparkContext(conf),Seconds(5))
    val address = Seq(new InetSocketAddress("64centos2",8888))
    val flumeStream = FlumeUtils.createPollingStream(ssc,address,StorageLevel.MEMORY_AND_DISK)
    flumeStream.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
