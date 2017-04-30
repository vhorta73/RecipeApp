package com.app.recipe.Database.SQL.Core.Ingredient.Tables

import com.app.recipe.Database.SQL.Core.Ingredient.SQLIngredientTableAccess
import com.app.recipe.Model.Ingredient
import java.sql.PreparedStatement

/**
 * This class knows all there is to know about the ingredient attribute.
 * It is a flexible way to query the database and should not be used
 * directly by the front end.
 */
class IngredientAttribute() extends SQLIngredientTableAccess {
  
  /**
   * The row by id.
   */
  override def getRowId( id : Int ) : Option[IngredientAttributeRow] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getIngredientAttributeTableName()} WHERE id = ?")
    statement.setInt(1, id)
    getHashMapFromSQL( statement, getIngredientAttributeColumns() ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), getIngredientAttributeTableName()).asInstanceOf[IngredientAttributeRow])
      case _ => throw new IllegalStateException(s"Multiple primary key'd rows found for id '$id'.")
    }
  }

  /**
   * The row that match supplied name.
   */
  def getRowByName( name : String ) : Option[List[IngredientAttributeRow]] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getIngredientAttributeTableName()} WHERE name = ?")
    statement.setString(1, name)
    getHashMapFromSQL( statement, getIngredientAttributeColumns() ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[IngredientAttributeRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, getIngredientAttributeTableName()).asInstanceOf[IngredientAttributeRow]) ::: optionList
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
    if ( ingredient.attribute.isEmpty ) return None
    val ingredientAttributes: List[String] = ingredient.attribute.get
    var finalList : List[IngredientAttributeRow] = Nil

    // Set all ingredient attributes as deleted before inserting/updating
    setIngredientAttributeDeleted( ingredient )

    for ( ingredientAttribute <- ingredientAttributes ) {
      var ingredientAttributeRow : IngredientAttributeRow = IngredientAttributeRow(
          name = ingredientAttribute
        , last_updated_by = last_updated_by
      )

      // Insert first. Update next..
      try { prepareRecord("insert", ingredientAttributeRow).execute() } 
      catch { case duplicatedKey : Throwable => prepareRecord("update", ingredientAttributeRow).execute() }
      finalList = List(ingredientAttributeRow) ::: finalList
    }
    if ( finalList.size > 0 ) Some(finalList) else None
  }
  
  /**
   * Set all ingredient attributes not active for given ingredient_is.
   */
  private final def setIngredientAttributeDeleted( ingredient : Ingredient ) {
    val statement = getStatement(raw"UPDATE ${getCoreDatabaseName()}.${getIngredientAttributeTableName()} " +
        " SET active = 'N' WHERE id = ?")
    statement.setInt(1, 1)//ingredient.id.get)
    statement.execute()
  }

  /**
   * Creates a prepared to execute statement, depending on the action requested.
   */
  private final def prepareRecord( action : String, row : IngredientAttributeRow ) : PreparedStatement = {

    // The final updating statement to be delivered
    var statement : PreparedStatement = null

    action match {
      case "insert" => {
        statement = getStatement(raw"INSERT INTO ${getCoreDatabaseName()}.${getIngredientAttributeTableName()} " +
        " (`name`,`active`,`created_by`,`last_updated_by`) " +
        " VALUES(?, ?, ?, ?)")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getIngredientAttributeColumns().foreach { columnName => columnName match {
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
        statement = getStatement(raw"UPDATE ${getCoreDatabaseName()}.${getIngredientAttributeTableName()} " +
        " SET `created_by` = ?, `last_updated_by` = ?, `active` = ? " + 
        " WHERE `name` = ? ")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getIngredientAttributeColumns().foreach { columnName => columnName match {
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