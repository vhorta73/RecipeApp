package com.app.recipe.Database.SQL.Core.Recipe

import com.app.recipe.Database.SQL.SQLDatabaseHandle
import com.app.recipe.Log.RecipeLogging

/**
 * The generic class to save supplied information to respective database and
 * table, setting given columns with data supplied .
 */
class RecipeCoreSavingQuer( database: String, table: String ) extends RecipeLogging {

  /**
   * Create a new recipe row with given name and version. If another recipe 
   * exists with same name and version, will throw a SQL INSERT DUP key 
   * exception error.
   */
  def newRecipe( name : String, version : Int ) : Unit = {
    val statement = SQLDatabaseHandle.getMultiThreadedSQLHandle().prepareStatement(raw"INSERT INTO ?.?${database}.${table} (`name`,`version`) VALUES('$name','$version')")
//    .execute()
  }

  /**
   * Saving supplied data, returning the generated id.
   */
  def save(dataMap : Map[String,String], name : String, version : Int) : Int = {
    table match {
      case "e" => println("ok")
      case _ => println("no")
    }
    val columns : String = getMapToColumns(dataMap)
    val values  : String = getMapToValues(dataMap)
//    val test = SQLDatabase.getMultiThreadedSQLHandle().prepareStatement(s"INSERT IGNORE INTO ${database}.${table} ($columns) VALUES($values)").execute()
//    println(test)
    1
  }
  
  private def getMapToColumns(dataMap : Map[String,String]) : String = {
    var str : List[String] = List()
    for(column <- dataMap) str = str ::: List(column._1.toString())
    str = str.map{ s => s"`$s`" }
    var string : String = str.mkString(",")
    println(string)
    string
  }
  
  private def getMapToValues(dataMap : Map[String,String]) : String = {
    var str : List[String] = List()
    for(column <- dataMap) str = str ::: List(column._2.toString())
    str = str.map{ s => s"'$s'" }
    var string = str.mkString(",")
    println(string)
    string
  }
}