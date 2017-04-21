package com.bj.sxt.caseDaemon.implic

/**
  * Created by user on 2017/2/13.
  */
class Boy(val name:String,var faceValue:Int) extends Comparable[Boy]{
  override def compareTo(o: Boy): Int ={
      this.faceValue - o.faceValue
  }
}

object Boy{
  def main(args: Array[String]): Unit = {
    val a1 = new Boy("zhangsan",99)
    val a2 = new Boy("lisi",80)
    val arr = Array(a1,a2)
    val sorted = arr.sortBy(x => x).reverse
    for(i <- sorted){
      print(i.name)
    }
  }
}