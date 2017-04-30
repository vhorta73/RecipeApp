package com.app.recipe.Database.SQL.Core.Ingredient.Tables

import java.sql.PreparedStatement

import com.app.recipe.Database.SQL.Core.Ingredient.SQLIngredientTableAccess
import com.app.recipe.Model.Ingredient

/**
 * This class knows all there is to know about the ingredient attribute.
 * It is a flexible way to query the database and should not be used
 * directly by the front end.
 */
class IngredientSource() extends SQLIngredientTableAccess {
  
  /**
   * The row by id.
   */
  override def getRowId( id : Int ) : Option[IngredientSourceRow] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getIngredientSourceTableName()} WHERE id = ?")
    statement.setInt(1, id)
    getHashMapFromSQL( statement, getIngredientSourceColumns() ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), getIngredientSourceTableName()).asInstanceOf[IngredientSourceRow])
      case _ => throw new IllegalStateException(s"Multiple primary key'd rows found for id '$id'.")
    }
  }

  /**
   * The row that match supplied name.
   */
  def getRowByName( name : String ) : Option[List[IngredientSourceRow]] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getIngredientSourceTableName()} WHERE name = ?")
    statement.setString(1, name)
    getHashMapFromSQL( statement, getIngredientSourceColumns() ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[IngredientSourceRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, getIngredientSourceTableName()).asInstanceOf[IngredientSourceRow]) ::: optionList
        }
        if ( optionList.size == 0 ) None else Some(optionList)
      }
    }
  }

  /**
   * Creates a new record with information supplied or updates existing row/rows
   * for given ingredient otherwise. Returns true if inserted, false if updated.
   * 
   * @param ingredient
   * @return Option[List[TableRow]] 
   */
  override def saveRecord( ingredient : Ingredient ) : Option[List[IngredientTableRow]] = {
    if ( ingredient.source.isEmpty ) return None
    val ingredientSources: List[String] = ingredient.source.get
    var finalList : List[IngredientSourceRow] = Nil

    // Set all ingredient source as deleted before inserting/updating
    setIngredientSourceDeleted( ingredient )

    for ( ingredientSource <- ingredientSources ) {
      var ingredientSourceRow : IngredientSourceRow = IngredientSourceRow(
          name = ingredientSource
        , last_updated_by = last_updated_by
      )

      // Insert first. Update next..
      try { prepareRecord("insert", ingredientSourceRow).execute() } 
      catch { case duplicatedKey : Throwable => prepareRecord("update", ingredientSourceRow).execute() }
      finalList = List(ingredientSourceRow) ::: finalList
    }
    if ( finalList.size > 0 ) Some(finalList) else None
  }
  
  /**
   * Set all ingredient sources not active for given ingredient_id.
   */
  private final def setIngredientSourceDeleted( ingredient : Ingredient ) {
    val statement = getStatement(raw"UPDATE ${getCoreDatabaseName()}.${getIngredientSourceTableName()} " +
        " SET active = 'N' WHERE id = ?")
    statement.setInt(1, 1)//ingredient.id.get)
    statement.execute()
  }

  /**
   * Creates a prepared to execute statement, depending on the action requested.
   */
  private final def prepareRecord( action : String, row : IngredientSourceRow ) : PreparedStatement = {

    // The final updating statement to be delivered
    var statement : PreparedStatement = null

    action match {
      case "insert" => {
        statement = getStatement(raw"INSERT INTO ${getCoreDatabaseName()}.${getIngredientSourceTableName()} " +
        " (`name`,`active`,`created_by`,`last_updated_by`) " +
        " VALUES(?, ?, ?, ?)")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getIngredientSourceColumns().foreach { columnName => columnName match {
          case "id"                => "No actrion required"
          case "name"              => statement.setString(1, row.name)
          case "active"            => statement.setString(2, "Y")
          case "created_by"        => statement.setString(3, row.created_by)
          case "created_date"      => "No action required"
          case "last_updated_by"   => statement.setString(4, row.last_updated_by)
          case "last_updated_date" => "No action required"
          case _ => error(s"Unknwon new table column: '$columnName'.")
        } }
      }
      case "update" => {
        statement = getStatement(raw"UPDATE ${getCoreDatabaseName()}.${getIngredientSourceTableName()} " +
        " SET `created_by` = ?, `last_updated_by` = ?, `active` = ? " + 
        " WHERE `name` = ? ")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getIngredientSourceColumns().foreach { columnName => columnName match {
          case "created_by"        => statement.setString(1, row.created_by)
          case "last_updated_by"   => statement.setString(2, row.last_updated_by)
          case "active"            => statement.setString(3, "Y")
          case "name"              => statement.setString(4, row.name)
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