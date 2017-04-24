package com.app.recipe.Database.SQL.Core.Recipe.Tables

import scala.util.Random

import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeCore
import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeTableAccess
import com.app.recipe.Database.SQL.SQLDatabaseHandle
import com.app.recipe.Model.Recipe

/**
 * This class knows all there is to know about the recipe duration
 * table structure. It is a flexible way to query the database and 
 * should not be used directly by the front end.
 */
class RecipeDuration() extends SQLRecipeTableAccess {

  /**
   * The row by id.
   */
  override def getRowId( id : Int ) : Option[RecipeDurationRow] = {
    val getIdStatement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeDurationTableName()} WHERE id = ?")
    getIdStatement.setInt(1,id)
    getHashMapFromSQL( getIdStatement, getRecipeDurationColumns() ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), getRecipeDurationTableName()).asInstanceOf[RecipeDurationRow])
      case _ => throw new IllegalStateException(s"Multiple primary key'd rows found for id '$id'.")
    }
  }

  /**
   * The rows that match supplied recipe id.
   */
  override def getRecipeId( id : Int ) : Option[List[RecipeDurationRow]] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeDurationTableName()} WHERE recipe_id = ?" )
    statement.setInt(1,id)
    getHashMapFromSQL( statement, getRecipeDurationColumns() ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[RecipeDurationRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, getRecipeDurationTableName()).asInstanceOf[RecipeDurationRow]) ::: optionList
        }
        if ( optionList.size == 0 ) None else Some(optionList)
      }
    }
  }

  /**
   * Creates a new record with information supplied or updates existing row/rows
   * for given recipe otherwise. Returns true if inserted, false if updated.
   * 
   * @param recipe
   * @return Option[List[TableRow]] 
   */
  override def saveRecord( recipe : Recipe ) : Option[List[TableRow]] = None
}