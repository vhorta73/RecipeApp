package com.app.recipe.Database.SQL.Core.Recipe

import com.app.recipe.Log.RecipeLogging
import com.app.recipe.Model.Recipe
import com.app.recipe.Database.SQL.Core.SQLCore

/**
 * The Recipe Core Saver implementing required methods set by RecipeDatabaseCore 
 * interface, to save data onto the database.
 */
object SQLRecipeCoreSaver extends SQLRecipeCore with RecipeLogging {

  /**
   * Get a new recipe Id after passing a name and version number.
   * If the name and version already exists, it will return the existing one.
   */
  def getNewRecipeId( name : String, version : Int ) : Option[Int] = {
    None
  }
  
  /**
   * Updates the supplied recipe to the database, inserting if new.
   * 
   * @param recipe : Recipe
   * @returns Option[Recipe]
   */
  def update( recipe : Recipe ) : Option[Recipe] = {
    None
  }

  
  protected def getMapToColumns(dataMap : Map[String,String]) : String = {
    var str : List[String] = List()
    for(column <- dataMap) str = str ::: List(column._1.toString())
    str = str.map{ s => s"`$s`" }
    var string : String = str.mkString(",")
    println(string)
    string
  }
  
  protected def getMapToValues(dataMap : Map[String,String]) : String = {
    var str : List[String] = List()
    for(column <- dataMap) str = str ::: List(column._2.toString())
    str = str.map{ s => s"'$s'" }
    var string = str.mkString(",")
    println(string)
    string
  }
 
}