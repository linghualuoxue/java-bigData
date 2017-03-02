package com.day3

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by user on 2017/3/2.
  */
object WindowOpts {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("windowOpts").setMaster("local[2]")
    val ssc = new StreamingContext(conf,Seconds(5))
    val data = ssc.socketTextStream("64centos2",8888,StorageLevel.MEMORY_AND_DISK_SER_2)
    val lines = data.flatMap(_.split(" ")).map((_,1))
    val windowCount = lines.reduceByKeyAndWindow((a:Int,b:Int)=>a+b,Seconds(15),Seconds(10))
    windowCount.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
