package com.bj.cz

/**
  * Created by user on 2017/2/4.
  */
class PeopleHuman extends Human with Animal{
  override def eat(): Unit = {
      println("吃饭")
  }
}
object PeopleHuman extends App{
  val people = new PeopleHuman
  people.run()
  people.eat()
  people.drink()
}
