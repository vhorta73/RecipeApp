package com.app.recipe.Database.SQL.Core.Recipe.Modules.Retriever

import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeNameRow
import com.app.recipe.Database.SQL.Core.Recipe.RetrieverCore

/**
 * This class knows all there is to know about the recipe structure.
 * It is a flexible way to query the database and should not be used
 * directly by the front end.
 */
class RecipeNameRetriever( database : String ) extends RetrieverCore {

  /**
   * The table name.
   */
  private final val TABLE_NAME = "recipe"

  /**
   * The column names.
   */
  private final val COLUMNS = Array("id","name","version","created_by","created_date","last_updated_by","last_updated_date")

  /**
   * The row by id.
   */
  override def getRowId( id : Int ) : Option[RecipeNameRow] = {
    getHashMapFromSQL( raw"SELECT * FROM ${database}.${TABLE_NAME} WHERE id = '${id}' ", COLUMNS ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), TABLE_NAME).asInstanceOf[RecipeNameRow])
      case _ => throw new IllegalStateException(s"Multiple primary key'd rows found for id '$id'.")
    }
  }

  /**
   * Overriding the default getRecipeId that is used across all the other core tables.
   */
  override def getRecipeId( id : Int ) : Option[List[RecipeNameRow]] = getRowId(id) match {
    case None => None
    case result => Some(List(result.get))
  }

  /**
   * The Recipe by name. Multiple rows expected, one per version.
   */
  def getRecipeByName( name : String ) : Option[List[RecipeNameRow]] = 
    getHashMapFromSQL( raw"SELECT * FROM ${database}.${TABLE_NAME} WHERE name = '${name}'", COLUMNS ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[RecipeNameRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, TABLE_NAME).asInstanceOf[RecipeNameRow]) ::: optionList
        }
        if ( optionList.size == 0 ) None else Some(optionList)
      }
    }

  /**
   * The Recipe by name and version.
   */
  def getRecipeByNameAndVersion( name : String, version : Int ) : Option[RecipeNameRow] = 
    getHashMapFromSQL( raw"SELECT * FROM ${database}.${TABLE_NAME} WHERE name = '${name}' AND version = '${version}'", COLUMNS ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), TABLE_NAME).asInstanceOf[RecipeNameRow])
      case _ => throw new IllegalStateException(s"Multiple rows for same name and version found: name '$name', version '$version'.")
    }
}