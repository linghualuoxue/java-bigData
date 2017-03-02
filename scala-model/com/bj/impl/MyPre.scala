package com.bj.impl

/**
  * Created by Administrator on 2017/2/13.
  */
object MyPre {

  implicit def gir2Odred(girl:Girl)=new Ordered[Girl]{
    override def compare(that: Girl): Int = {
      girl.faceValue - that.faceValue
    }
  }

}
