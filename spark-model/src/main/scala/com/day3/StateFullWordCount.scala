package com.day3

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

/**
  * Created by Administrator on 2017/3/1.
  */
object StateFullWordCount {
  //updateFunc: (Iterator[(K, Seq[V], Option[S])]) => Iterator[(K, S)],
  val updateFunc =(iter:Iterator[(String, Seq[Int], Option[Int])] ) =>{
    //iter.map(t=>(t._1,t._2.sum+t._3.getOrElse(0)))
    iter.map{case (word,current_count,history_count) => (word,current_count.sum + history_count.getOrElse(0))}
  }

  def main(args: Array[String]): Unit = {
    LoggerLevels.setStreamingLogLevels()
    val conf = new SparkConf().setAppName("stateFullWordCount").setMaster("local[2]")
    val sc = new SparkContext(conf)
    sc.setCheckpointDir("C://stateFullWordCout")
    val ssc = new StreamingContext(sc,Seconds(5))
    val ds = ssc.socketTextStream("64centos2",8888,StorageLevel.MEMORY_AND_DISK)
    val result = ds.flatMap(_.split(" ")).map((_,1)).updateStateByKey(updateFunc,new HashPartitioner(sc.defaultParallelism),true)
    result.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
