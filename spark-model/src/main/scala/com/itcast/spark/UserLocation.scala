package com.itcast.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2017/2/17.
  */
class UserLocation {

}
object UserLocation{
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("userLocation").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val mbt = sc.textFile("C:\\bs_log").map(lines=>{
      val f = lines.split(",")
      val p_c =f(0)+"_"+f(2)
      val eventType = f(3)
      val time = f(1)
      val timeLone = if(eventType == "1") -time.toLong else time.toLong
      (p_c,timeLone)
    })
    val rdd1 = mbt.groupBy(_._1).mapValues(_.foldLeft(0.toLong)(_+_._2)) //(18611132889_9F36407EAD0629FC166F14DDE7970F68,54000), (18688888888_9F36407EAD0629FC166F14DDE7970F68,51200),
   // val rdd1 = mbt.reduceByKey(_+_)
    val rdd2 = rdd1.map(it=>{
     val f = it._1.split("_")
     val mobile = f(0)
     val lac = f(1)
     (mobile,lac,it._2)
   })
    val rdd3 =rdd2.groupBy(_._1).mapValues(it=>{
      it.toList.sortBy(_._3).reverse.take(2)
    })
    println(rdd2.collect().toBuffer)
    sc.stop()
  }
}