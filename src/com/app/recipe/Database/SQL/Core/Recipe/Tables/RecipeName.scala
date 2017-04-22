package com.app.recipe.Database.SQL.Core.Recipe.Tables

import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeTableAccess

/**
 * This class knows all there is to know about the recipe structure.
 * It is a flexible way to query the database and should not be used
 * directly by the front end.
 */
class RecipeName() extends SQLRecipeTableAccess {

  /**
   * The row by id.
   */
  override def getRowId( id : Int ) : Option[RecipeNameRow] = {
    getHashMapFromSQL( raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeNameTableName()} WHERE id = '${id}' ", getRecipeNameColumns() ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), getRecipeNameTableName()).asInstanceOf[RecipeNameRow])
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
    getHashMapFromSQL( raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeNameTableName()} WHERE name = '${name}'", getRecipeNameColumns() ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[RecipeNameRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, getRecipeNameTableName()).asInstanceOf[RecipeNameRow]) ::: optionList
        }
        if ( optionList.size == 0 ) None else Some(optionList)
      }
    }

  /**
   * The Recipe by name and version.
   */
  def getRecipeByNameAndVersion( name : String, version : Int ) : Option[RecipeNameRow] = 
    getHashMapFromSQL( raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeNameTableName()} WHERE name = '${name}' AND version = '${version}'", getRecipeNameColumns() ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), getRecipeNameTableName()).asInstanceOf[RecipeNameRow])
      case _ => throw new IllegalStateException(s"Multiple rows for same name and version found: name '$name', version '$version'.")
    }
}