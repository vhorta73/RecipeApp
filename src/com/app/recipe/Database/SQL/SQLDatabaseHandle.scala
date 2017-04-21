package com.app.recipe.Database.SQL

import java.sql.Connection

import com.app.recipe.Database.RecipeDatabase
import com.app.recipe.Database.RecipeDatabase
import scala.util.Random

/**
 * The SQL database object for SQL database access.
 */
object SQLDatabaseHandle extends RecipeDatabase { 
  /**
   * Single threaded database access handle.
   */
  private final val dbHandle = SQLDatabaseAccess.getHandle(this)

  /**
   * The max number of allowed connections.
   */
  private final val MAX_CONNECTIONS = SQLDatabaseAccess.getThreadCount()
  
  /**
   * The multiple opened db connections for round-robin.
   */
  private final var dbMultiHandle : List[Connection] = Nil
  
  /**
   * The SQL Database connection.
   */
  def getSQLHandle() : Connection = dbHandle

  /**
   * Evenly distributed multi-threaded connection handles.
   */
  def getMultiThreadedSQLHandle() : Connection = getSQLMultiHandle()

  /**
   * Setting the multiple DB handle in a lazy way.
   */
  private final def getSQLMultiHandle() : Connection = {
    if ( dbMultiHandle == Nil ) {
      var finalList : List[Connection] = List()
      for ( i <- 1 to MAX_CONNECTIONS ) finalList = SQLDatabaseAccess.getHandle(SQLDatabaseHandle) :: finalList
      dbMultiHandle = finalList
    }
    dbMultiHandle(Random.nextInt(MAX_CONNECTIONS))
  }
}