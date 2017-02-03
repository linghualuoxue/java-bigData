package com.bj.cz

/**
  * Created by user on 2017/2/3.
  */
class People(val id:String,var name:String,size:Int,var age:Int =18) {


}
object People{
  def main (args: Array[String] ): Unit = {
   val people = new People("123","zhangsan",18)
    print("id:"+people.id+"、name:"+people.name+",age:"+people.age)
}
}
