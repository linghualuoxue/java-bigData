package com.bj.sxt.caseDaemon

import scala.util.Random


/**
  * Created by user on 2017/2/4.
  */
object CaseDaemon1 extends App{
  val arr = Array("a","b","c")
  var name = arr(Random.nextInt(arr.length))
  println(name)
  name match {
    case "a" => println("this is a")
    case "b" => println("this is b")
    case _ =>println("不知道是哪个")
  }


}
