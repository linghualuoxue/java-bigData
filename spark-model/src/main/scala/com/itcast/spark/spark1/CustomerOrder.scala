package com.itcast.spark.spark1

import org.apache.spark.{SparkConf, SparkContext}


object order{
  implicit val gir2Ordering = new Ordering[Gir]{
    override def compare(x: Gir, y: Gir): Int = {
     if(x.faceValue > y.faceValue) 1
     else if(x.faceValue == y.faceValue){
       if(x.age > y.age ) -1 else 1
     }else -1
    }
  }
}

/**
  * Created by Administrator on 2017/2/20.
  */
object CustomerOrder {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("custom").setMaster("local[2]")
    val sc = new SparkContext(conf)

    val arr = sc.parallelize(List(("zhangsan",89,25),("lisi",89,30)))
    import order._
    val rdd1 = arr.sortBy(x=>Gir(x._1,x._2,x._3),false)
    println(rdd1.collect().toBuffer)
    sc.stop()
  }
}

/*
case class Gir(name:String,faceValue:Int,age:Int) extends Ordered[Gir] with Serializable{
  override def compare(that: Gir): Int = {
    if(this.faceValue == that.faceValue){
      that.age - this.age
    }else{
      this.faceValue - that.faceValue
    }
  }
}*/
case class Gir(name:String,faceValue:Int,age:Int) extends Serializable