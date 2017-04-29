package com.app.recipe.Model

import java.sql.Time

import com.app.recipe.Import.Product.Units.Model.StandardUnits.Units
import com.app.recipe.Log.RecipeLogging

/**
 * The Ingredient case class.
 */
case class Ingredient( 
    name      : Option[String]                    = None
  , attribute : Option[List[IngredientAttribute]] = None
  , source    : Option[List[IngredientSource]]    = None 
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
    
      var recipe = givenIngredient.get

      var sources    : List[String] = if ( givenIngredient.get.source.isDefined ) givenIngredient.get.source.get else List()
      var attributes : List[String] = if ( givenIngredient.get.attribute.isDefined ) givenIngredient.get.attribute.get else List()

      updateMap.foreach( 
        key => key match {
          case (field,value) if (field.equals("source")) => sources = value.asInstanceOf[List[String]] ::: sources
          case (field,value) if (field.equals("attributes")) => attributes = value.asInstanceOf[List[String]] ::: attributes
          case _ => error("IngredientManager.add found a bad key: " + key.toString())
        }
      )

      Some(Ingredient(
          name        = givenIngredient.get.name
        , source      = Some(sources)
        , attribute   = Some(attributes)
      ))
    }
    else givenIngredient
  }
}

/**
 * The Ingredient Source describing the origin of the ingredient.
 */
case class IngredientSource( name : Option[String] = None )

/**
 * The Recipe duration for different types like preparation, execution, etc.
 */
case class IngredientAttribute( name : Option[String] = None )
