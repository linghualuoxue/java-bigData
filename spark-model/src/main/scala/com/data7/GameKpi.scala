package com.data7

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by user on 2017/3/8.
  */
object GameKpi {
  def main(args: Array[String]): Unit = {
    val queryTime = "2016-02-02 00:00:00"
    val beginTime = TimeUtils(queryTime)
    val endTime = TimeUtils.getLongTime(+1)
    val conf = new SparkConf().setAppName("gameKpi").setMaster("local[2]")
    val sc = new SparkContext(conf)

    //切分日志
    val splitedLogs = sc.textFile("C://GameLog.txt").map(_.split("\\|"))
    //过滤后并缓存
    val filteredLogs = splitedLogs.filter(file => FilterUtils.filterByTime(file,beginTime,endTime))
      .cache()
    //日新增用户

  }
}
