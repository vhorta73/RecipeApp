package com.app.recipe.Database.SQL.Core.Recipe.Tables

import java.sql.Connection

import scala.util.Random

import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeCore
import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeTableAccess
import com.app.recipe.Database.SQL.SQLDatabaseHandle
import java.time.LocalDateTime
import com.app.recipe.Model.Recipe

/**
 * This class knows all there is to know about the main ingredient 
 * table structure. It is a flexible way to query the database and 
 * should not be used directly by the front end.
 */
class RecipeMainIngredient() extends SQLRecipeTableAccess {

  /**
   * The row by id.
   */
  override def getRowId( id : Int ) : Option[RecipeMainIngredientRow] = {
    val getIdStatement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeMainIngredientTableName()} WHERE id = ?")
    getIdStatement.setInt(1,id)
    getHashMapFromSQL( getIdStatement, getRecipeMainIngredientColumns() ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), getRecipeMainIngredientTableName()).asInstanceOf[RecipeMainIngredientRow])
      case _ => throw new IllegalStateException(s"Multiple primary key'd rows found for id '$id'.")
    }
  }

  /**
   * The Recipe Main Ingredient by name. Multiple rows expected, if such ingredient
   * is assigned to many recipes.
   */
  def getRecipeMainIngredientByName( name : String ) : Option[List[RecipeMainIngredientRow]] = {
    val getNameStatement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeMainIngredientTableName()} WHERE main_ingredient = ? ")
    getNameStatement.setString(1,name)
    
    getHashMapFromSQL( getNameStatement, getRecipeNameColumns() ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[RecipeMainIngredientRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, getRecipeMainIngredientTableName()).asInstanceOf[RecipeMainIngredientRow]) ::: optionList
        }
        if ( optionList.size == 0 ) None else Some(optionList)
      }
    }
  }

  /**
   * The rows that match supplied recipe id.
   */
  override def getRecipeId( id : Int ) : Option[List[RecipeMainIngredientRow]] = {
    val getRecipeIdStatement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeMainIngredientTableName()} WHERE recipe_id = ?")
    getRecipeIdStatement.setInt(1, id)
    getHashMapFromSQL( getRecipeIdStatement, getRecipeMainIngredientColumns() ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[RecipeMainIngredientRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, getRecipeMainIngredientTableName()).asInstanceOf[RecipeMainIngredientRow]) ::: optionList
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