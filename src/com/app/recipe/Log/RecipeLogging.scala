package com.app.recipe.Log

import com.typesafe.scalalogging.LazyLogging
import java.util.Date

/**
 * Setting the logging structure.
 */
trait RecipeLogging extends LazyLogging {

  /**
   * The logger debug.
   */
  def debug(string : String) {
    logger.debug("["+getDateTime()+"] : "+string)
  }

  /**
   * The logger info.
   */
  def info(string : String) {
    logger.info("["+getDateTime()+"] : "+string)
  }

  /**
   * The logger warn.
   */
  def warn(string : String) {
    logger.warn("["+getDateTime()+"] : "+string)
  }

  /**
   * The logger error.
   */
  def error(string : String) {
    logger.error("["+getDateTime()+"] : "+string)
  }

  /**
   * The logger trace.
   */
  def trace(string : String) {
    logger.trace("["+getDateTime()+"] : "+string)
  }

  /** ===================================================================== **/
  /**
   * The standard pre-string to be added at the start of every log.
   */
  private final def getPreString() : String = "["+getDateTime()+"]"

  /**
   * The date time to be added at the start of the log.
   */
  private final def getDateTime() : String = {
    val date = new Date()
    getYears(date) + getMonths(date) + getDays(date) + "T" + 
    getHours(date) + getMinutes(date) + getSeconds(date)
  }

  /**
   * The converted years, part of the current time.
   */
  private final def getYears(date : Date) : String = (date.getYear + 1900).toString()

  /**
   * The converted months, part of the current time.
   */
  private final def getMonths(date : Date) : String = date match {
    case x if x.getMonth() < 10 => "0"+date.getMonth()
    case _ => date.getMonth.toString()
  }

  /**
   * The converted days, part of the current time.
   */
  private final def getDays(date : Date) : String = date match {
    case x if x.getDay() < 10 => "0"+date.getDay()
    case _ => date.getDay.toString()
  }

  /**
   * The converted hours, part of the current time.
   */
  private final def getHours(date : Date) : String = date match {
    case x if x.getHours() < 10 => "0"+date.getHours()
    case _ => date.getHours.toString()
  }

  /**
   * The converted minutes, part of the current time.
   */
  private final def getMinutes(date : Date) : String = date match {
    case x if x.getMinutes() < 10 => "0"+date.getMinutes()
    case _ => date.getMinutes.toString()
  }

  /**
   * The converted seconds, part of the current time.
   */
  private final def getSeconds(date : Date) : String = date match {
    case x if x.getSeconds() < 10 => "0"+date.getSeconds()
    case _ => date.getSeconds.toString()
  }
}
