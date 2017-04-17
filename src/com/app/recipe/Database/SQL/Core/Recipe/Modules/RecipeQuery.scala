package com.app.recipe.Database.SQL.Core.Recipe.Modules

import com.app.recipe.Database.SQL.SQLDatabaseAccess
import com.app.recipe.Database.SQL.SQLDatabase
import scala.collection.immutable.HashMap

/**
 * The generic class to collect the information from the database respective
 * to the requested recipe id from the database and table supplied, against 
 * the also supplied column names. 
 */
class RecipeQuery( database: String, table: String, columns : Array[String], key_id : String ) {
// TODO: Add session here and check if allowed to query the DB or not.
  /**
   * The database handle.
   */
  private val dbHandle = SQLDatabaseAccess.getHandle(SQLDatabase)

  /**
   * The Recipe name from id.
   */
  def getResult( id : Int ) : List[Map[ String , String ]] = {
    getHashMapFromSQL(
      raw"SELECT * FROM ${database}.${table} WHERE ${key_id} = '${id}' ", 
      columns
    )
  }

  /**
   * Generic DB SQL query for given SQL and column names.
   */
  private def getHashMapFromSQL( sql : String, columns : Array[String] ) : List[Map[ String, String ]] = {
    val resultSet = SQLDatabase.getSQLHandle().createStatement().executeQuery(sql)
    var finalList : List[Map[ String, String ]] = List()
    var hashMap : HashMap[ String , String ] = HashMap()
    if ( resultSet.next() ) {
      var index : Int = 1
      for( column : String <- columns ) {
        hashMap = hashMap + (column -> resultSet.getString(index))
        index = index + 1
      }
      finalList = finalList ::: List(hashMap)
    }
    finalList
  }
}