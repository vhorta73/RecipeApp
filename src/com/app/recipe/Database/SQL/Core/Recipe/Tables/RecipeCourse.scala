package com.app.recipe.Database.SQL.Core.Recipe.Tables

import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeTableAccess
import com.app.recipe.Model.Recipe

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
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeSourceTableName()} WHERE id = ?")
    statement.setInt(1, id)
    getHashMapFromSQL( statement, getRecipeSourceColumns() ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), getRecipeSourceTableName()).asInstanceOf[RecipeSourceRow])
      case _ => throw new IllegalStateException(s"Multiple primary key'd rows found for id '$id'.")
    }
  }

  /**
   * The rows that match supplied recipe id.
   */
  def getRecipeId( id : Int ) : Option[List[RecipeSourceRow]] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeSourceTableName()} WHERE recipe_id = ?")
    statement.setInt(1, id)
    getHashMapFromSQL( statement, getRecipeSourceColumns() ) match {
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

  /**
   * Creates a new record with information supplied. If the record already 
   * exists for the unique key supplied, it will apply an update instead.
   * Returns true if created, false if update and None if no action applied.
   * 
   * @param recipe
   * @return Option[TableRow] 
   */
  override def saveRecord( recipe : Recipe ) : Option[TableRow] = None
}