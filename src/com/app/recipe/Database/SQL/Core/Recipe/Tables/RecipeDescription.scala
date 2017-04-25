package com.app.recipe.Database.SQL.Core.Recipe.Tables

import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeCore
import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeTableAccess
import scala.util.Random
import com.app.recipe.Model.Recipe
import java.sql.PreparedStatement

/**
 * This class knows all there is to know about the recipe author
 * table structure. It is a flexible way to query the database and 
 * should not be used directly by the front end.
 */
class RecipeDescription() extends SQLRecipeTableAccess {

  /**
   * The row by id.
   */
  override def getRowId( id : Int ) : Option[RecipeDescriptionRow] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeDescriptionTableName()} WHERE id = ? ")
    statement.setInt(1, id)
    getHashMapFromSQL( statement, getRecipeDescriptionColumns() ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), getRecipeDescriptionTableName()).asInstanceOf[RecipeDescriptionRow])
      case _ => throw new IllegalStateException(s"Multiple primary key'd rows found for id '$id'.")
    }
  }

  /**
   * The rows that match supplied recipe id.
   */
  def getRecipeId( id : Int ) : Option[List[RecipeDescriptionRow]] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeDescriptionTableName()} WHERE recipe_id = ?")
    statement.setInt(1, id)
    getHashMapFromSQL( statement, getRecipeDescriptionColumns() ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[RecipeDescriptionRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, getRecipeDescriptionTableName()).asInstanceOf[RecipeDescriptionRow]) ::: optionList
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
    if ( recipe.description.isEmpty ) return None
    val recipeDescription: String = recipe.description.get.asInstanceOf[String]
    var finalList : List[RecipeDescriptionRow] = Nil

    // Set recipe description as deleted before inserting/updating
    setDescriptionDeleted( recipe )
    
    var recipeDescriptionRow : RecipeDescriptionRow = RecipeDescriptionRow(
        recipe_id  = if (recipe.id.isDefined ) recipe.id.get else throw new IllegalStateException(s"No recipe_id supplied in $recipe")
      , description = recipeDescription
      , last_updated_by = last_updated_by
    )

    // Insert first. Update next..
    try { prepareRecord("insert", recipeDescriptionRow).execute() } 
    catch { case duplicatedKey : Throwable => prepareRecord("update",recipeDescriptionRow).execute() }
    finalList = List(recipeDescriptionRow)::: finalList

    if ( finalList.size > 0 ) Some(finalList) else None
  }
  
  /**
   * Set recipe description not active for given recipe_id.
   */
  private final def setDescriptionDeleted( recipe : Recipe ) {
    val statement = getStatement(raw"UPDATE ${getCoreDatabaseName()}.${getRecipeDescriptionTableName()} " +
        " SET active = 'N' WHERE recipe_id = ?")
    statement.setInt(1, recipe.id.get)
    statement.execute()
  }

  /**
   * Creates a prepared to execute statement, depending on the action requested.
   */
  private final def prepareRecord( action : String, row : RecipeDescriptionRow ) : PreparedStatement = {

    // The final updating statement to be delivered
    var statement : PreparedStatement = null
    
    action match {
      case "insert" => {
        statement = getStatement(raw"INSERT INTO ${getCoreDatabaseName()}.${getRecipeDescriptionTableName()} " +
        " (`recipe_id`,`description`,`active`,`created_by`,`last_updated_by`) " +
        " VALUES(?, ?, ?, ?, ?)")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getRecipeDescriptionColumns().foreach { columnName => columnName match {
          case "id"                => "No actrion required"
          case "recipe_id"         => statement.setInt(1, row.recipe_id)
          case "description"       => statement.setString(2, row.description)
          case "active"            => statement.setString(3, "Y")
          case "created_by"        => statement.setString(4, row.created_by)
          case "created_date"      => "No action required"
          case "last_updated_by"   => statement.setString(5, row.last_updated_by)
          case "last_updated_date" => "No action required"
          case _ => error(s"Unknwon new table column: '$columnName'.")
        } }
      }
      case "update" => {
        statement = getStatement(raw"UPDATE ${getCoreDatabaseName()}.${getRecipeDescriptionTableName()} " +
        " SET `created_by` = ?, `last_updated_by` = ?, `active` = ?, `description` = ? " + 
        " WHERE `recipe_id` = ? ")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getRecipeDescriptionColumns().foreach { columnName => columnName match {
          case "created_by"        => statement.setString(1, row.created_by)
          case "last_updated_by"   => statement.setString(2, row.last_updated_by)
          case "active"            => statement.setString(3, "Y")
          case "description"       => statement.setString(4, row.description)
          case "recipe_id"         => statement.setInt(5, row.recipe_id)
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