package com.app.recipe.Database.SQL.Core.Recipe.Tables

import java.sql.PreparedStatement

import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeTableAccess
import com.app.recipe.Model.Recipe
import com.app.recipe.Model.IngredientElement

/**
 * This class knows all there is to know about the recipe ingredient
 * table structure. It is a flexible way to query the database and 
 * should not be used directly by the front end.
 */
class RecipeIngredient() extends SQLRecipeTableAccess {

  /**
   * The row by id.
   */
  override def getRowId( id : Int ) : Option[RecipeIngredientRow] = {
    val statement= getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeIngredientTableName()} WHERE id = ?")
    statement.setInt(1, id)
    getHashMapFromSQL( statement, getRecipeIngredientColumns() ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), getRecipeIngredientTableName()).asInstanceOf[RecipeIngredientRow])
      case _ => throw new IllegalStateException(s"Multiple primary key'd rows found for id '$id'.")
    }
  }

  /**
   * The rows that match supplied recipe id.
   */
  def getRecipeId( id : Int ) : Option[List[RecipeIngredientRow]] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeIngredientTableName()} WHERE recipe_id = ?")
    statement.setInt(1, id)
    getHashMapFromSQL( statement, getRecipeIngredientColumns() ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[RecipeIngredientRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, getRecipeIngredientTableName()).asInstanceOf[RecipeIngredientRow]) ::: optionList
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
  override def saveRecord( recipe : Recipe ) : Option[List[RecipeTableRow]] = {
    if ( recipe.ingredientList.isEmpty ) return None
    val recipeIngredients: List[IngredientElement] = recipe.ingredientList.get.asInstanceOf[List[IngredientElement]]
    var finalList : List[RecipeIngredientRow] = Nil

    // Set all recipe ingredients as deleted before inserting/updating
    setIngredientsDeleted( recipe )
    
    for( recipeIngredient <- recipeIngredients ) {
      var recipeIngredientRow : RecipeIngredientRow = RecipeIngredientRow(
          recipe_id       = if (recipe.id.isDefined ) recipe.id.get else throw new IllegalStateException(s"No recipe_id supplied in $recipe")
        , ingredient_id   = recipeIngredient.ingredientId
        , quantity        = recipeIngredient.quantity
        , unit            = recipeIngredient.unit
        , last_updated_by = last_updated_by
      )

      // Insert first. Update next..
      try { prepareRecord("insert", recipeIngredientRow).execute() } 
      catch { case duplicatedKey : Throwable => prepareRecord("update",recipeIngredientRow).execute() }
      finalList = List(recipeIngredientRow) ::: finalList
    }

    if ( finalList.size > 0 ) Some(finalList) else None
  }
  
  /**
   * Set all recipe ingredients not active for given recipe_id.
   */
  private final def setIngredientsDeleted( recipe : Recipe ) {
    val statement = getStatement(raw"UPDATE ${getCoreDatabaseName()}.${getRecipeIngredientTableName()} " +
        " SET active = 'N' WHERE recipe_id = ?")
    statement.setInt(1, recipe.id.get)
    statement.execute()
  }

  /**
   * Creates a prepared to execute statement, depending on the action requested.
   */
  private final def prepareRecord( action : String, row : RecipeIngredientRow ) : PreparedStatement = {

    // The final updating statement to be delivered
    var statement : PreparedStatement = null
    
    action match {
      case "insert" => {
        statement = getStatement(raw"INSERT INTO ${getCoreDatabaseName()}.${getRecipeIngredientTableName()} " +
        " (`recipe_id`,`ingredient_id`,`quantity`,`unit`,`active`,`created_by`,`last_updated_by`) " +
        " VALUES(?, ?, ?, ?, ?, ?, ?)")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getRecipeIngredientColumns().foreach { columnName => columnName match {
          case "id"                => "No actrion required"
          case "recipe_id"         => statement.setInt(1, row.recipe_id)
          case "ingredient_id"     => statement.setInt(2, row.ingredient_id)
          case "quantity"          => statement.setDouble(3, row.quantity)
          case "unit"              => statement.setString(4, row.unit)
          case "active"            => statement.setString(5, "Y")
          case "created_by"        => statement.setString(6, row.created_by)
          case "created_date"      => "No action required"
          case "last_updated_by"   => statement.setString(7, row.last_updated_by)
          case "last_updated_date" => "No action required"
          case _ => error(s"Unknwon new table column: '$columnName'.")
        } }
      }
      case "update" => {
        statement = getStatement(raw"UPDATE ${getCoreDatabaseName()}.${getRecipeIngredientTableName()} " +
        " SET `created_by` = ?, `last_updated_by` = ?, `active` = ?, `quantity` = ?, `unit` = ? " + 
        " WHERE `recipe_id` = ? AND `ingredient_id` = ? ")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getRecipeIngredientColumns().foreach { columnName => columnName match {
          case "created_by"        => statement.setString(1, row.created_by)
          case "last_updated_by"   => statement.setString(2, row.last_updated_by)
          case "active"            => statement.setString(3, "Y")
          case "quantity"          => statement.setDouble(4, row.quantity)
          case "unit"              => statement.setString(5, row.unit)
          case "recipe_id"         => statement.setInt(6, row.recipe_id)
          case "ingredient_id"     => statement.setInt(7, row.ingredient_id)
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