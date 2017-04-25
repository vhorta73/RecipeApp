package com.app.recipe.Database.SQL.Core.Recipe.Tables

import java.sql.PreparedStatement

import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeTableAccess
import com.app.recipe.Model.Recipe
import com.app.recipe.Model.Stage

/**
 * This class knows all there is to know about the recipe stage
 * table structure. It is a flexible way to query the database and 
 * should not be used directly by the front end.
 */
class RecipeStage() extends SQLRecipeTableAccess {

  /**
   * The row by id.
   */
  override def getRowId( id : Int ) : Option[RecipeStageRow] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeStageTableName()} WHERE id = ? ")
    statement.setInt(1, id)
    getHashMapFromSQL( statement, getRecipeStageColumns() ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), getRecipeStageTableName()).asInstanceOf[RecipeStageRow])
      case _ => throw new IllegalStateException(s"Multiple primary key'd rows found for id '$id'.")
    }
  }

  /**
   * The rows that match supplied recipe id.
   */
  def getRecipeId( id : Int ) : Option[List[RecipeStageRow]] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeStageTableName()} WHERE recipe_id = ?")
    statement.setInt(1, id)
    getHashMapFromSQL( statement, getRecipeStageColumns() ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[RecipeStageRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, getRecipeStageTableName()).asInstanceOf[RecipeStageRow]) ::: optionList
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
    if ( recipe.stages.isEmpty ) return None
    val recipeStages: List[Stage] = recipe.stages.get.asInstanceOf[List[Stage]]
    var finalList : List[RecipeStageRow] = Nil

    // Set all recipe stages as deleted before inserting/updating
    setStagesDeleted( recipe )
    
    for( recipeStage <- recipeStages ) {
      var recipeStageRow : RecipeStageRow = RecipeStageRow(
          recipe_id  = if (recipe.id.isDefined ) recipe.id.get else throw new IllegalStateException(s"No recipe_id supplied in $recipe")
        , step_id           = if (recipe.stages.isDefined ) recipeStage.stepId else 0
        , step_name         = if (recipe.stages.isDefined ) recipeStage.stepName else ""
        , description       = if (recipe.stages.isDefined ) recipeStage.stepDescription else ""
        , last_updated_by = last_updated_by
      )

      // Insert first. Update next..
      try { prepareRecord("insert", recipeStageRow).execute() } 
      catch { case duplicatedKey : Throwable => prepareRecord("update",recipeStageRow).execute() }
      finalList = List(recipeStageRow)::: finalList
    }
    if ( finalList.size > 0 ) Some(finalList) else None
  }
  
  /**
   * Set all recipe stage not active for given recipe_id.
   */
  private final def setStagesDeleted( recipe : Recipe ) {
    val statement = getStatement(raw"UPDATE ${getCoreDatabaseName()}.${getRecipeStageTableName()} " +
        " SET active = 'N' WHERE recipe_id = ?")
    statement.setInt(1, recipe.id.get)
    statement.execute()
  }

  /**
   * Creates a prepared to execute statement, depending on the action requested.
   */
  private final def prepareRecord( action : String, row : RecipeStageRow ) : PreparedStatement = {

    // The final updating statement to be delivered
    var statement : PreparedStatement = null
    
    action match {
      case "insert" => {
        statement = getStatement(raw"INSERT INTO ${getCoreDatabaseName()}.${getRecipeStageTableName()} " +
        " (`recipe_id`,`step_id`,`step_name`,`description`,`active`,`created_by`,`last_updated_by`) " +
        " VALUES(?, ?, ?, ?, ?, ?, ?)")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getRecipeStageColumns().foreach { columnName => columnName match {
          case "id"                => "No actrion required"
          case "recipe_id"         => statement.setInt(1, row.recipe_id)
          case "step_id"           => statement.setInt(2, row.step_id)
          case "step_name"         => statement.setString(3, row.step_name)
          case "description"       => statement.setString(4, row.description)
          case "active"            => statement.setString(5, "Y")
          case "created_by"        => statement.setString(6, row.created_by)
          case "created_date"      => "No action required"
          case "last_updated_by"   => statement.setString(7, row.last_updated_by)
          case "last_updated_date" => "No action required"
          case _ => error(s"Unknwon new table column: '$columnName'.")
        } }
      }
      case "update" => {
        statement = getStatement(raw"UPDATE ${getCoreDatabaseName()}.${getRecipeStageTableName()} " +
        " SET `created_by` = ?, `last_updated_by` = ?, `active` = ?, `step_name` = ?, `description` = ? " + 
        " WHERE `recipe_id` = ? AND `step_id` = ? ")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getRecipeStageColumns().foreach { columnName => columnName match {
          case "created_by"        => statement.setString(1, row.created_by)
          case "last_updated_by"   => statement.setString(2, row.last_updated_by)
          case "active"            => statement.setString(3, "Y")
          case "step_name"         => statement.setString(4, row.step_name)
          case "description"       => statement.setString(5, row.description)
          case "recipe_id"         => statement.setInt(6, row.recipe_id)
          case "step_id"           => statement.setInt(7, row.step_id)
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