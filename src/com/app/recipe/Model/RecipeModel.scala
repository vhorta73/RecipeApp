package com.app.recipe.Model

import java.sql.Time
import com.app.recipe.Log.RecipeLogging

/**
 * The Recipe case class.
 */
case class Recipe(
    id               : Option[Int]          = None
  , name             : Option[String]       = None
  , version          : Option[Int]          = None
  , mainIngredient   : Option[List[String]] = None
  , recipeType       : Option[List[String]] = None
  , recipeStyle      : Option[List[String]] = None
  , course           : Option[List[String]] = None
  , description      : Option[String]       = None
  , source           : Option[List[String]] = None
  , recipeForPersons : Option[Int]          = None
  , author           : Option[List[String]] = None
  , ingredientList   : Option[List[String]] = None
  , rating           : Option[Int]          = None
  , difficulty       : Option[Int]          = None
  , duration         : Option[List[String]] = None
  , tags             : Option[List[String]] = None
  , stages           : Option[List[String]] = None
)

/**
 * Recipe manager to make any required updates.
 */
object RecipeManager extends RecipeLogging {

  /**
   * Add the given map data to the supplied recipe and return the updated 
   * case class wrapped in an Option object.
   * 
   * @param Map[String,List[String]]
   * @param Option[Recipe]
   * @return Option[Recipe]
   */
  def add( updateMap : Map[String,List[String]] )(givenRecipe : Option[Recipe]) : Option[Recipe] = {

    // Only making updates to valid recipes
    if ( givenRecipe.isDefined ) {
    
      var recipe = givenRecipe.get

      var mainIngredients     : List[String] = if ( givenRecipe.get.mainIngredient.isDefined ) givenRecipe.get.mainIngredient.get else List()
      var recipeTypeList      : List[String] = if ( givenRecipe.get.recipeType.isDefined ) givenRecipe.get.recipeType.get else List()
      var recipeStyleList     : List[String] = if ( givenRecipe.get.recipeStyle.isDefined ) givenRecipe.get.recipeStyle.get else List()
      var courseList          : List[String] = if ( givenRecipe.get.course.isDefined ) givenRecipe.get.course.get else List()
      var newDescription      : String       = if ( givenRecipe.get.description.isDefined ) givenRecipe.get.description.get else ""
      var sourceList          : List[String] = if ( givenRecipe.get.source.isDefined ) givenRecipe.get.source.get else List()
      var newRecipeForPersons : Int          = if ( givenRecipe.get.recipeForPersons.isDefined ) givenRecipe.get.recipeForPersons.get else 0
      var authorList          : List[String] = if ( givenRecipe.get.author.isDefined ) givenRecipe.get.author.get else List()
      var ingredients         : List[String] = if ( givenRecipe.get.ingredientList.isDefined) givenRecipe.get.ingredientList.get else List()
      var newRating           : Int          = if ( givenRecipe.get.rating.isDefined ) givenRecipe.get.rating.get else 0
      var newDifficulty       : Int          = if ( givenRecipe.get.difficulty.isDefined ) givenRecipe.get.difficulty.get else 0
      var newDuration         : List[String] = if ( givenRecipe.get.duration.isDefined ) givenRecipe.get.duration.get else List()
      var tagList             : List[String] = if ( givenRecipe.get.tags.isDefined ) givenRecipe.get.tags.get else List()
      var stageList           : List[String] = if ( givenRecipe.get.stages.isDefined ) givenRecipe.get.stages.get else List()

      updateMap.foreach( 
          key => key match {
            case (field,value) if (field.equals("mainIngredient")) => mainIngredients = value ::: mainIngredients
            case (field,value) if (field.equals("recipeType")) => recipeTypeList = value ::: recipeTypeList
            case (field,value) if (field.equals("author")) => authorList = value ::: authorList
        case _ => error("RecipeManager.add found a bad key: " + key.toString())
      })

      Some(Recipe(
          id               = recipe.id
        , name             = recipe.name
        , version          = recipe.version
        , mainIngredient   = recipe.mainIngredient
        , recipeType       = Some(recipeTypeList)
        , recipeStyle      = recipe.recipeStyle
        , course           = recipe.course
        , description      = recipe.description
        , source           = recipe.source
        , recipeForPersons = recipe.recipeForPersons
        , author           = Some(authorList)
        , ingredientList   = recipe.ingredientList
        , rating           = recipe.rating
        , difficulty       = recipe.difficulty
        , duration         = recipe.duration
        , tags             = recipe.tags
        , stages           = recipe.stages
      ))
    }
    else givenRecipe
  }
}

/**
 * The Recipe stage describing the recipe execution.
 */
case class Stage( stepId : Int, stepName : String, stepDescription : String )

/**
 * The Recipe duration for different types like preparation, execution, etc.
 */
case class Duration( durationType : String, duration : Time )

