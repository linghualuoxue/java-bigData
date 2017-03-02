package com.bj.sxt.ipLocation

import java.sql.{Connection, Date, DriverManager, PreparedStatement}

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2017/2/21.
  */
object IpLocation {

  val data2Mysql = (iterator:Iterator[(String,Int)]) =>{
    var connect:Connection = null
    var ps :PreparedStatement = null
    val sql ="insert into location_info(location,counts,access_date) values (?,?,?)"
    try {
      connect = DriverManager.getConnection("jdbc:mysql://64centos3:3306/bigdata", "root", "123")
      iterator.foreach(line => {
        ps = connect.prepareStatement(sql)
        ps.setString(1, line._1)
        ps.setInt(2, line._2)
        ps.setDate(3, new Date(System.currentTimeMillis()))
        ps.executeUpdate()
      })
    } catch {
      case e:Exception => println("mysql exception")
    }finally {
      if(ps!=null)ps.close()
      if(connect!=null)connect.close()
    }
  }
  def ip2Long(ip:String):Long={
    val fragments = ip.split("[.]")
    var ipNum =0L
    for(i <- 0 until fragments.length){
      ipNum = fragments(i).toLong | ipNum << 8L
    }
    ipNum
  }
  def binarySearch(lines:Array[(String,String,String)],ip:Long):Int={
    var low = 0
    var high = lines.length-1
    while(low <= high){
      var middle = (low + high)/2
      if((ip >= lines(middle)._1.toLong) && (ip <= lines(middle)._2.toLong)){
       return middle
      }else if(ip < lines(middle)._1.toLong){
       high = middle -1
      }else{
       low = middle + 1
      }
    }
    -1
  }

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("ipLocation").setMaster("local[2]")
    val sc = new SparkContext(conf)

    val ipRulesRdd = sc.textFile("C:\\ip.txt").map(line =>{
      val fields = line.split("\\|")
      val start_num = fields(2)
      val end_num = fields(3)
      val province = fields(6)
      (start_num,end_num,province)
    })
    val ipRulesArr = ipRulesRdd.collect()           //需要进行action操作返回到driver中然后才能广播变量
    val ipRulesBroadcast = sc.broadcast(ipRulesArr) //广播变量

    val ipsRdd = sc.textFile("C:\\access_log.format").map(line =>{
      val fields = line.split("\\|")
      (fields(1))
     })

     val result = ipsRdd.map(ip =>{
       val ipsLong = ip2Long(ip)
       val index = binarySearch(ipRulesBroadcast.value,ipsLong)
       val info = ipRulesBroadcast.value(index)
       info
     }).map(t => (t._3,1)).reduceByKey(_+_)
    result.foreachPartition(data2Mysql(_))
   /* print(result.collect().toBuffer)*/
    sc.stop()
  }
}

