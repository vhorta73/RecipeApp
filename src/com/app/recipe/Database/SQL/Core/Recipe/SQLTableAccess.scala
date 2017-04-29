package com.app.recipe.Database.SQL.Core.Recipe

import java.sql.PreparedStatement
import java.time.LocalDateTime

import scala.collection.immutable.HashMap
import scala.util.Random

import com.app.recipe.Database.SQL.SQLDatabaseHandle
import com.app.recipe.Log.RecipeLogging

/**
 * Shared logic amongst all retriever classes and objects.
 */
abstract class SQLTableAccess extends RecipeLogging {

  /**
   * Generic DB SQL query for given SQL and column names.
   * TODO: Add a session to this to restrict access.
   */
  protected def getHashMapFromSQL( statement : PreparedStatement, columns : Array[String] ) : List[Map[ String, String ]] = {
    val resultSet = statement.executeQuery()
    var finalList : List[Map[ String, String ]] = List()
    var hashMap : HashMap[ String , String ] = HashMap()
    while ( resultSet.next() ) {
      for( column <- columns ) {
        hashMap = hashMap + (column -> resultSet.getString(column))
      }
      finalList = finalList ::: List(hashMap)
    }
    finalList
  }
  
  /**
   * The single point where all statements are prepared before execution.
   */
  protected def getStatement( sql : String ) : PreparedStatement = SQLDatabaseHandle.getMultiThreadedSQLHandle()
    .prepareStatement(sql)
    
  /**
   * Supplying a random name.
   */
  protected def getRandomName() : String = "Temp+" + LocalDateTime.now() + "+" + (new Random()).nextInt().toString()


}