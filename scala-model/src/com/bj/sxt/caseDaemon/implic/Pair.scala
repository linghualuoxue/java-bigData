package com.bj.sxt.caseDaemon.implic

/**
  * Created by user on 2017/2/13.
  */
class Pair[T <: Comparable[T]] {

  def compare(first:T,second:T): T ={
    if(first.compareTo(second)>0) first else second
  }
}

object Pair{
  def main(args: Array[String]): Unit = {
    val pair = new Pair[String]
    println(pair.compare("hadoop","spark"))
  }
}