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
    //日新增用户 Daily new Users 简写dnu
    val dnu = filteredLogs.filter(file => FilterUtils.filterByType(file,EventType.REGISTER)).count()

    //日活跃用户 Daily Active Users
    val adu = filteredLogs.filter(file => FilterUtils.filterByType(file,EventType.REGISTER,EventType.LOGIN))
      .map(_(3))
        .distinct()
      .count()
    //次日留存  第一天注册的用户第二天还登录 这部分用户占第一天注册用户的比例为次日留存率
    val yesterday = TimeUtils.getLongTime(-1)
    val yesterdayRegisterUser = splitedLogs.filter(file => FilterUtils.filterByTypeAndTime(file,EventType.REGISTER,yesterday,beginTime))
      .map(x => (x(3),1))
    val todayLoginUser= filteredLogs.filter(file => FilterUtils.filterByType(file,EventType.LOGIN))
      .map(x => (x(3),1)).distinct()

    val dlr:Double = yesterdayRegisterUser.join(todayLoginUser).count()
    val dlrr = dlr / yesterdayRegisterUser.count()

    println(s"日新增用户:${dnu}")
    println(s"日活跃用户:${adu}")
    println("次日留存率:"+dlrr)
    sc.stop()

  }
}
