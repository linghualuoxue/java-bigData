package com.bj.sxt

import scala.util.Random

/**
  * Created by Administrator on 2017/2/7.
  */
case class SubmitTask(id:String,name:String)
case class HearBeat(time:Long)
case object CheckTimeOutTask
object CaseDaemon4 extends App{

  val arr = Array(CheckTimeOutTask,HearBeat(12333),SubmitTask("123","task-001"))
  arr(Random.nextInt(arr.length)) match {
    case SubmitTask(id,name) => println(s"$id,$name")
    case HearBeat(time) => println(s"$time")
    case CheckTimeOutTask => println("check")
  }
}
