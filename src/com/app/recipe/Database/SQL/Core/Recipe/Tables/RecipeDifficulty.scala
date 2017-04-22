package com.app.recipe.Database.SQL.Core.Recipe.Tables

import com.app.recipe.Database.SQL.Core.Recipe.RecipeDifficultyRow
import com.app.recipe.Database.SQL.Core.Recipe.RetrieverCore
import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeCore

/**
 * This class knows all there is to know about the recipe difficulty
 * table structure. It is a flexible way to query the database and 
 * should not be used directly by the front end.
 */
class RecipeDifficulty() extends RetrieverCore with SQLRecipeCore {

  /**
   * The row by id.
   */
  override def getRowId( id : Int ) : Option[RecipeDifficultyRow] = {
    getHashMapFromSQL( raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeDifficultyTableName()} WHERE id = '${id}' ", getRecipeDifficultyColumns() ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), getRecipeDifficultyTableName()).asInstanceOf[RecipeDifficultyRow])
      case _ => throw new IllegalStateException(s"Multiple primary key'd rows found for id '$id'.")
    }
  }

  /**
   * The rows that match supplied recipe id.
   */
  def getRecipeId( id : Int ) : Option[List[RecipeDifficultyRow]] = 
    getHashMapFromSQL( raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeDifficultyTableName()} WHERE recipe_id = '${id}'", getRecipeDifficultyColumns() ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[RecipeDifficultyRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, getRecipeDifficultyTableName()).asInstanceOf[RecipeDifficultyRow]) ::: optionList
        }
        if ( optionList.size == 0 ) None else Some(optionList)
      }
    }
}