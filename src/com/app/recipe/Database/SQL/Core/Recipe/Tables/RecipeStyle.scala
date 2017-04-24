package com.app.recipe.Database.SQL.Core.Recipe.Tables

import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeCore
import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeTableAccess
import scala.util.Random
import com.app.recipe.Model.Recipe
import java.sql.PreparedStatement

/**
 * This class knows all there is to know about the recipe style
 * table structure. It is a flexible way to query the database and 
 * should not be used directly by the front end.
 */
class RecipeStyle() extends SQLRecipeTableAccess {

  /**
   * The row by id.
   */
  override def getRowId( id : Int ) : Option[RecipeStyleRow] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeStyleTableName()} WHERE id = ? AND active = 'Y' ")
    statement.setInt(1, id)
    getHashMapFromSQL( statement, getRecipeStyleColumns() ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), getRecipeStyleTableName()).asInstanceOf[RecipeStyleRow])
      case _ => throw new IllegalStateException(s"Multiple primary key'd rows found for id '$id'.")
    }
  }

  /**
   * The rows that match supplied recipe id.
   */
  def getRecipeId( id : Int ) : Option[List[RecipeStyleRow]] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeStyleTableName()} WHERE recipe_id = ? AND active = 'Y' ")
    statement.setInt(1, id)
    getHashMapFromSQL( statement, getRecipeStyleColumns() ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[RecipeStyleRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, getRecipeStyleTableName()).asInstanceOf[RecipeStyleRow]) ::: optionList
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
    if ( recipe.recipeStyle.isEmpty ) return None
    val recipeStyles: List[String] = recipe.recipeStyle.get
    var finalList : List[RecipeStyleRow] = Nil

    // Set all recipe types as deleted before inserting/updating
    setStylesDeleted( recipe )
    
    for( recipeStyle <- recipeStyles ) {
      var recipeStyleRow : RecipeStyleRow = RecipeStyleRow(
          recipe_id = if (recipe.id.isDefined ) recipe.id.get else throw new IllegalStateException(s"No recipe_id supplied in $recipe")
        , style     = if (recipe.recipeStyle.isDefined ) recipeStyle.toString() else ""
        , last_updated_by = last_updated_by
      )

      // Insert first. Update next..
      try { prepareRecord("insert", recipeStyleRow).execute() } 
      catch { case duplicatedKey : Throwable => prepareRecord("update",recipeStyleRow).execute() }
      finalList = List(recipeStyleRow)::: finalList
    }
    if ( finalList.size > 0 ) Some(finalList) else None
  }
  
  /**
   * Set all recipe types not active for given recipe_id.
   */
  private final def setStylesDeleted( recipe : Recipe ) {
    val statement = getStatement(raw"UPDATE ${getCoreDatabaseName()}.${getRecipeStyleTableName()} " +
        " SET active = 'N' WHERE recipe_id = ?")
    statement.setInt(1, recipe.id.get)
    statement.execute()
  }

  /**
   * Creates a prepared to execute statement, depending on the action requested.
   */
  private final def prepareRecord( action : String, row : RecipeStyleRow ) : PreparedStatement = {

    // The final updating statement to be delivered
    var statement : PreparedStatement = null
    
    action match {
      case "insert" => {
        statement = getStatement(raw"INSERT INTO ${getCoreDatabaseName()}.${getRecipeStyleTableName()} " +
        " (`recipe_id`,`style`,`active`,`created_by`,`last_updated_by`) " +
        " VALUES(?, ?, ?, ?, ?)")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getRecipeStyleColumns().foreach { columnName => columnName match {
          case "id"                => "No actrion required"
          case "recipe_id"         => statement.setInt(1, row.recipe_id)
          case "style"             => statement.setString(2, row.style)
          case "active"            => statement.setString(3, "Y")
          case "created_by"        => statement.setString(4, row.created_by)
          case "created_date"      => "No action required"
          case "last_updated_by"   => statement.setString(5, row.last_updated_by)
          case "last_updated_date" => "No action required"
          case _ => error(s"Unknwon new table column: '$columnName'.")
        } }
      }
      case "update" => {
        statement = getStatement(raw"UPDATE ${getCoreDatabaseName()}.${getRecipeStyleTableName()} " +
        " SET `created_by` = ?, `last_updated_by` = ?, `active` = ? " + 
        " WHERE `recipe_id` = ? AND `style` = ? ")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getRecipeStyleColumns().foreach { columnName => columnName match {
          case "created_by"        => statement.setString(1, row.created_by)
          case "last_updated_by"   => statement.setString(2, row.last_updated_by)
          case "active"            => statement.setString(3, "Y")
          case "recipe_id"         => statement.setInt(4, row.recipe_id)
          case "style"             => statement.setString(5, row.style)
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