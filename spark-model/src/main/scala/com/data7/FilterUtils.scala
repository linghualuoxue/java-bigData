package com.data7

import org.apache.commons.lang3.time.FastDateFormat


/**
  * Created by user on 2017/3/8.
  */
object FilterUtils {
   val fastDateFormat = FastDateFormat.getInstance("yyyy年MM月dd日,E,HH:mm:ss")
  def filterByTime(file: Array[String], beginTime: Long, endTime: Long): Boolean = {
     val time = file(1)
     val longTime = fastDateFormat.parse(time).getTime
     longTime >= beginTime && longTime <= endTime
  }

}
