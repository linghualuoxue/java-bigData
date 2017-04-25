/**
  * Created by user on 2017/4/25.
  */
class Student {
   var age=""
   var sex=""

  def this(age:String){
    this()
    this.age = age
  }
  def this(age:String,sex:String){
    this(age)
    this.sex=sex
  }

}
object Sutdent extends App{

  val student = new Student("zhangsan")
  print(student.age)
}
