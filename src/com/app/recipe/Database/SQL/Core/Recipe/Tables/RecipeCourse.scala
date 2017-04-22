package com.app.recipe.Database.SQL.Core.Recipe.Tables

import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeCore
import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeTableAccess

/**
 * This class knows all there is to know about the recipe source
 * table structure. It is a flexible way to query the database and 
 * should not be used directly by the front end.
 */
class RecipeSource() extends SQLRecipeTableAccess {

  /**
   * The row by id.
   */
  override def getRowId( id : Int ) : Option[RecipeSourceRow] = {
    getHashMapFromSQL( raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeSourceTableName()} WHERE id = '${id}' ", getRecipeSourceColumns() ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), getRecipeSourceTableName()).asInstanceOf[RecipeSourceRow])
      case _ => throw new IllegalStateException(s"Multiple primary key'd rows found for id '$id'.")
    }
  }

  /**
   * The rows that match supplied recipe id.
   */
  def getRecipeId( id : Int ) : Option[List[RecipeSourceRow]] = 
    getHashMapFromSQL( raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeSourceTableName()} WHERE recipe_id = '${id}'", getRecipeSourceColumns() ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[RecipeSourceRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, getRecipeSourceTableName()).asInstanceOf[RecipeSourceRow]) ::: optionList
        }
        if ( optionList.size == 0 ) None else Some(optionList)
      }
    }
}