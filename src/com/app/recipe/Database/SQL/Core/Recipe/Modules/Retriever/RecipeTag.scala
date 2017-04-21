package com.app.recipe.Database.SQL.Core.Recipe.Modules.Retriever

import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeTagRow
import com.app.recipe.Database.SQL.Core.Recipe.RetrieverCore

/**
 * This class knows all there is to know about the recipe tag
 * table structure. It is a flexible way to query the database and 
 * should not be used directly by the front end.
 */
class RecipeTag( database : String ) extends RetrieverCore {

  /**
   * The table name.
   */
  private final val TABLE_NAME = "recipe_tag"

  /**
   * The column names.
   */
  private final val COLUMNS = Array("id","recipe_id","tag","created_by","created_date","last_updated_by","last_updated_date")

  /**
   * The row by id.
   */
  override def getRowId( id : Int ) : Option[RecipeTagRow] = {
    getHashMapFromSQL( raw"SELECT * FROM ${database}.${TABLE_NAME} WHERE id = '${id}' ", COLUMNS ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), TABLE_NAME).asInstanceOf[RecipeTagRow])
      case _ => throw new IllegalStateException(s"Multiple primary key'd rows found for id '$id'.")
    }
  }

  /**
   * The rows that match supplied recipe id.
   */
  def getRecipeId( id : Int ) : Option[List[RecipeTagRow]] = 
    getHashMapFromSQL( raw"SELECT * FROM ${database}.${TABLE_NAME} WHERE recipe_id = '${id}'", COLUMNS ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[RecipeTagRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, TABLE_NAME).asInstanceOf[RecipeTagRow]) ::: optionList
        }
        if ( optionList.size == 0 ) None else Some(optionList)
      }
    }
}