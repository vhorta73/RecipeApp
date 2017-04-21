package com.app.recipe.Recipe.Model

import com.app.recipe.Recipe.Model._

/**
 * The Recipe case class.
 */
case class Recipe(
    id               : Option[Int]                   = None
  , name             : Option[String]                = None
  , version          : Option[Int]                   = None
  , mainIngredient   : Option[List[String]]          = None
  , recipeType       : Option[List[String]]          = None
  , recipeStyle      : Option[List[String]]          = None
  , course           : Option[List[String]]          = None
  , description      : Option[String]                = None
  , source           : Option[List[String]]          = None
  , recipeForPersons : Option[Int]                   = None
  , author           : Option[List[String]]          = None
  , ingredientList   : Option[List[String]]          = None
  , rating           : Option[Int]                   = None
  , difficulty       : Option[Int]                   = None
  , duration         : Option[List[(Double,String)]] = None
  , tags             : Option[List[String]]          = None
  , stages           : Option[List[String]]          = None
)
