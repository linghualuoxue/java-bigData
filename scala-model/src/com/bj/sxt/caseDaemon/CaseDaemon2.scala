package com.bj.sxt.caseDaemon

import scala.util.Random

/**
  * Created by user on 2017/2/4.
  */
object CaseDaemon2 extends App{

  val arr = Array("hello",1,2.0,CaseDaemon2)
  val name = arr(Random.nextInt(arr.length))
  println(name)
  name match {
    case x:Double => println("Double:"+x)
    case y:Int => println("Int:"+y)
    case z:String => println("String:"+z)
    case _ => throw new Exception("not match Exception")
  }


}
