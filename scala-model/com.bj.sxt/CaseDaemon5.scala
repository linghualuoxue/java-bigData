package com.bj.sxt

/**
  * Created by Administrator on 2017/2/7.
  */
object CaseDaemon5 extends App{
  val map = Map("a" -> 1,"b" -> 2)
  val v = map.get("a") match{
    case Some(i) => i
    case None => 0
  }
 print(v)
  val v1 = map.getOrElse("c",0)
  println(v1)
}
