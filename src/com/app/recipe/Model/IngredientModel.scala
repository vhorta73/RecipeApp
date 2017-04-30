package com.app.recipe.Model

import com.app.recipe.Import.Product.Units.Model.StandardUnits.Units
import com.app.recipe.Log.RecipeLogging

/**
 * The Ingredient case class.
 */
case class Ingredient(
    id        : Option[Int]          = None
  , name      : Option[String]       = None
  , attribute : Option[List[String]] = None
  , source    : Option[List[String]] = None 
)

/**
 * Ingredient manager to make any required updates.
 */
object IngredientManager extends RecipeLogging {

  /**
   * Add the given map data to the supplied ingredient and return the updated 
   * case class wrapped in an Option object.
   * 
   * @param Map[String,Any]
   * @param Option[Ingredient]
   * @return Option[Ingredient]
   */
  def add( updateMap : Map[String,Any] )(givenIngredient : Option[Ingredient]) : Option[Ingredient] = {

    // Only making updates to valid ingredients
    if ( givenIngredient.isDefined ) {
    
      var ingredient = givenIngredient.get

      var ingredientId : Int = if ( ingredient.id.isDefined ) ingredient.id.get else 0
      var sources      : List[String] = if ( givenIngredient.get.source.isDefined ) givenIngredient.get.source.get.asInstanceOf[List[String]] else List()
      var attributes   : List[String] = if ( givenIngredient.get.attribute.isDefined ) givenIngredient.get.attribute.get.asInstanceOf[List[String]] else List()

      updateMap.foreach( 
        key => key match {
          case (field,value) if (field.equals("sources"))    => sources = value.asInstanceOf[List[String]] ::: sources
          case (field,value) if (field.equals("attributes")) => attributes = value.asInstanceOf[List[String]] ::: attributes
          case (field,value) if (field.equals("id")) => ingredientId = value.asInstanceOf[Int]
          case _ => error("IngredientManager.add found a bad key: " + key.toString())
        }
      )

      Some(Ingredient(
          id          = if ( ingredientId > 0 ) Some(ingredientId) else ingredient.id
        , name        = givenIngredient.get.name
        , source      = Some(sources)
        , attribute   = Some(attributes)
      ))
    }
    else givenIngredient
  }
}
