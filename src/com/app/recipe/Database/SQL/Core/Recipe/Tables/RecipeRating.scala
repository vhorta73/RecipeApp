package com.app.recipe.Database.SQL.Core.Recipe.Tables

import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeTableAccess
import com.app.recipe.Model.Recipe

/**
 * This class knows all there is to know about the recipe rating
 * table structure. It is a flexible way to query the database and 
 * should not be used directly by the front end.
 */
class RecipeRating() extends SQLRecipeTableAccess {

  /**
   * The row by id.
   */
  override def getRowId( id : Int ) : Option[RecipeRatingRow] = {
    val statement= getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeRatingTableName()} WHERE id = ?")
    statement.setInt(1, id)
    getHashMapFromSQL( statement, getRecipeRatingColumns() ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), getRecipeRatingTableName()).asInstanceOf[RecipeRatingRow])
      case _ => throw new IllegalStateException(s"Multiple primary key'd rows found for id '$id'.")
    }
  }

  /**
   * The rows that match supplied recipe id.
   */
  def getRecipeId( id : Int ) : Option[List[RecipeRatingRow]] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeRatingTableName()} WHERE recipe_id = ?")
    statement.setInt(1, id)
    getHashMapFromSQL( statement, getRecipeRatingColumns() ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[RecipeRatingRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, getRecipeRatingTableName()).asInstanceOf[RecipeRatingRow]) ::: optionList
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