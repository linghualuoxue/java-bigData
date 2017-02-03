package com.bj.sxt

/**
  * Created by user on 2017/2/3.
  */

class TestScala{
  val id =1973
  private var name = "zhangsan" //有get和set方法

}

//伴生对象
object TestScala {

  def main(args: Array[String]): Unit = {
    val testScala = new TestScala();
    testScala.name = "lisi"
    print(testScala.name)
  }
}
