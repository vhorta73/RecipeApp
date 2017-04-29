package com.app.recipe.Database.SQL.Core.Recipe.Tables

import scala.util.Random

import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeCore
import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeTableAccess
import com.app.recipe.Database.SQL.SQLDatabaseHandle
import com.app.recipe.Model.Recipe
import java.sql.PreparedStatement
import com.app.recipe.Model.Duration


/**
 * This class knows all there is to know about the recipe duration
 * table structure. It is a flexible way to query the database and 
 * should not be used directly by the front end.
 */
class RecipeDuration() extends SQLRecipeTableAccess {

  /**
   * The row by id.
   */
  override def getRowId( id : Int ) : Option[RecipeDurationRow] = {
    val getIdStatement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeDurationTableName()} WHERE id = ?")
    getIdStatement.setInt(1,id)
    getHashMapFromSQL( getIdStatement, getRecipeDurationColumns() ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), getRecipeDurationTableName()).asInstanceOf[RecipeDurationRow])
      case _ => throw new IllegalStateException(s"Multiple primary key'd rows found for id '$id'.")
    }
  }

  /**
   * The rows that match supplied recipe id.
   */
  override def getRecipeId( id : Int ) : Option[List[RecipeDurationRow]] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeDurationTableName()} WHERE recipe_id = ?" )
    statement.setInt(1,id)
    getHashMapFromSQL( statement, getRecipeDurationColumns() ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[RecipeDurationRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, getRecipeDurationTableName()).asInstanceOf[RecipeDurationRow]) ::: optionList
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
    if ( recipe.duration.isEmpty ) return None
    val recipeDuration: List[Duration] = recipe.duration.get.asInstanceOf[List[Duration]]
    var finalList : List[RecipeDurationRow] = Nil

    // Set all recipe duration as deleted before inserting/updating
    setDurationDeleted( recipe )
    
    for( rDuration <- recipeDuration ) {
      var recipeDurationRow : RecipeDurationRow = RecipeDurationRow(
          recipe_id  = if (recipe.id.isDefined ) recipe.id.get else throw new IllegalStateException(s"No recipe_id supplied in $recipe")
        , Type       = rDuration.durationType
        , duration   = rDuration.duration 
        , last_updated_by = last_updated_by
      )

      // Insert first. Update next..
      try { prepareRecord("insert", recipeDurationRow).execute() } 
      catch { case duplicatedKey : Throwable => prepareRecord("update",recipeDurationRow).execute() }
      finalList = List(recipeDurationRow)::: finalList
    }
    if ( finalList.size > 0 ) Some(finalList) else None
  }
  
  /**
   * Set all recipe duration not active for given recipe_id.
   */
  private final def setDurationDeleted( recipe : Recipe ) {
    val statement = getStatement(raw"UPDATE ${getCoreDatabaseName()}.${getRecipeDurationTableName()} " +
        " SET active = 'N' WHERE recipe_id = ?")
    statement.setInt(1, recipe.id.get)
    statement.execute()
  }

  /**
   * Creates a prepared to execute statement, depending on the action requested.
   */
  private final def prepareRecord( action : String, row : RecipeDurationRow ) : PreparedStatement = {

    // The final updating statement to be delivered
    var statement : PreparedStatement = null
    
    action match {
      case "insert" => {
        statement = getStatement(raw"INSERT INTO ${getCoreDatabaseName()}.${getRecipeDurationTableName()} " +
        " (`recipe_id`,`type`,`duration`,`active`,`created_by`,`last_updated_by`) " +
        " VALUES(?, ?, ?, ?, ?, ?)")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getRecipeDurationColumns().foreach { columnName => columnName match {
          case "id"                => "No actrion required"
          case "recipe_id"         => statement.setInt(1, row.recipe_id)
          case "type"              => statement.setString(2, row.Type)
          case "duration"          => statement.setTime(3, row.duration)
          case "active"            => statement.setString(4, "Y")
          case "created_by"        => statement.setString(5, row.created_by)
          case "created_date"      => "No action required"
          case "last_updated_by"   => statement.setString(6, row.last_updated_by)
          case "last_updated_date" => "No action required"
          case _ => error(s"Unknwon new table column: '$columnName'.")
        } }
      }
      case "update" => {
        statement = getStatement(raw"UPDATE ${getCoreDatabaseName()}.${getRecipeDurationTableName()} " +
        " SET `created_by` = ?, `last_updated_by` = ?, `active` = ?, `duration` = ? " + 
        " WHERE `recipe_id` = ? AND `type` = ? ")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getRecipeDurationColumns().foreach { columnName => columnName match {
          case "created_by"        => statement.setString(1, row.created_by)
          case "last_updated_by"   => statement.setString(2, row.last_updated_by)
          case "active"            => statement.setString(3, "Y")
          case "duration"          => statement.setTime(4, row.duration)
          case "recipe_id"         => statement.setInt(5, row.recipe_id)
          case "type"              => statement.setString(6, row.Type)
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