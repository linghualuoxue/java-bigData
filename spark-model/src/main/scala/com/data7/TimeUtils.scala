package com.data7

import java.text.SimpleDateFormat
import java.util.Calendar

/**
  * Created by user on 2017/3/8.
  */
object TimeUtils {

  val simpleDateFormat = new SimpleDateFormat("yyyy-M-dd HH:mm:ss")
  val calendar = Calendar.getInstance()
  def apply(time:String):Long = {
      calendar.setTime(simpleDateFormat.parse(time))
      calendar.getTimeInMillis
  }

   def getLongTime(amount:Int):Long={
      calendar.add(Calendar.DATE,amount)
      val time = calendar.getTimeInMillis
      calendar.add(Calendar.DATE,-amount)
      time
   }



}
