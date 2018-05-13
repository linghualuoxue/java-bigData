
import java.net.URL

import org.apache.spark.{SparkConf, SparkContext}
/**
  * Created by Administrator on 2017/2/16.
  */
object
UrlCount {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("urlCount").setMaster("local[2]")
    val sc = new SparkContext(conf)
    //rdd1 将数据切分(url,1)
    val rdd1 = sc.textFile("C:\\itcast.log")map(lines =>{
      val f = lines.split("\t")
      (f(1),1)
    })
    val rdd2 = rdd1.reduceByKey(_+_)
    val rdd3 = rdd2.map(x=>{
      val url = x._1
      val host = new URL(url).getHost
      (host,url,x._2)
    })
    val rdd4 = rdd3.groupBy(_._1).mapValues(it=>{
      it.toList.sortBy(_._3).reverse.take(3)
    })
    println(rdd4.collect().toBuffer)
    sc.stop()
  }
}
