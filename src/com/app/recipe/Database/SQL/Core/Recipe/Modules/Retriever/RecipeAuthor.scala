package com.app.recipe.Database.SQL.Core.Recipe.Modules.Retriever

import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeMainIngredientRow
import com.app.recipe.Database.SQL.Core.Recipe.RetrieverCore
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeAuthorRow

/**
 * This class knows all there is to know about the recipe author
 * table structure. It is a flexible way to query the database and 
 * should not be used directly by the front end.
 */
class RecipeAuthor( database : String ) extends RetrieverCore {

  /**
   * The table name.
   */
  private final val TABLE_NAME = "recipe_author"

  /**
   * The column names.
   */
  private final val COLUMNS = Array("id","recipe_id","author","created_by","created_date","last_updated_by","last_updated_date")

  /**
   * The row by id.
   */
  override def getRowId( id : Int ) : Option[RecipeAuthorRow] = {
    getHashMapFromSQL( raw"SELECT * FROM ${database}.${TABLE_NAME} WHERE id = '${id}' ", COLUMNS ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), TABLE_NAME).asInstanceOf[RecipeAuthorRow])
      case _ => throw new IllegalStateException(s"Multiple primary key'd rows found for id '$id'.")
    }
  }

  /**
   * The rows that match supplied recipe id.
   */
  def getRecipeId( id : Int ) : Option[List[RecipeAuthorRow]] = 
    getHashMapFromSQL( raw"SELECT * FROM ${database}.${TABLE_NAME} WHERE recipe_id = '${id}'", COLUMNS ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[RecipeAuthorRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, TABLE_NAME).asInstanceOf[RecipeAuthorRow]) ::: optionList
        }
        if ( optionList.size == 0 ) None else Some(optionList)
      }
    }
}