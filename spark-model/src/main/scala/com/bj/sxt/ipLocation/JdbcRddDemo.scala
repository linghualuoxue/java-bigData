package com.bj.sxt.ipLocation

import java.sql.DriverManager

import org.apache.spark.rdd.JdbcRDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2017/2/27.
  */
class JdbcRddDemo {

}
object JdbcRddDemo{
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("jdbcRddDemo").setMaster("local[2]")
    val sc  = new SparkContext(conf)
    val connect =() => {
      Class.forName("com.mysql.jdbc.Driver").newInstance()
      DriverManager.getConnection("jdbc:mysql://64centos3:3306/bigdata","root","123")
    }
    val jdbcRdd = new JdbcRDD(
      sc,connect,
      "select * from location_info where id >= ? and id<= ?",
      1,4,2,
      r => {
        val id = r.getInt(4)
        val location = r.getString(2)
        (id,location)
      }
    )
    println(jdbcRdd.getPartitions.length)
    println(jdbcRdd.collect().toBuffer)
    sc.stop()


  }
}