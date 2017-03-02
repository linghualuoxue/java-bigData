package com.day3

import org.apache.spark.SparkConf
import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by Administrator on 2017/3/1.
  */
object FlumePushWordCount {
  def main(args: Array[String]): Unit = {
    LoggerLevels.setStreamingLogLevels()
    /*val host = args(0)
    val port = args(1).toInt*/
    val conf =  new SparkConf().setAppName("flumePushWordCount").setMaster("local[2]")
    val ssc = new StreamingContext(conf,Seconds(20))
    val ds = FlumeUtils.createStream(ssc,"192.168.121.102",8888)
    val word = ds.flatMap(x=>new String(x.event.getBody.array()).split("\t")).map((_,1))
    val result = word.reduceByKey(_+_)
    ds.print()
    println("---->------->---------> -------->---------------")
    //result.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
