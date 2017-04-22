package com.app.recipe.Database.SQL.Core.Recipe.Modules.Retriever

import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeTypeRow
import com.app.recipe.Database.SQL.Core.Recipe.RetrieverCore
import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeCore

/**
 * This class knows all there is to know about the recipe type
 * table structure. It is a flexible way to query the database and 
 * should not be used directly by the front end.
 */
class RecipeType() extends RetrieverCore with SQLRecipeCore {

  /**
   * The row by id.
   */
  override def getRowId( id : Int ) : Option[RecipeTypeRow] = {
    getHashMapFromSQL( raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeTypeTableName()} WHERE id = '${id}' ", getRecipeTypeColumns() ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), getRecipeTypeTableName()).asInstanceOf[RecipeTypeRow])
      case _ => throw new IllegalStateException(s"Multiple primary key'd rows found for id '$id'.")
    }
  }

  /**
   * The rows that match supplied recipe id.
   */
  def getRecipeId( id : Int ) : Option[List[RecipeTypeRow]] = 
    getHashMapFromSQL( raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeTypeTableName()} WHERE recipe_id = '${id}'", getRecipeTypeColumns() ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[RecipeTypeRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, getRecipeTypeTableName()).asInstanceOf[RecipeTypeRow]) ::: optionList
        }
        if ( optionList.size == 0 ) None else Some(optionList)
      }
    }
}