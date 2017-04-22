package com.app.recipe.Database.SQL.Core.Recipe

import scala.collection.immutable.HashMap

import com.app.recipe.Database.SQL.Core.Recipe.Tables.TableRow
import com.app.recipe.Database.SQL.SQLDatabaseHandle

/**
 * Shared logic amongst all retriever classes and objects.
 */
abstract class SQLTableAccess extends SQLRecipeCore {

  /**
   * Method to get one row by id that must be implemented by child classes.
   */
  def getRowId( id : Int ) : Option[TableRow]
  
  /**
   * Generic DB SQL query for given SQL and column names.
   * TODO: Add a session to this to restrict access.
   */
  protected def getHashMapFromSQL( sql : String, columns : Array[String] ) : List[Map[ String, String ]] = {
    val resultSet = SQLDatabaseHandle.getSQLHandle().createStatement().executeQuery(sql)
    var finalList : List[Map[ String, String ]] = List()
    var hashMap : HashMap[ String , String ] = HashMap()
    while ( resultSet.next() ) {
      var index : Int = 1
      for( column <- columns ) {
        hashMap = hashMap + (column -> resultSet.getString(index))
        index = index + 1
      }
      finalList = finalList ::: List(hashMap)
    }
    finalList
  }

}