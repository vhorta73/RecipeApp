package com.app.recipe.Database.SQL.Core.Recipe.Modules

import scala.collection.immutable.HashMap
import com.app.recipe.Database.SQL.SQLDatabaseHandle
import com.app.recipe.Log.RecipeLogging


/**
 * The generic class to collect the information from the database respective
 * to the requested recipe id from the database and table supplied, against 
 * the also supplied column names. 
 * 
 * @param database : String        - The database name
 * @param table    : String        - The table name to query
 * @param columns  : Array[String] - The list of column names
 * @param keys     : String*       - The key names of interest when passing values.
 */
class RecipeCoreRetrieveQuery( database: String, table: String, columns : Array[String], keys : String* ) extends RecipeLogging {

  /**
   * Convert given keys to a list to easier access.
   */
  private final val key : List[String] = keys.toList

  /**
   * The Recipe by id.
   */
  def getRecipeById( id : Int ) : List[Map[ String , String ]] = 
    getHashMapFromSQL(raw"SELECT * FROM ${database}.${table} WHERE ${key(0)} = '${id}' ", columns)

  /**
   * The Recipe by name and version.
   */
  def getRecipeByNameAndVersion( name : String, version : Int ) : List[Map[ String , String ]] = 
    getHashMapFromSQL(raw"SELECT * FROM ${database}.${table} WHERE ${key(0)} = '${name}' AND ${key(1)} = '${version}'", columns)

  /**
   * Generic DB SQL query for given SQL and column names.
   */
  private def getHashMapFromSQL( sql : String, columns : Array[String] ) : List[Map[ String, String ]] = {
    val resultSet = SQLDatabaseHandle.getMultiThreadedSQLHandle().createStatement().executeQuery(sql)
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