package com.bj.sxt.caseDaemon.implic

/**
  * Created by user on 2017/2/13.
  */
//视图界定
class Choose[T <% Ordered[T]] {

    def choose(first:T,second:T): T ={
      if(first > second ) first else second
    }

}

object Choose{
  def main(args: Array[String]): Unit = {

  }
}