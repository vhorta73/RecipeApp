package com.app.recipe.Database.SQL.Core.Recipe

import java.sql.Timestamp

import scala.collection.immutable.HashMap

import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeNameRow
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.TableValueObject
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.TableValueObject
import com.app.recipe.Database.SQL.SQLDatabaseHandle
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeMainIngredientRow
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeAuthorRow

/**
 * Shared logic amongst all retriever classes and objects.
 */
abstract class RetrieverCore {
  
  /**
   * Method to get one row by id that must be implemented by child classes.
   */
  def getRowId( id : Int ) : Option[TableValueObject]
  
  /**
   * Method to get one or more rows by recipe id.
   */
  def getRecipeId( id : Int ) : Option[List[TableValueObject]]
  
  /**
   * Generic DB SQL query for given SQL and column names.
   * TODO: Add a session to this to restrict access.
   */
  protected def getHashMapFromSQL( sql : String, columns : Array[String] ) : List[Map[ String, String ]] = {
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

  /**
   * Returning the respective object depending on the table and data supplied.
   */
  protected def getObject( data : Map[String,String], table : String ) : TableValueObject = table match {
    case "recipe" => {
      val created_date      : Timestamp = Timestamp.valueOf(data("created_date").toString())
      val last_updated_date : Timestamp = Timestamp.valueOf(data("last_updated_date").toString())
      RecipeNameRow( 
          id                = data("id").toInt
        , name              = data("name") 
        , version           = data("version").toInt 
        , created_by        = data("created_by")
        , created_date      = created_date
        , last_updated_by   = data("last_updated_by") 
        , last_updated_date = last_updated_date
      )
    }
    case "recipe_main_ingredient" => {
      val created_date      : Timestamp = Timestamp.valueOf(data("created_date").toString())
      val last_updated_date : Timestamp = Timestamp.valueOf(data("last_updated_date").toString())
      RecipeMainIngredientRow( 
          id                = data("id").toInt
        , recipe_id         = data("recipe_id").toInt 
        , main_ingredient   = data("main_ingredient") 
        , created_by        = data("created_by")
        , created_date      = created_date
        , last_updated_by   = data("last_updated_by") 
        , last_updated_date = last_updated_date
      )
    }
    case "recipe_author" => {
      val created_date      : Timestamp = Timestamp.valueOf(data("created_date").toString())
      val last_updated_date : Timestamp = Timestamp.valueOf(data("last_updated_date").toString())
      RecipeAuthorRow( 
          id                = data("id").toInt
        , recipe_id         = data("recipe_id").toInt 
        , author            = data("author") 
        , created_by        = data("created_by")
        , created_date      = created_date
        , last_updated_by   = data("last_updated_by") 
        , last_updated_date = last_updated_date
      )
    }
    case _ => throw new IllegalStateException("Not known object table")
  }
}