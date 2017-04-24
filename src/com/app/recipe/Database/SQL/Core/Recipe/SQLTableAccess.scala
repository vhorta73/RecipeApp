package com.app.recipe.Database.SQL.Core.Recipe

import java.sql.PreparedStatement

import scala.collection.immutable.HashMap

import com.app.recipe.Database.SQL.Core.Recipe.Tables.TableRow
import com.app.recipe.Database.SQL.SQLDatabaseHandle
import java.time.LocalDateTime
import scala.util.Random
import com.app.recipe.Model.Recipe
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeNameRow

/**
 * Shared logic amongst all retriever classes and objects.
 */
abstract class SQLTableAccess extends SQLRecipeCore {

  /**
   * Method to get one row by id that must be implemented by child classes.
   * 
   * @param id : Int
   * @return Option[TableRow]
   */
  def getRowId( id : Int ) : Option[TableRow]
  
  /**
   * Creates a new record with information supplied. If the record already 
   * exists for the unique key supplied, it will apply an update instead.
   * Returns true if created, false if update and None if no action applied.
   * 
   * In the case an id is not supplied or set to zero, it will and then key
   * on the recipe name and version, updating if existing or creating a new
   * record across the board otherwise.
   * 
   * @param recipe
   * @return Option[List[TableRow]] 
   */
  def saveRecord( recipe : Recipe ) : Option[List[TableRow]]

  /**
   * Generic DB SQL query for given SQL and column names.
   * TODO: Add a session to this to restrict access.
   */
  protected def getHashMapFromSQL( statement : PreparedStatement, columns : Array[String] ) : List[Map[ String, String ]] = {
    val resultSet = statement.executeQuery()
    var finalList : List[Map[ String, String ]] = List()
    var hashMap : HashMap[ String , String ] = HashMap()
    while ( resultSet.next() ) {
      for( column <- columns ) {
        hashMap = hashMap + (column -> resultSet.getString(column))
      }
      finalList = finalList ::: List(hashMap)
    }
    finalList
  }
  
  /**
   * The single point where all statements are prepared before execution.
   */
  protected def getStatement( sql : String ) : PreparedStatement = SQLDatabaseHandle.getMultiThreadedSQLHandle()
    .prepareStatement(sql)
    
  /**
   * Supplying a random name.
   */
  protected def getRandomName() : String = "Temp+" + LocalDateTime.now() + "+" + (new Random()).nextInt().toString()


}