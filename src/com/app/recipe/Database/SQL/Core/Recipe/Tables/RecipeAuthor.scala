package com.app.recipe.Database.SQL.Core.Recipe.Tables

import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeCore
import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeTableAccess
import com.app.recipe.Model.Recipe
import java.sql.PreparedStatement

/**
 * This class knows all there is to know about the recipe author
 * table structure. It is a flexible way to query the database and 
 * should not be used directly by the front end.
 */
class RecipeAuthor() extends SQLRecipeTableAccess {

  /**
   * The row by id.
   */
  override def getRowId( id : Int ) : Option[RecipeAuthorRow] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeAuthorTableName()} WHERE id = ? AND active = 'Y' ")
    statement.setInt(1, id)
    getHashMapFromSQL( statement, getRecipeAuthorColumns() ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), getRecipeAuthorTableName()).asInstanceOf[RecipeAuthorRow])
      case _ => throw new IllegalStateException(s"Multiple primary key'd rows found for id '$id'.")
    }
  }

  /**
   * The rows that match supplied recipe id.
   */
  def getRecipeId( id : Int ) : Option[List[RecipeAuthorRow]] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeAuthorTableName()} WHERE recipe_id = ? AND active = 'Y' ")
    statement.setInt(1, id)
    getHashMapFromSQL( statement, getRecipeAuthorColumns() ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[RecipeAuthorRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, getRecipeAuthorTableName()).asInstanceOf[RecipeAuthorRow]) ::: optionList
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
    if ( recipe.author.isEmpty ) return None
    val authors : List[String] = recipe.author.get
    var finalList : List[RecipeAuthorRow] = Nil

    // Set all authors as deleted before inserting/updating
    setAuthorsDeleted( recipe )
    
    for( author <- authors ) {
      var recipeAuthor : RecipeAuthorRow = RecipeAuthorRow(
          recipe_id = if (recipe.id.isDefined ) recipe.id.get else throw new IllegalStateException(s"No recipe_id supplied in $recipe")
        , author    = if (recipe.author.isDefined ) author.toString() else ""
        , last_updated_by = last_updated_by
      )

      // Insert first. Update next..
      try { prepareRecord("insert", recipeAuthor).execute() } 
      catch { case duplicatedKey : Throwable => prepareRecord("update",recipeAuthor).execute() }
      finalList = List(recipeAuthor)::: finalList

    }
    if ( finalList.size > 0 ) Some(finalList) else None
  }
  
  /**
   * Set all authors not active for given recipe_id.
   */
  private final def setAuthorsDeleted( recipe : Recipe ) {
    val statement = getStatement(raw"UPDATE ${getCoreDatabaseName()}.${getRecipeAuthorTableName()} " +
        " SET active = 'N' WHERE recipe_id = ?")
    statement.setInt(1, recipe.id.get)
    statement.execute()
  }

  /**
   * Creates a prepared to execute statement, depending on the action requested.
   */
  private final def prepareRecord( action : String, row : RecipeAuthorRow ) : PreparedStatement = {

    // The final updating statement to be delivered
    var statement : PreparedStatement = null
    
    action match {
      case "insert" => {
        statement = getStatement(raw"INSERT INTO ${getCoreDatabaseName()}.${getRecipeAuthorTableName()} " +
        " (`recipe_id`,`author`,`active`,`created_by`,`last_updated_by`) " +
        " VALUES(?, ?, ?, ?, ?)")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getRecipeAuthorColumns().foreach { columnName => columnName match {
          case "id"                => "No actrion required"
          case "recipe_id"         => statement.setInt(1, row.recipe_id)
          case "author"            => statement.setString(2, row.author)
          case "active"            => statement.setString(3, "Y")
          case "created_by"        => statement.setString(4, row.created_by)
          case "created_date"      => "No action required"
          case "last_updated_by"   => statement.setString(5, row.last_updated_by)
          case "last_updated_date" => "No action required"
          case _ => throw new IllegalStateException(s"Unknwon new table column: '$columnName'.")
        } }
      }
      case "update" => {
        statement = getStatement(raw"UPDATE ${getCoreDatabaseName()}.${getRecipeAuthorTableName()} " +
        " SET `created_by` = ?, `last_updated_by` = ?, `active` = ? " + 
        " WHERE `recipe_id` = ? AND `author` = ? ")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getRecipeAuthorColumns().foreach { columnName => columnName match {
          case "created_by"        => statement.setString(1, row.created_by)
          case "last_updated_by"   => statement.setString(2, row.last_updated_by)
          case "active"            => statement.setString(3, "Y")
          case "recipe_id"         => statement.setInt(4, row.recipe_id)
          case "author"            => statement.setString(5, row.author)
          case "created_date"      => "No action required"
          case "last_updated_date" => "No action required"
          case "id"                => "No action required"
          case _ => throw new IllegalStateException(s"Unknwon new table column: '$columnName'.")
        } }
      }
    }
    println(statement)
    statement
  }
}