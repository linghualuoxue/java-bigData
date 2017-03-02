package com.bj.actor
import scala.actors.Actor

/**
  * Created by Administrator on 2017/2/7.
  */
class MyActor extends Actor{
  override def act(): Unit = {
    while(true){
      receive{
        case "start" => {
            println("starting....")
           Thread.sleep(5000)
          Thread.currentThread().getName
          println("started")
        }
        case  "stop" => {
           println("stoping...")
          Thread.sleep(5000)
          Thread.currentThread().getName
          println("stoped..")
        }
      }
    }
  }
}
class YourActor extends  Actor{
  override def act(): Unit = {
    loop{
      react{
    case "start" => {
       println("starting...")
      Thread.sleep(5000)
      Thread.currentThread().getName
      println("started")
      }
    case "stop" => {
      println("stoping...")
      Thread.sleep(5000)
      Thread.currentThread().getName
      println("stoped")
    }
    case "exit" => {
      println("exit..")
      exit()
    }
      }
  }
  }
}

object Boot{
  def main(args: Array[String]): Unit = {
/*    val actor = new MyActor
    actor.start()
    actor ! "start"
    actor ! "stop"
    println("消息发送完成！")*/

/*    val a2 = new YourActor
    a2.start()
    a2 ! "start"
    a2 ! "stop"
    println("消息发送完成")
    a2 ! "exit"*/


  }
}