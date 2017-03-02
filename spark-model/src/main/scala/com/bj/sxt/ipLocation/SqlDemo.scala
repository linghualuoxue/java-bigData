package com.bj.sxt.ipLocation

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2017/2/27.
  */
object SqlDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("sqlDemo").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val sqlContet = new SQLContext(sc)
    System.setProperty("user.name","root")
    val personRdd = sc.textFile("hdfs://64centos1:9000/person.txt").map(line =>{
      val f = line.split(",")
      Person(f(0).toLong,f(1),f(2).toInt)
    })
    import sqlContet.implicits._
    val df = personRdd.toDF
    //df.select("id","name").write.json("hdfs://64centos1:9000/json")
    df.registerTempTable("t_person")
    sqlContet.sql("select * from t_person order by age desc limit 2").show()
    sc.stop()
  }
}
case class Person(id:Long,name:String,age:Int)