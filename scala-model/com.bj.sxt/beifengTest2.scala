import scala.collection.mutable.ArrayBuffer

/**
  * Created by user on 2017/4/25.
  */
object beifengTest2 {

  def main(args: Array[String]): Unit = {
    // print(sayHello("zhangsan"))
    //import scala.io.Source._
    //lazy val lines =fromFile("http://www.baidu.com").mkString   //延时加载
    //println(lines)
    //val arr = Array("123","abc")
    trimArrBuffer
  }
  //def sayHello(name:String)="hello,"+name
  //def sayHello(name:String){println("hello"+name);"hello"+name}
  def trimArrBuffer: Unit ={
    val arr = ArrayBuffer[Int]()
    arr+=(1,2,3,4,5,-1,-3,-5,-9)
    var fondFirstFu = false
    val keepIndex = for(i <- 0 until arr.length if !fondFirstFu || arr(i)>=0 ) yield {
      if(arr(i)<0) fondFirstFu=true
      i
    }
    print(keepIndex)

    for(i<- 0 until keepIndex.length){
      arr(i)=arr(keepIndex(i))
    }
    arr.trimEnd(arr.length-keepIndex.length)
    println(arr)
  }
  def trimArr={
    val arr = ArrayBuffer[Int]()
    arr+=(1,2,3,4,5,-1,-3,-5,-9)
    var length = arr.length
    var fondFirstFu = false
    var i=0
    while (i<length){
      if(arr(i)>=0)i+=1
      else
      {
        if(!fondFirstFu){ i+=1;fondFirstFu=true}
        else
        {
          arr.remove(i)
          length-=1
        }
      }
    }
    print(arr.mkString(","))
  }
}
