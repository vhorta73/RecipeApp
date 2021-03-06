package com.app.recipe.Database.SQL.Core.Ingredient

import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientCore
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientName
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientNameRow
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientTableRow
import com.app.recipe.Log.RecipeLogging
import com.app.recipe.Model.Ingredient

/**
 * The Recipe Core implementing public methods required by RecipeDatabase 
 * interface, returning the required built Recipe case classes.
 */
object SQLIngredientCoreRetriever extends SQLIngredientCore with RecipeLogging {

  /**
   * The ingredient by ID.
   */
  def getIngredientAggregatedById( id : Int ) : Option[Ingredient] = {
    // The single row results
    val ingredientName       : Option[IngredientTableRow] = getIngredientClass[IngredientName](getIngredientNameTableName()).getRowId(id)

    // Ingredient Name is the core information. Not having a row, means no data for this ingredient.
    if ( ingredientName.isEmpty ) return None 
    
    // The multiple row results
    val ingredientAttributes : Option[List[String]] = getIngredientClass[IngredientCore](getIngredientCoreTableName()).getAttributeNamesForIngredientId(id)
    val ingredientSources    : Option[List[String]] = getIngredientClass[IngredientCore](getIngredientCoreTableName()).getSourceNamesForIngredientId(id)

    // Each column comes from one or many rows from different tables.
    // Methods are called to aggregate each parameter to the expected value and type.
    Some(Ingredient(
        id                  = getIngredientId( ingredientName )
      , name                = getName( ingredientName )
      , attribute           = ingredientAttributes
      , source              = ingredientSources
    ))
  }
  
  /**
   * A list of results is converted to String.
   */
  def getListMapToListString( mapList : List[Map[String, String]], columns : Array[String], indexes : List[Int] ) : List[String] = {
    var finalList : List[String] = List()
    if ( mapList.isEmpty ) List("")
    else {
      for ( index <- indexes; map <- mapList ) {
        finalList = finalList ::: List(map(columns(index)))
      }
      finalList
    }
  }
  
  /**
   * Overriding the super getIngredientClass to deal with the optional.
   */
  def getIngredientClass[A]( tableName : String ) : A = super.getIngredientClass(tableName).get.asInstanceOf[A]


  /*************************************************************************************
   *                                                                                   *
   *  The methods that will parse complex data into Ingredient respective column data. *
   *                                                                                   * 
   *************************************************************************************/

  /**
   * The ingredient id.
   * 
   * @param ingredientName : Option[TableValueClass]
   * @returns Option[Int]
   */
  private final def getIngredientId( ingredientName : Option[IngredientTableRow] ) : Option[Int] = 
    if ( ingredientName.isEmpty ) None else Some( ingredientName.get.asInstanceOf[IngredientNameRow].id )

  /**
   * The ingredient name.
   * 
   * @param ingredientName : Option[TableValueClass]
   * @returns Option[String]
   */
  private final def getName( ingredientName : Option[IngredientTableRow] ) : Option[String] = 
    if ( ingredientName.isEmpty ) None else Some( ingredientName.get.asInstanceOf[IngredientNameRow].name )

}