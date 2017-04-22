package com.app.recipe.Database.SQL.Core.Recipe.Modules.Retriever

import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeStyleRow
import com.app.recipe.Database.SQL.Core.Recipe.RetrieverCore
import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeCore

/**
 * This class knows all there is to know about the recipe style
 * table structure. It is a flexible way to query the database and 
 * should not be used directly by the front end.
 */
class RecipeStyle() extends RetrieverCore with SQLRecipeCore {

  /**
   * The row by id.
   */
  override def getRowId( id : Int ) : Option[RecipeStyleRow] = {
    getHashMapFromSQL( raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeStyleTableName()} WHERE id = '${id}' ", getRecipeStyleColumns() ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), getRecipeStyleTableName()).asInstanceOf[RecipeStyleRow])
      case _ => throw new IllegalStateException(s"Multiple primary key'd rows found for id '$id'.")
    }
  }

  /**
   * The rows that match supplied recipe id.
   */
  def getRecipeId( id : Int ) : Option[List[RecipeStyleRow]] = 
    getHashMapFromSQL( raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeStyleTableName()} WHERE recipe_id = '${id}'", getRecipeStyleColumns() ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[RecipeStyleRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, getRecipeStyleTableName()).asInstanceOf[RecipeStyleRow]) ::: optionList
        }
        if ( optionList.size == 0 ) None else Some(optionList)
      }
    }
}