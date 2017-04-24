package com.app.recipe.Database.SQL.Core.Recipe.Tables

import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeTableAccess
import com.app.recipe.Model.Recipe

/**
 * This class knows all there is to know about the recipe tag
 * table structure. It is a flexible way to query the database and 
 * should not be used directly by the front end.
 */
class RecipeTag() extends SQLRecipeTableAccess {

  /**
   * The row by id.
   */
  override def getRowId( id : Int ) : Option[RecipeTagRow] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeTagTableName()} WHERE id = ?")
    statement.setInt(1, id)
    getHashMapFromSQL( statement, getRecipeTagColumns() ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), getRecipeTagTableName()).asInstanceOf[RecipeTagRow])
      case _ => throw new IllegalStateException(s"Multiple primary key'd rows found for id '$id'.")
    }
  }

  /**
   * The rows that match supplied recipe id.
   */
  def getRecipeId( id : Int ) : Option[List[RecipeTagRow]] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeTagTableName()} WHERE recipe_id = ?")
    statement.setInt(1, id)
    getHashMapFromSQL( statement, getRecipeTagColumns() ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[RecipeTagRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, getRecipeTagTableName()).asInstanceOf[RecipeTagRow]) ::: optionList
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