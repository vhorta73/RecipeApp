package com.app.recipe.Recipe.Model

import com.app.recipe.Recipe.Model._

/**
 * The Recipe case class.
 */
case class Recipe(
    id               : String
  , name             : String
  , version          : Int = 0
  , mainIngredient   : Option[String] = None
  , recipeType       : Option[String] = None
  , recipeStyle      : Option[String] = None
  , course           : Option[String] = None
  , description      : Option[String] = None
  , source           : Option[String] = None
  , recipeForPersons : Option[Int] = Some(1)
  , author           : Option[String] = None
//  , ingredientList   : List[Ingredient] = None
  , rating           : Option[String] = None
  , difficulty       : Option[String] = None
  , tags             : List[String]   = Nil
//  , stages           : List[Step] = None
)
