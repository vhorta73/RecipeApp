package com.app.recipe.Database.SQL.Core.Ingredient

import java.sql.Timestamp

import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientNameRow
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientTableRow
import com.app.recipe.Database.SQL.Core.SQLGlobalMethods
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientAttributeRow

/**
 * Setting the methods that all children must implement and sharing logic 
 * amongst all of them.
 */
abstract class SQLIngredientTableAccess extends SQLIngredientCore with SQLGlobalMethods[com.app.recipe.Model.Ingredient] {

  // TODO: Know who is requesting these methods...
  protected final val last_updated_by : String = "Me"

  /**
   * Method to get one or more rows by recipe id.
   * //TODO: This method will cause issues if core will have tables without recipe id...
   */
  def getIngredientId( id : Int ) : Option[List[IngredientTableRow]]
  
  /**
   * Returning the respective object depending on the table and data supplied.
   */
  protected def getObject( data : Map[String,String], table : String ) : IngredientTableRow = table match {
    case "ingredient_name" => {
      val created_date      : Timestamp = Timestamp.valueOf(data("created_date").toString())
      val last_updated_date : Timestamp = Timestamp.valueOf(data("last_updated_date").toString())
      IngredientNameRow( 
          id                = data("id").toInt
        , name              = data("name").toString
        , created_by        = data("created_by")
        , created_date      = created_date
        , last_updated_by   = data("last_updated_by") 
        , last_updated_date = last_updated_date
      )
    }
    case "ingredient_attribute" => {
      val created_date      : Timestamp = Timestamp.valueOf(data("created_date").toString())
      val last_updated_date : Timestamp = Timestamp.valueOf(data("last_updated_date").toString())
      IngredientAttributeRow( 
          id                = data("id").toInt
        , name              = data("name").toString
        , created_by        = data("created_by")
        , created_date      = created_date
        , last_updated_by   = data("last_updated_by") 
        , last_updated_date = last_updated_date
      )
    }
    case e => throw new IllegalStateException(s"Not known object table: $e")
  }
}