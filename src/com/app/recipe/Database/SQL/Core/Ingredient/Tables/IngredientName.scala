package com.app.recipe.Database.SQL.Core.Ingredient.Tables

import java.sql.PreparedStatement

import com.app.recipe.Database.SQL.Core.Ingredient.SQLIngredientTableAccess
import com.app.recipe.Model.Ingredient

/**
 * This class knows all there is to know about the ingredient structure.
 * It is a flexible way to query the database and should not be used
 * directly by the front end.
 */
class IngredientName() extends SQLIngredientTableAccess {
  
  /**
   * The row by id.
   */
  override def getRowId( id : Int ) : Option[IngredientNameRow] = {
    println(id)
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getIngredientNameTableName()} WHERE id = ? ")
    statement.setInt(1,id)

    getHashMapFromSQL( statement, getIngredientNameColumns() ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), getIngredientNameTableName()).asInstanceOf[IngredientNameRow])
      case _ => throw new IllegalStateException(s"Multiple primary key'd rows found for id '$id'.")
    }
  }

//  /**
//   * Overriding the default getIngredientId that is used across all the other core tables.
//   */
//  override def getIngredientId( id : Int ) : Option[List[IngredientNameRow]] = getRowId(id) match {
//    case None => None
//    case result => Some(List(result.get))
//  }

  /**
   * The Ingredient by name. One row expected.
   */
  def getIngredientByName( name : String ) : Option[List[IngredientNameRow]] = {
    val getNameStatement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getIngredientNameTableName()} WHERE name = ? ")
    getNameStatement.setString(1,name)

    getHashMapFromSQL( getNameStatement, getIngredientNameColumns() ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[IngredientNameRow] = List()
        for( row <- result ) {
          println(s"ROW: $row")
          optionList = List(getObject(row, getIngredientNameTableName()).asInstanceOf[IngredientNameRow]) ::: optionList
        }
        if ( optionList.size == 0 ) None else Some(optionList)
      }
    }
  }

  /**
   * Creates a new record with information supplied. If the record already 
   * exists for the unique key supplied, it will apply an update instead.
   * Returns true if inserted and false if updated.
   * 
   * @param ingredient
   * @return Option[Ingredient] 
   */
  override def saveRecord( ingredient : Ingredient ) : Option[List[IngredientTableRow]] = {
    var r : Option[Ingredient] = Some(ingredient)
    var ingredientId : Int = 0
    if ( ingredient.id.isEmpty && ingredient.name.isDefined ) {
      var ingredientByName = getIngredientByName(ingredient.name.get)
      if ( ingredientByName.isDefined ) ingredientId = ingredientByName.get(0).id
    }

    var ingredientName : IngredientNameRow = IngredientNameRow(
        // If id is not supplied, allow the system to work it out.
          id      = if ( ingredient.id.isDefined ) ingredient.id.get else ingredientId
        , name    = ingredient.name.get
        , last_updated_by = last_updated_by
    )
    println(s"Ingredient Name: $ingredientName")

    // Insert first. Update next..
    try { prepareRecord("insert", ingredientName).execute() } 
    catch { case duplicatedKey : Throwable => prepareRecord("update",ingredientName).execute() }

    getIngredientByName(ingredientName.name)
  }

  /**
   * Creates a prepared to execute statement, depending on the action requested.
   */
  private final def prepareRecord( action : String, row : IngredientNameRow ) : PreparedStatement = {
    val ingredient : IngredientNameRow = row.asInstanceOf[IngredientNameRow]

    // The final updating statement to be delivered
    var statement : PreparedStatement = null
    
    action match {
      case "insert" => {
        statement = getStatement(raw"INSERT INTO ${getCoreDatabaseName()}.${getIngredientNameTableName()} " +
        " (`name`,`active`,`created_by`,`last_updated_by`) " +
        " VALUES(?, ?, ?, ?)")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getIngredientNameColumns().foreach { columnName => columnName match {
          case "name"              => statement.setString(1, ingredient.name)
          case "active"            => statement.setString(2, "Y")
          case "created_by"        => statement.setString(3, ingredient.created_by)
          case "created_date"      => "No action required"
          case "last_updated_by"   => statement.setString(4, ingredient.last_updated_by)
          case "last_updated_date" => "No action required"
          case "id"                => "No action required"
          case _ => throw new IllegalStateException(s"Unknwon new table column: '$columnName'.")
        } }
      }
      case "update" => {
        statement = getStatement(raw"UPDATE ${getCoreDatabaseName()}.${getIngredientNameTableName()} " +
        " SET `created_by` = ?, `last_updated_by` = ?, `active` = ? " + 
        " WHERE `name` = ? ")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getIngredientNameColumns().foreach { columnName => columnName match {
          case "created_by"        => statement.setString(1, ingredient.created_by)
          case "created_date"      => "No action required"
          case "last_updated_by"   => statement.setString(2, ingredient.last_updated_by)
          case "active"            => statement.setString(3, "Y")
          case "last_updated_date" => "No action required"
          case "name"              => statement.setString(4, ingredient.name)
          case "id"                => "No action required"
          case _ => throw new IllegalStateException(s"Unknwon new table column: '$columnName'.")
        } }
      }
    }
    statement
  }
}