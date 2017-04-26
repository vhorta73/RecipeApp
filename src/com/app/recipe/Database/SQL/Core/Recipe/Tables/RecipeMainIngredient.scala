package com.app.recipe.Database.SQL.Core.Recipe.Tables

import java.sql.PreparedStatement

import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeTableAccess
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
  override def saveRecord( recipe : Recipe ) : Option[List[TableRow]] = {
    if ( recipe.mainIngredient.isEmpty ) return None
    val recipeMainIngredients : List[String] = recipe.mainIngredient.get.asInstanceOf[List[String]]
    var finalList : List[RecipeMainIngredientRow] = Nil

    // Set all recipe main ingredient as deleted before inserting/updating
    setMainIngredientDeleted( recipe )

    for( recipeIngredient <- recipeMainIngredients ) {
      var recipeMainIngredientRow : RecipeMainIngredientRow = RecipeMainIngredientRow(
          recipe_id       = if (recipe.id.isDefined ) recipe.id.get else throw new IllegalStateException(s"No recipe_id supplied in $recipe")
        , main_ingredient = recipeIngredient.toString
        , last_updated_by = last_updated_by
      )

      // Insert first. Update next..
      try { prepareRecord("insert", recipeMainIngredientRow).execute() } 
      catch { case duplicatedKey : Throwable => prepareRecord("update",recipeMainIngredientRow).execute() }
      finalList = List(recipeMainIngredientRow) ::: finalList
    }

    if ( finalList.size > 0 ) Some(finalList) else None
  }
  
  /**
   * Set all recipe main ingredient not active for given recipe_id.
   */
  private final def setMainIngredientDeleted( recipe : Recipe ) {
    val statement = getStatement(raw"UPDATE ${getCoreDatabaseName()}.${getRecipeMainIngredientTableName()} " +
        " SET active = 'N' WHERE recipe_id = ?")
    statement.setInt(1, recipe.id.get)
    statement.execute()
  }

  /**
   * Creates a prepared to execute statement, depending on the action requested.
   */
  private final def prepareRecord( action : String, row : RecipeMainIngredientRow ) : PreparedStatement = {

    // The final updating statement to be delivered
    var statement : PreparedStatement = null
    
    action match {
      case "insert" => {
        statement = getStatement(raw"INSERT INTO ${getCoreDatabaseName()}.${getRecipeMainIngredientTableName()} " +
        " (`recipe_id`,`main_ingredient`,`active`,`created_by`,`last_updated_by`) " +
        " VALUES(?, ?, ?, ?, ?)")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getRecipeMainIngredientColumns().foreach { columnName => columnName match {
          case "id"                => "No actrion required"
          case "recipe_id"         => statement.setInt(1, row.recipe_id)
          case "main_ingredient"   => statement.setString(2, row.main_ingredient)
          case "active"            => statement.setString(3, "Y")
          case "created_by"        => statement.setString(4, row.created_by)
          case "created_date"      => "No action required"
          case "last_updated_by"   => statement.setString(5, row.last_updated_by)
          case "last_updated_date" => "No action required"
          case _ => error(s"Unknwon new table column: '$columnName'.")
        } }
      }
      case "update" => {
        statement = getStatement(raw"UPDATE ${getCoreDatabaseName()}.${getRecipeMainIngredientTableName()} " +
        " SET `created_by` = ?, `last_updated_by` = ?, `active` = ? " + 
        " WHERE `recipe_id` = ? AND `main_ingredient` = ? ")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getRecipeMainIngredientColumns().foreach { columnName => columnName match {
          case "created_by"        => statement.setString(1, row.created_by)
          case "last_updated_by"   => statement.setString(2, row.last_updated_by)
          case "active"            => statement.setString(3, "Y")
          case "recipe_id"         => statement.setInt(4, row.recipe_id)
          case "main_ingredient"   => statement.setString(5, row.main_ingredient)
          case "created_date"      => "No action required"
          case "last_updated_date" => "No action required"
          case "id"                => "No action required"
          case _ => error(s"Unknwon new table column: '$columnName'.")
        } }
      }
    }
    statement
  }
}