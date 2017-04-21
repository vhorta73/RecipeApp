package com.app.recipe.Database.SQL.Core.Recipe.Modules.Retriever

import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeSourceRow
import com.app.recipe.Database.SQL.Core.Recipe.RetrieverCore
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeCourseRow

/**
 * This class knows all there is to know about the recipe course
 * table structure. It is a flexible way to query the database and 
 * should not be used directly by the front end.
 */
class RecipeCourse( database : String ) extends RetrieverCore {

  /**
   * The table name.
   */
  private final val TABLE_NAME = "recipe_course"

  /**
   * The column names.
   */
  private final val COLUMNS = Array("id","recipe_id","course","created_by","created_date","last_updated_by","last_updated_date")

  /**
   * The row by id.
   */
  override def getRowId( id : Int ) : Option[RecipeCourseRow] = {
    getHashMapFromSQL( raw"SELECT * FROM ${database}.${TABLE_NAME} WHERE id = '${id}' ", COLUMNS ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), TABLE_NAME).asInstanceOf[RecipeCourseRow])
      case _ => throw new IllegalStateException(s"Multiple primary key'd rows found for id '$id'.")
    }
  }

  /**
   * The rows that match supplied recipe id.
   */
  def getRecipeId( id : Int ) : Option[List[RecipeCourseRow]] = 
    getHashMapFromSQL( raw"SELECT * FROM ${database}.${TABLE_NAME} WHERE recipe_id = '${id}'", COLUMNS ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[RecipeCourseRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, TABLE_NAME).asInstanceOf[RecipeCourseRow]) ::: optionList
        }
        if ( optionList.size == 0 ) None else Some(optionList)
      }
    }
}