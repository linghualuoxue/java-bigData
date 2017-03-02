package com.day3

import org.apache.log4j.{Level, Logger}
import org.apache.spark.Logging

/**
  * Created by Administrator on 2017/2/28.
  */
object LoggerLevels extends Logging{

  def setStreamingLogLevels(): Unit ={
    val log4jInitialized = Logger.getRootLogger.getAllAppenders.hasMoreElements
    if(!log4jInitialized){
      logInfo("Setting log level to [WARN] for streaming example."
      +"To override add a custom log4j.properties to class path")
      Logger.getRootLogger.setLevel(Level.WARN)
    }

  }

}
