package com.itcast.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2017/2/17.
  */
class UserLocation2 {

}
object UserLocation2{
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("userLocation").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val mbt = sc.textFile("C:\\bs_log").map(lines=>{
      val f = lines.split(",")
      val eventType = f(3)
      val time = f(1)
      val timeLone = if(eventType == "1") -time.toLong else time.toLong
      ((f(0),f(2)),timeLone)
    })
    val rdd1 = mbt.reduceByKey(_+_).map(it=>{
      (it._1._1,(it._1._2,it._2))
    }).groupByKey().mapValues(it=>{
      it.toList.sortBy(_._2).reverse.take(2)
    })
    println(rdd1.collect().toBuffer)
    sc.stop()
  }
}