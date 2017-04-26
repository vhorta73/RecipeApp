package com.app.recipe.Model

import java.sql.Time
import com.app.recipe.Log.RecipeLogging

/**
 * The Recipe case class.
 */
case class Recipe(
    id               : Option[Int]            = None // TODO: must create new id when required
  , name             : Option[String]         = None // Unique with a version
  , version          : Option[Int]            = None // Unique with a name
  , mainIngredient   : Option[List[String]]   = None // DONE
  , recipeType       : Option[List[String]]   = None // DONE
  , recipeStyle      : Option[List[String]]   = None // DONE
  , course           : Option[List[String]]   = None // DONE
  , description      : Option[String]         = None // DONE
  , source           : Option[List[String]]   = None // DOME
  , recipeForPersons : Option[Int]            = None // TODO
  , author           : Option[List[String]]   = None // DONE
  , ingredientList   : Option[List[String]]   = None // TODO
  , rating           : Option[Int]            = None // DONE
  , difficulty       : Option[Int]            = None // DONE
  , duration         : Option[List[Duration]] = None // DONE
  , tags             : Option[List[String]]   = None // DONE
  , stages           : Option[List[Stage]]    = None // DONE
)

/**
 * Recipe manager to make any required updates.
 */
object RecipeManager extends RecipeLogging {

  /**
   * Add the given map data to the supplied recipe and return the updated 
   * case class wrapped in an Option object.
   * 
   * @param Map[String,Any]
   * @param Option[Recipe]
   * @return Option[Recipe]
   */
  def add( updateMap : Map[String,Any] )(givenRecipe : Option[Recipe]) : Option[Recipe] = {

    // Only making updates to valid recipes
    if ( givenRecipe.isDefined ) {
    
      var recipe = givenRecipe.get

      var mainIngredients     : List[String]   = if ( givenRecipe.get.mainIngredient.isDefined ) givenRecipe.get.mainIngredient.get else List()
      var recipeTypeList      : List[String]   = if ( givenRecipe.get.recipeType.isDefined ) givenRecipe.get.recipeType.get else List()
      var recipeStyleList     : List[String]   = if ( givenRecipe.get.recipeStyle.isDefined ) givenRecipe.get.recipeStyle.get else List()
      var courseList          : List[String]   = if ( givenRecipe.get.course.isDefined ) givenRecipe.get.course.get else List()
      var newDescription      : String         = if ( givenRecipe.get.description.isDefined ) givenRecipe.get.description.get else ""
      var sourceList          : List[String]   = if ( givenRecipe.get.source.isDefined ) givenRecipe.get.source.get else List()
      var newRecipeForPersons : Int            = if ( givenRecipe.get.recipeForPersons.isDefined ) givenRecipe.get.recipeForPersons.get else 0
      var authorList          : List[String]   = if ( givenRecipe.get.author.isDefined ) givenRecipe.get.author.get else List()
      var ingredients         : List[String]   = if ( givenRecipe.get.ingredientList.isDefined) givenRecipe.get.ingredientList.get else List()
      var newRating           : Int            = if ( givenRecipe.get.rating.isDefined ) givenRecipe.get.rating.get else 3
      var newDifficulty       : Int            = if ( givenRecipe.get.difficulty.isDefined ) givenRecipe.get.difficulty.get else 3
      var newDuration         : List[Duration] = if ( givenRecipe.get.duration.isDefined ) givenRecipe.get.duration.get.asInstanceOf[List[Duration]] else List()
      var tagList             : List[String]   = if ( givenRecipe.get.tags.isDefined ) givenRecipe.get.tags.get else List()
      var stageList           : List[Stage]    = if ( givenRecipe.get.stages.isDefined ) givenRecipe.get.stages.get.asInstanceOf[List[Stage]] else List()

      updateMap.foreach( 
        key => key match {
          case (field,value) if (field.equals("main_ingredient")) => mainIngredients = value.asInstanceOf[List[String]] ::: mainIngredients
          case (field,value) if (field.equals("recipeType")) => recipeTypeList = value.asInstanceOf[List[String]] ::: recipeTypeList
          case (field,value) if (field.equals("recipeStyle")) => recipeStyleList = value.asInstanceOf[List[String]] ::: recipeStyleList
          case (field,value) if (field.equals("author")) => authorList = value.asInstanceOf[List[String]] ::: authorList
          case (field,value) if (field.equals("tags")) => tagList = value.asInstanceOf[List[String]] ::: tagList
          case (field,value) if (field.equals("stages")) => stageList = value.asInstanceOf[List[Stage]] ::: stageList
          case (field,value) if (field.equals("duration")) => newDuration = value.asInstanceOf[List[Duration]] ::: newDuration
          case (field,value) if (field.equals("course")) => courseList = value.asInstanceOf[List[String]] ::: courseList
          case (field,value) if (field.equals("description")) => newDescription = value.toString() + newDescription
          case (field,value) if (field.equals("difficulty")) => newDifficulty = value.asInstanceOf[Int] 
          case (field,value) if (field.equals("rating")) => newRating = value.asInstanceOf[Int] 
          case (field,value) if (field.equals("source")) => sourceList = value.asInstanceOf[List[String]] ::: sourceList
          case _ => error("RecipeManager.add found a bad key: " + key.toString())
        }
      )

      Some(Recipe(
          id               = recipe.id
        , name             = recipe.name
        , version          = recipe.version
        , mainIngredient   = Some(mainIngredients)
        , recipeType       = Some(recipeTypeList)
        , recipeStyle      = Some(recipeStyleList)
        , course           = Some(courseList)
        , description      = Some(newDescription)
        , source           = Some(sourceList)
        , recipeForPersons = recipe.recipeForPersons
        , author           = Some(authorList)
        , ingredientList   = recipe.ingredientList
        , rating           = Some(newRating)
        , difficulty       = Some(newDifficulty)
        , duration         = Some(newDuration)
        , tags             = Some(tagList)
        , stages           = Some(stageList)
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

