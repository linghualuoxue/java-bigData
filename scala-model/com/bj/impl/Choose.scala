package com.bj.impl

/**
  * Created by Administrator on 2017/2/13.
  */

//视图界定
/*class Choose[T <% Ordered[T]] {

  def choose(first:T,second:T):T={
    if(first > second) first else second
  }
}*/

//上下文界定
class Choose[T : Ordering]{
  def choose(first:T,second:T):T={
    val ord = implicitly[Ordering[T]]
    if(ord.gt(first,second)) first else second
  }
}

object Choose{
  def main(args: Array[String]): Unit = {

    import com.bj.impl.MyPre._
    val g1 = new Girl("zhangsan",90)
    val g2 = new Girl("lisi",98)
    val choose = new Choose[Girl]
    println(choose.choose(g1,g2).name)
  }
}