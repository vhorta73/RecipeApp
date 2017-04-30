package com.app.recipe.Database.SQL.Core.Ingredient.Tables

import java.sql.PreparedStatement

import com.app.recipe.Database.SQL.Core.Ingredient.SQLIngredientTableAccess
import com.app.recipe.Model.Ingredient

/**
 * This class knows all there is to know about the ingredient sources and
 * attributes which are split by type in the ingredient core table.
 * It is a flexible way to query the database and should not be used
 * directly by the front end.
 */
class IngredientCore() extends SQLIngredientTableAccess {
  
  /**
   * The attribute type name identifying attribute rows.
   */
  private final val ATTRIBUTE_TYPE = "attribute"
  
  /**
   * The source type name which identifies a source row.
   */
  private final val SOURCE_TYPE = "source"

  /**
   * The ingredient attribute table access handle for name to id conversion.
   */
  private final val attributeDB = new IngredientAttribute

  /**
   * The ingredient source table access handle for name to id conversion.
   */
  private final val sourceDB = new IngredientSource

  /**
   * The row by id.
   */
  override def getRowId( id : Int ) : Option[IngredientCoreRow] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getIngredientCoreTableName()} WHERE id = ?")
    statement.setInt(1, id)
    getHashMapFromSQL( statement, getIngredientCoreColumns() ) match {
      case result if result.size == 0 => None
      case result if result.size == 1 => Some(getObject(result(0), getIngredientCoreTableName()).asInstanceOf[IngredientCoreRow])
      case _ => throw new IllegalStateException(s"Multiple primary key'd rows found for id '$id'.")
    }
  }

  /**
   * The rows that match supplied ingredient id.
   */
  def getRowByIngredientId( id : Int ) : Option[List[IngredientCoreRow]] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getIngredientCoreTableName()} WHERE ingredient_id = ?")
    statement.setInt(1, id)
    getHashMapFromSQL( statement, getIngredientCoreColumns() ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[IngredientCoreRow] = List()
        for( row <- result ) {
          optionList = List(getObject(row, getIngredientCoreTableName()).asInstanceOf[IngredientCoreRow]) ::: optionList
        }
        if ( optionList.size == 0 ) None else Some(optionList)
      }
    }
  }

  /**
   * The attribute rows that match supplied ingredient id.
   */
  def getAttributeNamesForIngredientId( id : Int ) : Option[List[String]] = getNamesForIngredientId(ATTRIBUTE_TYPE, id)
  
  /**
   * The source rows that match supplied ingredient id.
   */
  def getSourceNamesForIngredientId( id : Int ) : Option[List[String]] = getNamesForIngredientId(SOURCE_TYPE, id)

  /**
   * Retrieving the string list of types as per request.
   */
  private final def getNamesForIngredientId( xType : String, id : Int ) : Option[List[String]] = {
    val statement = getStatement(raw"SELECT * FROM ${getCoreDatabaseName()}.${getIngredientCoreTableName()} WHERE ingredient_id = ? AND type = ? ")
    statement.setInt(1, id)
    statement.setString(2, xType)
    getHashMapFromSQL( statement, getIngredientCoreColumns() ) match {
      case result if result.size == 0 => None
      case result => {
        var optionList : List[String] = List()
        for( row <- result ) {
          var typeId = getObject(row, getIngredientCoreTableName()).asInstanceOf[IngredientCoreRow].type_id
          var typeName = xType match {
            case x if x.equals(ATTRIBUTE_TYPE) => attributeDB.getRowId(typeId).get.name 
            case x if x.equals(SOURCE_TYPE)    => sourceDB.getRowId(typeId).get.name
            case _ => throw new IllegalStateException(s"Unknown type '$xType'")
          }
          optionList = List(typeName) ::: optionList
        }
        if ( optionList.size == 0 ) None else Some(optionList)
      }
    }
  }
    

  /**
   * Creates a new record with information supplied or updates existing row/rows
   * for given ingredient otherwise.
   * 
   * @param ingredient
   * @return Option[List[TableRow]] 
   */
  override def saveRecord( ingredient : Ingredient ) : Option[List[IngredientTableRow]] = {
    // If no attributes or source found, nothing needs done.
    if ( ingredient.source.isEmpty && ingredient.attribute.isEmpty ) return None

    // Instantiate the final list to be returned
    var finalList : List[IngredientTableRow] = Nil
    
    // Save the sources if we have any
    if ( ingredient.source.isDefined ) finalList = saveSources(ingredient) ::: finalList 
    
    // Save the attributes if we have any
    if ( ingredient.attribute.isDefined ) finalList = saveAttributes(ingredient) ::: finalList

    if ( finalList.size > 0 ) Some(finalList) else None
  }

  /**
   * Saving only attributes to the ingredient core table.
   */
  private final def saveAttributes( ingredient : Ingredient ) : List[IngredientTableRow] = {
    if ( ingredient.attribute.isEmpty ) return Nil
    val ingredientAttributes : List[String] = ingredient.attribute.get
    var finalList : List[IngredientCoreRow] = Nil

    // Set all ingredient attributes as deleted before inserting/updating
    setIngredientAttributesDeleted( ingredient )

    for ( ingredientAttribute <- ingredientAttributes ) {
      var attributeOptionRow = attributeDB.getRowByName(ingredientAttribute)
      // If the attribute is not known, ignore it.
      if ( attributeOptionRow.isDefined ) {
        val attributeRow = attributeOptionRow.get(0)
        var ingredientCoreRow : IngredientCoreRow = IngredientCoreRow(
            ingredient_id = ingredient.id.get
          , Type          = ATTRIBUTE_TYPE
          , type_id       = attributeRow.id
          , last_updated_by = last_updated_by
        )

        // Insert first. Update next..
        try { prepareRecord("insert", ingredientCoreRow).execute() } 
        catch { case duplicatedKey : Throwable => prepareRecord("update", ingredientCoreRow).execute() }
        finalList = List(ingredientCoreRow) ::: finalList
      } else info(s"Attribute $ingredientAttribute not found")
    }
    if ( finalList.size > 0 ) finalList else Nil
  }

  /**
   * Saving the ingredient sources.
   */
  private final def saveSources( ingredient : Ingredient ) : List[IngredientTableRow] = {
    if ( ingredient.source.isEmpty ) return Nil
    val ingredientSources : List[String] = ingredient.source.get
    var finalList : List[IngredientCoreRow] = Nil

    // Set all ingredient attributes as deleted before inserting/updating
    setIngredientSourcesDeleted( ingredient )

    for ( ingredientSource <- ingredientSources ) {
      var sourceOptionRow = sourceDB.getRowByName(ingredientSource)
      // If the source is not known, ignore it.
      if ( sourceOptionRow.isDefined ) {
        val sourceRow = sourceOptionRow.get(0)
        var ingredientCoreRow : IngredientCoreRow = IngredientCoreRow(
            ingredient_id = ingredient.id.get
          , Type          = SOURCE_TYPE
          , type_id       = sourceRow.id
          , last_updated_by = last_updated_by
        )

        // Insert first. Update next..
        try { prepareRecord("insert", ingredientCoreRow).execute() } 
        catch { case duplicatedKey : Throwable => prepareRecord("update", ingredientCoreRow).execute() }
        finalList = List(ingredientCoreRow) ::: finalList
      }
      else info(s"Source $ingredientSource not found")
    }

    if ( finalList.size > 0 ) finalList else Nil
  }
  
  /**
   * Set all ingredient sources not active for given ingredient_id.
   */
  private final def setIngredientSourcesDeleted( ingredient : Ingredient ) {
    val statement = getStatement(raw"UPDATE ${getCoreDatabaseName()}.${getIngredientCoreTableName()} " +
        " SET active = 'N' WHERE ingredient_id = ? AND type = ?")
    statement.setInt(1, ingredient.id.get)
    statement.setString(2, SOURCE_TYPE)
    statement.execute()
  }

  /**
   * Set all ingredient attributes not active for given ingredient_id.
   */
  private final def setIngredientAttributesDeleted( ingredient : Ingredient ) {
    val statement = getStatement(raw"UPDATE ${getCoreDatabaseName()}.${getIngredientCoreTableName()} " +
        " SET active = 'N' WHERE ingredient_id = ? AND type = ?")
    statement.setInt(1, ingredient.id.get)
    statement.setString(2, ATTRIBUTE_TYPE)
    statement.execute()
  }

  /**
   * Creates a prepared to execute statement, depending on the action requested.
   */
  private final def prepareRecord( action : String, row : IngredientCoreRow ) : PreparedStatement = {

    // The final updating statement to be delivered
    var statement : PreparedStatement = null

    action match {
      case "insert" => {
        statement = getStatement(raw"INSERT INTO ${getCoreDatabaseName()}.${getIngredientCoreTableName()} " +
        " (`ingredient_id`,`type`,`type_id`,`active`,`created_by`,`last_updated_by`) " +
        " VALUES(?, ?, ?, ?, ?, ?)")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getIngredientCoreColumns().foreach { columnName => columnName match {
          case "id"                => "No actrion required"
          case "ingredient_id"     => statement.setInt(1, row.ingredient_id)
          case "type"              => statement.setString(2, row.Type)
          case "type_id"           => statement.setInt(3, row.type_id)
          case "active"            => statement.setString(4, "Y")
          case "created_by"        => statement.setString(5, row.created_by)
          case "created_date"      => "No action required"
          case "last_updated_by"   => statement.setString(6, row.last_updated_by)
          case "last_updated_date" => "No action required"
          case _ => error(s"Unknwon new table column: '$columnName'.")
        } }
      }
      case "update" => {
        statement = getStatement(raw"UPDATE ${getCoreDatabaseName()}.${getIngredientCoreTableName()} " +
        " SET `created_by` = ?, `last_updated_by` = ?, `active` = ? " + 
        " WHERE `ingredient_id` = ? AND `type` = ? AND `type_id` = ? ")

        // Use this method here to capture when new columns are added to the table,
        // so that this can complain about them
        getIngredientCoreColumns().foreach { columnName => columnName match {
          case "created_by"        => statement.setString(1, row.created_by)
          case "last_updated_by"   => statement.setString(2, row.last_updated_by)
          case "active"            => statement.setString(3, "Y")
          case "ingredient_id"     => statement.setInt(4, row.ingredient_id)
          case "type"              => statement.setString(5, row.Type)
          case "type_id"           => statement.setInt(6, row.type_id)
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