package com.app.recipe.Database.SQL.Core.Recipe.Tables

import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeTableAccess
import com.app.recipe.Model.Recipe

/**
 * This class knows all there is to know about the recipe course
 * table structure. It is a flexible way to query the database and 
 * should not be used directly by the front end.
 */
class RecipeCourse() extends SQLRecipeTableAccess {

  /**
   * The row by id.
   */
  override def getRowId( id : Int ) : Option[RecipeCourseRow] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeCourseTableName()} WHERE id = ?")
    statement.setInt(1, id)
    getHashMapFromSQL( statement, getRecipeCourseColumns() ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), getRecipeCourseTableName()).asInstanceOf[RecipeCourseRow])
      case _ => throw new IllegalStateException(s"Multiple primary key'd rows found for id '$id'.")
    }
  }

  /**
   * The rows that match supplied recipe id.
   */
  def getRecipeId( id : Int ) : Option[List[RecipeCourseRow]] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeCourseTableName()} WHERE recipe_id = ?")
    statement.setInt(1, id)
    getHashMapFromSQL( statement, getRecipeCourseColumns() ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[RecipeCourseRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, getRecipeCourseTableName()).asInstanceOf[RecipeCourseRow]) ::: optionList
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