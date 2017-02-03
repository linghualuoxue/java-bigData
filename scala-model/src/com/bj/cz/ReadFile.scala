package com.bj.cz
import scala.io._

/**
  * Created by user on 2017/2/3.
  */
class ReadFile {

  val name = "zhagnsan"
  println(name)
  def sayHello: Unit ={
    println("sayHello:"+name)
  }
  try{
    val cont = Source.fromFile("C://HelloWorld.scala").mkString
  }catch {
    case e:Exception => e.printStackTrace()
  }finally {
     println("exception")
  }
  name.toUpperCase
}

  object ReadFile{
    def main(args: Array[String]): Unit = {
      val readFile = new ReadFile
      readFile.sayHello
    }
  }