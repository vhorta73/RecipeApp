package com.app.recipe.Database.SQL.Core.Recipe.Tables

import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeCore
import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeTableAccess
import scala.util.Random
import com.app.recipe.Model.Recipe
import java.sql.PreparedStatement

/**
 * This class knows all there is to know about the recipe Utensils
 * table structure. It is a flexible way to query the database and 
 * should not be used directly by the front end.
 */
class RecipeUtensils() extends SQLRecipeTableAccess {

  /**
   * The row by id.
   */
  override def getRowId( id : Int ) : Option[RecipeUtensilsRow] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeUtensilsTableName()} WHERE id = ?")
    statement.setInt(1,id)
    getHashMapFromSQL( statement, getRecipeUtensilsColumns() ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), getRecipeUtensilsTableName()).asInstanceOf[RecipeUtensilsRow])
      case _ => throw new IllegalStateException(s"Multiple primary key'd rows found for id '$id'.")
    }
  }

  /**
   * The rows that match supplied recipe id.
   */
  def getRecipeId( id : Int ) : Option[List[RecipeUtensilsRow]] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeUtensilsTableName()} WHERE recipe_id = ?")
    statement.setInt(1, id)
    getHashMapFromSQL( statement, getRecipeUtensilsColumns() ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[RecipeUtensilsRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, getRecipeUtensilsTableName()).asInstanceOf[RecipeUtensilsRow]) ::: optionList
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
    if ( recipe.utensils.isEmpty ) return None
    val recipeUtensils : List[String] = recipe.utensils.get
    var finalList : List[RecipeUtensilsRow] = Nil

    // Set all recipe course as deleted before inserting/updating
    setUtensilsDeleted( recipe )

    for ( rUtensil <- recipeUtensils ) {
      var recipeUtensilsRow : RecipeUtensilsRow = RecipeUtensilsRow(
          recipe_id  = if (recipe.id.isDefined ) recipe.id.get else throw new IllegalStateException(s"No recipe_id supplied in $recipe")
        , kitchen_utensil = rUtensil
        , last_updated_by = last_updated_by
      )

      // Insert first. Update next..
      try { prepareRecord("insert", recipeUtensilsRow).execute() } 
      catch { case duplicatedKey : Throwable => prepareRecord("update",recipeUtensilsRow).execute() }
      finalList = List(recipeUtensilsRow)::: finalList
    }
    if ( finalList.size > 0 ) Some(finalList) else None
  }
  
  /**
   * Set all recipe difficulty not active for given recipe_id.
   */
  private final def setUtensilsDeleted( recipe : Recipe ) {
    val statement = getStatement(raw"UPDATE ${getCoreDatabaseName()}.${getRecipeUtensilsTableName()} " +
        " SET active = 'N' WHERE recipe_id = ?")
    statement.setInt(1, recipe.id.get)
    statement.execute()
  }

  /**
   * Creates a prepared to execute statement, depending on the action requested.
   */
  private final def prepareRecord( action : String, row : RecipeUtensilsRow ) : PreparedStatement = {

    // The final updating statement to be delivered
    var statement : PreparedStatement = null
    
    action match {
      case "insert" => {
        statement = getStatement(raw"INSERT INTO ${getCoreDatabaseName()}.${getRecipeUtensilsTableName()} " +
        " (`recipe_id`,`kitchen_utensil`,`active`,`created_by`,`last_updated_by`) " +
        " VALUES(?, ?, ?, ?, ?)")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getRecipeUtensilsColumns().foreach { columnName => columnName match {
          case "id"                => "No actrion required"
          case "recipe_id"         => statement.setInt(1, row.recipe_id)
          case "kitchen_utensil"   => statement.setString(2, row.kitchen_utensil)
          case "active"            => statement.setString(3, "Y")
          case "created_by"        => statement.setString(4, row.created_by)
          case "created_date"      => "No action required"
          case "last_updated_by"   => statement.setString(5, row.last_updated_by)
          case "last_updated_date" => "No action required"
          case _ => error(s"Unknwon new table column: '$columnName'.")
        } }
      }
      case "update" => {
        statement = getStatement(raw"UPDATE ${getCoreDatabaseName()}.${getRecipeUtensilsTableName()} " +
        " SET `created_by` = ?, `last_updated_by` = ?, `active` = ?  " + 
        " WHERE `recipe_id` = ? AND `kitchen_utensil` = ? ")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getRecipeUtensilsColumns().foreach { columnName => columnName match {
          case "created_by"        => statement.setString(1, row.created_by)
          case "last_updated_by"   => statement.setString(2, row.last_updated_by)
          case "active"            => statement.setString(3, "Y")
          case "recipe_id"         => statement.setInt(4, row.recipe_id)
          case "kitchen_utensil"   => statement.setString(5, row.kitchen_utensil)
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