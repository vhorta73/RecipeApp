package com.app.recipe.Database.SQL.Core.Recipe.Tables

import java.sql.PreparedStatement

import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeTableAccess
import com.app.recipe.Model.Recipe

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
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeNameTableName()} WHERE id = ? ")
    statement.setInt(1,id)

    getHashMapFromSQL( statement, getRecipeNameColumns() ) match {
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
  def getRecipeByName( name : String ) : Option[List[RecipeNameRow]] = {
    val getNameStatement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeNameTableName()} WHERE name = ? ")
    getNameStatement.setString(1,name)
    
    getHashMapFromSQL( getNameStatement, getRecipeNameColumns() ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[RecipeNameRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, getRecipeNameTableName()).asInstanceOf[RecipeNameRow]) ::: optionList
        }
        if ( optionList.size == 0 ) None else Some(optionList)
      }
    }
  }

  /**
   * The Recipe by name and version.
   */
  def getRecipeByNameAndVersion( name : String, version : Int ) : Option[RecipeNameRow] = {
    val getNameVersionStatement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getRecipeNameTableName()} WHERE name = ? AND version = ?")
    getNameVersionStatement.setString(1,name)
    getNameVersionStatement.setInt(2,version)

    getHashMapFromSQL( getNameVersionStatement, getRecipeNameColumns() ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), getRecipeNameTableName()).asInstanceOf[RecipeNameRow])
      case _ => throw new IllegalStateException(s"Multiple rows for same name and version found: name '$name', version '$version'.")
    }
  }

  /**
   * Creates a new record with information supplied. If the record already 
   * exists for the unique key supplied, it will apply an update instead.
   * Returns true if inserted and false if updated.
   * 
   * @param recipe
   * @return Option[Recipe] 
   */
  override def saveRecord( recipe : Recipe ) : Option[List[TableRow]] = {
    var recipeName : RecipeNameRow = RecipeNameRow(
        // If id is not supplied, allow the system to work it out.
          id      = if (recipe.id.isDefined ) recipe.id.get else 0
        , name    = recipe.name.get
        , version = recipe.version.get
        , last_updated_by = last_updated_by
    )

    // Insert first. Update next..
    try { prepareRecord("insert", recipeName).execute() } 
    catch { case duplicatedKey : Throwable => prepareRecord("update",recipeName).execute() }

    Some(List(getRecipeByNameAndVersion(recipeName.name, recipeName.version).get))
  }

  /**
   * Creates a prepared to execute statement, depending on the action requested.
   */
  private final def prepareRecord( action : String, row : RecipeNameRow ) : PreparedStatement = {
    val recipe : RecipeNameRow = row.asInstanceOf[RecipeNameRow]

    // The final updating statement to be delivered
    var statement : PreparedStatement = null
    
    action match {
      case "insert" => {
        statement = getStatement(raw"INSERT INTO ${getCoreDatabaseName()}.${getRecipeNameTableName()} " +
        " (`id`,`name`,`version`,`created_by`,`last_updated_by`) " +
        " VALUES(?, ?, ?, ?, ?)")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getRecipeNameColumns().foreach { columnName => columnName match {
          case "id"                => statement.setInt(1, recipe.id)
          case "name"              => statement.setString(2, recipe.name)
          case "version"           => statement.setInt(3, recipe.version)
          case "created_by"        => statement.setString(4, recipe.created_by)
          case "created_date"      => "No action required"
          case "last_updated_by"   => statement.setString(5, recipe.last_updated_by)
          case "last_updated_date" => "No action required"
          case _ => throw new IllegalStateException(s"Unknwon new table column: '$columnName'.")
        } }
      }
      case "update" => {
        statement = getStatement(raw"UPDATE ${getCoreDatabaseName()}.${getRecipeNameTableName()} " +
        " SET `name` = ?, `version` = ?, `created_by` = ?, `last_updated_by` = ? " + 
        " WHERE `id` = ? ")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getRecipeNameColumns().foreach { columnName => columnName match {
          case "name"              => statement.setString(1, recipe.name)
          case "version"           => statement.setInt(2, recipe.version)
          case "created_by"        => statement.setString(3, recipe.created_by)
          case "created_date"      => "No action required"
          case "last_updated_by"   => statement.setString(4, recipe.last_updated_by)
          case "last_updated_date" => "No action required"
          case "id"                => statement.setInt(5, recipe.id)
          case _ => throw new IllegalStateException(s"Unknwon new table column: '$columnName'.")
        } }
      }
    }
    statement
  }
}