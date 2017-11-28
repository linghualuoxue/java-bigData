package com.bj.actor
import scala.actors.Actor
/**
  * Created by Administrator on 2017/2/7.
  */
object MyActor1 extends Actor{
  override def act(): Unit = {
    for(i <- 1 to 10){
      println("actor-1:"+i)
      Thread.sleep(1000)
    }
  }
}

object MyActor2 extends Actor{
  override def act(): Unit = {
    for(i <- 1 to 10){
      println("actor-2:"+i)
      Thread.sleep(1000)
    }
  }
}
object ActorTest{
  def main(args: Array[String]): Unit = {
    MyActor1.start()
    MyActor2.start()
  }

}
