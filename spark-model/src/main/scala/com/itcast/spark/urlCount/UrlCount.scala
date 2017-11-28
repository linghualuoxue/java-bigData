package com.itcast.spark.urlCount

import java.net.URL

import org.apache.spark.{Partitioner, SparkConf, SparkContext}

import scala.collection.mutable

/**
  * Created by Administrator on 2017/2/20.
  */
class UrlCount {

}
object UrlCount{
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("urlCount").setMaster("local[2]")
    val sc = new SparkContext(conf)

    //切分
    val rdd1 = sc.textFile("C:\\itcast.log").map(line =>{
      val f = line.split("\t")
      (f(1),1)
    })
    val rdd2 = rdd1.reduceByKey(_+_)
    val rdd3 = rdd2.map(it =>{
      val host = new URL(it._1).getHost
      (host,(it._1,it._2))
    })
    val ins = rdd3.map(_._1).distinct().collect()

    val rdd4 =rdd3.partitionBy(new MyPartitioner(ins)).mapPartitions(it =>{
      it.toList.sortBy(_._2._2).reverse.take(2).toIterator
    })
    rdd4.saveAsTextFile("C:\\urlCountOut")
    //println(rdd3.collect().toBuffer)
    sc.stop()
  }
}

class MyPartitioner(ins : Array[String])extends Partitioner{
  val parMap = new mutable.HashMap[String,Int]()
  var count=0
  
  for (i <- ins){
    parMap += (i -> count)
    count += 1
  }
  override def numPartitions: Int = ins.length
  override def getPartition(key: Any): Int = {
    parMap.getOrElse(key.toString,0)
  }
}
