package com.app.recipe.Database.SQL

import java.sql.Connection
import java.sql.DriverManager

import com.app.recipe.Database.RecipeDatabase
import com.typesafe.config.ConfigFactory

/**
 * Grant database access if conditions are met.
 */
object SQLDatabaseAccess {

  /**
   * The config file to load with set authentication
   */
  private final val config                 = ConfigFactory.load(DATABABASE_CONFIG_FILE)
  private final val DATABABASE_CONFIG_FILE = "configuration/database.conf"
  private final val USERNAME_CONF          = "sql.username"
  private final val PASSWORD_CONF          = "sql.password"
  private final val DRIVER_CONF            = "sql.driver"
  private final val URL_CONF               = "sql.url"
  private final val THREAD_COUNT           = "sql.threads"

  /**
   * The database handle which gives access to the DB.
   */
  def getHandle(db : RecipeDatabase) : Connection = {

    if ( db == null || ! db.getClass.equals(SQLDatabaseHandle.getClass) )
      throw new IllegalArgumentException("Cannot access SQL database")
    
    // Capture any errors and define what to show.
    try {
      Class.forName(config.getString(DRIVER_CONF))
      return DriverManager.getConnection(
          config.getString(URL_CONF), 
          config.getString(USERNAME_CONF), 
          config.getString(PASSWORD_CONF))
    } catch { case _ : Throwable => Nil }

    // If we get here, means that something was wrong.
    // Never display user details.
    throw new IllegalStateException("Cannot access SQL database")
  }
  
  /**
   * The configured set thread count.
   */
  def getThreadCount() : Int = config.getString(THREAD_COUNT).toInt
}