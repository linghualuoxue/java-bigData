package com.bj.cz

/**
  * Created by user on 2017/2/3.
  */
class Dog(var name:String) {
  var id:Int = _
  def this(id:Int,name:String){
    this(name)
    this.id = id
  }

}
object Dog{
  def main(args: Array[String]): Unit = {
    val dog = new Dog(123,"dog")
    print(dog.id+":"+dog.name)
  }
}
