package com.app.recipe.Database.SQL.Core.Ingredient

import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientAttribute
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientAttributeRow
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientName
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientName
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientNameRow
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientTableRow
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientTableRow
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientTableRow
import com.app.recipe.Log.RecipeLogging
import com.app.recipe.Model.Ingredient
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientSourceRow
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientSource

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
    val ingredientAttributes : Option[List[IngredientTableRow]] = getIngredientClass[IngredientAttribute](getIngredientAttributeTableName()).getIngredientId(id)
    val ingredientSources    : Option[List[IngredientTableRow]] = getIngredientClass[IngredientSource](getIngredientSourceTableName()).getIngredientId(id)

    // Each column comes from one or many rows from different tables.
    // Methods are called to aggregate each parameter to the expected value and type.
    Some(Ingredient(
        id                  = getIngredientId( ingredientName )
      , name                = getName( ingredientName )
      , attribute           = getAttributes( ingredientAttributes )
      , source              = getSources( ingredientSources )
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

  /**
   * The ingredient attributes.
   * 
   * @param ingredientAttributes : Option[List[TableValueClass]]
   * @returns Option[List[String]]
   */
  private final def getAttributes( ingredientAttributes : Option[List[IngredientTableRow]] ) : Option[List[String]] = 
    if ( ingredientAttributes.isEmpty ) None else Some(ingredientAttributes.get.toList
      .map { row => row.asInstanceOf[IngredientAttributeRow].name })

  /**
   * The ingredient source.
   * 
   * @param ingredientSources : Option[List[TableValueClass]]
   * @returns Option[List[String]]
   */
  private final def getSources( ingredientSources : Option[List[IngredientTableRow]] ) : Option[List[String]] = 
    if ( ingredientSources.isEmpty ) None else Some(ingredientSources.get.toList
      .map { row => row.asInstanceOf[IngredientSourceRow].name })

//  /**
//   * The recipe description.
//   * 
//   * @param recipeDescription : Option[List[TableValueClass]]
//   * @returns Option[String]
//   */
//  private final def getDescription( recipeDescription : Option[List[RecipeTableRow]] ) : Option[String] = {
//    var string : String = ""
//    if ( recipeDescription.isEmpty ) None 
//    else Some( recipeDescription.get.toList.foreach { row => string += row.asInstanceOf[RecipeDescriptionRow].description })
//    Some(string)
//  }
//
//  /**
//   * The recipe stage.
//   * 
//   * @param recipeStage : Option[List[TableValueClass]]
//   * @returns Option[List[Stage]]
//   */
//  private final def getStage( recipeStage : Option[List[RecipeTableRow]] ) : Option[List[Stage]] = {
//      var finalStage : List[Stage] = List()
//      if ( recipeStage.isEmpty ) return None
//      recipeStage.get.toList.foreach { 
//        row => {
//          finalStage = List(Stage(
//              row.asInstanceOf[RecipeStageRow].step_id,
//              row.asInstanceOf[RecipeStageRow].step_name,
//              row.asInstanceOf[RecipeStageRow].description)
//              ) ::: finalStage
//        }
//      }
//      Some(finalStage)
//  }
}