package com.app.recipe.Database.SQL.Core.Recipe

import com.app.recipe.Database.RecipeDatabase
import com.app.recipe.Database.SQL.Core.Recipe.Modules.RecipeQuery
import com.app.recipe.Log.RecipeLogging
import com.app.recipe.Recipe.Model.Recipe

/**
 * The Recipe Core implementing public methods required by RecipeDatabase 
 * interface, returning the required built Recipe case classes.
 */
object SQLRecipeCore extends RecipeDatabase with RecipeLogging {

  /**
   * The column names to be used to call on the found database values.
   */
  private final val RECIPE_NAME_COLUMNS            = Array("id","name","version","created_by","created_date","last_updated_by","last_updated_date")
  private final val RECIPE_MAIN_INGREDIENT_COLUMNS = Array("id","recipe_id","main_ingredient","created_by","created_date","last_updated_by","last_updated_date")
  private final val RECIPE_TYPE_COLUMNS            = Array("id","recipe_id","type","created_by","created_date","last_updated_by","last_updated_date")
  private final val RECIPE_STYLE_COLUMNS           = Array("id","recipe_id","style","created_by","created_date","last_updated_by","last_updated_date")
  private final val RECIPE_COURSE_COLUMNS          = Array("id","recipe_id","course","created_by","created_date","last_updated_by","last_updated_date")
  private final val RECIPE_DESCRIPTION_COLUMNS     = Array("id","recipe_id","description","created_by","created_date","last_updated_by","last_updated_date")
  private final val RECIPE_SOURCE_COLUMNS          = Array("id","recipe_id","source","created_by","created_date","last_updated_by","last_updated_date")
  private final val RECIPE_AUTHOR_COLUMNS          = Array("id","recipe_id","author","created_by","created_date","last_updated_by","last_updated_date")
  private final val RECIPE_RATING_COLUMNS          = Array("id","recipe_id","rating","created_by","created_date","last_updated_by","last_updated_date")
  private final val RECIPE_DIFFICULTY_COLUMNS      = Array("id","recipe_id","difficulty","created_by","created_date","last_updated_by","last_updated_date")
  private final val RECIPE_DURATION_COLUMNS        = Array("id","recipe_id","type","duration","created_by","created_date","last_updated_by","last_updated_date")
  private final val RECIPE_TAGS_COLUMNS            = Array("id","recipe_id","tag","created_by","created_date","last_updated_by","last_updated_date")
  private final val RECIPE_STAGE_COLUMNS           = Array("id","recipe_id","step_id","step_name","description","created_by","created_date","last_updated_by","last_updated_date")

  /**
   * The Map of instantiations for the generic class to then call upon the 
   * respective table with respective columns and key to get the correct data
   * out, key'd on the array of column names. 
   */
  private final val recipeClassMap : Map[Array[String],RecipeQuery] = Map(
      RECIPE_NAME_COLUMNS            -> new RecipeQuery(getCoreDatabaseName(),getCoreRecipeTableName(),RECIPE_NAME_COLUMNS, "id")
    , RECIPE_MAIN_INGREDIENT_COLUMNS -> new RecipeQuery(getCoreDatabaseName(),getMainIngredientTableName(),RECIPE_MAIN_INGREDIENT_COLUMNS, "recipe_id")
    , RECIPE_TYPE_COLUMNS            -> new RecipeQuery(getCoreDatabaseName(),getRecipeTypeTableName(),RECIPE_TYPE_COLUMNS, "recipe_id")
    , RECIPE_STYLE_COLUMNS           -> new RecipeQuery(getCoreDatabaseName(),getRecipeStyleTableName(),RECIPE_STYLE_COLUMNS, "recipe_id")
    , RECIPE_COURSE_COLUMNS          -> new RecipeQuery(getCoreDatabaseName(),getRecipeCourseTableName(),RECIPE_COURSE_COLUMNS, "recipe_id")
    , RECIPE_DESCRIPTION_COLUMNS     -> new RecipeQuery(getCoreDatabaseName(),getRecipeDescriptionTableName(),RECIPE_DESCRIPTION_COLUMNS, "recipe_id")
    , RECIPE_SOURCE_COLUMNS          -> new RecipeQuery(getCoreDatabaseName(),getRecipeSourceTableName(),RECIPE_SOURCE_COLUMNS, "recipe_id")
    , RECIPE_AUTHOR_COLUMNS          -> new RecipeQuery(getCoreDatabaseName(),getRecipeAuthorTableName(),RECIPE_AUTHOR_COLUMNS, "recipe_id")
    , RECIPE_RATING_COLUMNS          -> new RecipeQuery(getCoreDatabaseName(),getRecipeRatingTableName(),RECIPE_RATING_COLUMNS, "recipe_id")
    , RECIPE_DIFFICULTY_COLUMNS      -> new RecipeQuery(getCoreDatabaseName(),getRecipeDifficultyTableName(),RECIPE_DIFFICULTY_COLUMNS, "recipe_id")
    , RECIPE_DURATION_COLUMNS        -> new RecipeQuery(getCoreDatabaseName(),getRecipeDurationTableName(),RECIPE_DURATION_COLUMNS, "recipe_id")
    , RECIPE_TAGS_COLUMNS            -> new RecipeQuery(getCoreDatabaseName(),getRecipeTagTableName(),RECIPE_TAGS_COLUMNS, "recipe_id")
    , RECIPE_STAGE_COLUMNS           -> new RecipeQuery(getCoreDatabaseName(),getRecipeStageTableName(),RECIPE_STAGE_COLUMNS, "recipe_id")
  )

  /**
   * The recipe by ID.
   */
  def getRecipeAggregatedById( id : Int ) : Option[Recipe] = {
    // The single row results
    val recipeName           = recipeClassMap(RECIPE_NAME_COLUMNS).getResult(id)
    val recipeDescription    = recipeClassMap(RECIPE_DESCRIPTION_COLUMNS).getResult(id)
    val recipeRating         = recipeClassMap(RECIPE_RATING_COLUMNS).getResult(id)
    val recipeDifficulty     = recipeClassMap(RECIPE_DIFFICULTY_COLUMNS).getResult(id)

    // The multiple row results
    val recipeMainIngredient = recipeClassMap(RECIPE_MAIN_INGREDIENT_COLUMNS).getResult(id)
    val recipeType           = recipeClassMap(RECIPE_TYPE_COLUMNS).getResult(id)
    val recipeStyle          = recipeClassMap(RECIPE_STYLE_COLUMNS).getResult(id)
    val recipeCourse         = recipeClassMap(RECIPE_COURSE_COLUMNS).getResult(id)
    val recipeSource         = recipeClassMap(RECIPE_SOURCE_COLUMNS).getResult(id)
    val recipeAuthor         = recipeClassMap(RECIPE_AUTHOR_COLUMNS).getResult(id)
    val recipeDuration       = recipeClassMap(RECIPE_DURATION_COLUMNS).getResult(id)
    val recipeTags           = recipeClassMap(RECIPE_TAGS_COLUMNS).getResult(id)
    val recipeStage          = recipeClassMap(RECIPE_STAGE_COLUMNS).getResult(id)

    // It is compulsory to have data at this point, else return nothing.
    if ( recipeName.size == 0 ) return None

    // TODO: Get data from methods where we set the correct return value and do required operations.
    val recipe_id            : String         = recipeName(0)(RECIPE_NAME_COLUMNS(0))
    val name                 : String         = recipeName(0)(RECIPE_NAME_COLUMNS(1))
    val version              : Int            = recipeName(0)(RECIPE_NAME_COLUMNS(2)).toInt
    val mainIngredient       : Option[String] = if ( recipeMainIngredient.isEmpty ) None else Some(recipeMainIngredient(0)(RECIPE_MAIN_INGREDIENT_COLUMNS(2)))
    val mainType             : Option[String] = if ( recipeType.isEmpty ) None else Some(recipeType(0)(RECIPE_TYPE_COLUMNS(2)))
    val mainStyle            : Option[String] = if ( recipeStyle.isEmpty ) None else Some(recipeType(0)(RECIPE_STYLE_COLUMNS(2)))
    val mainCourse           : Option[String] = if ( recipeCourse.isEmpty ) None else Some(recipeCourse(0)(RECIPE_COURSE_COLUMNS(2)))
    val mainDescription      : Option[String] = if ( recipeDescription.isEmpty ) None else Some(recipeDescription(0)(RECIPE_DESCRIPTION_COLUMNS(2)))
    val mainSource           : Option[String] = if ( recipeSource.isEmpty ) None else Some(recipeSource(0)(RECIPE_SOURCE_COLUMNS(2)))
    val mainAuthor           : Option[String] = if ( recipeAuthor.isEmpty ) None else Some(recipeAuthor(0)(RECIPE_AUTHOR_COLUMNS(2)))
    val mainRating           : Option[String] = if ( recipeRating.isEmpty ) None else Some(recipeRating(0)(RECIPE_RATING_COLUMNS(2)))
    val mainDifficulty       : Option[String] = if ( recipeDifficulty.isEmpty ) None else Some(recipeDifficulty(0)(RECIPE_DIFFICULTY_COLUMNS(2)))
    val mainDurationType     : Option[String] = if ( recipeDuration.isEmpty ) None else Some(recipeDuration(0)(RECIPE_DURATION_COLUMNS(2)))
    val mainDuration         : Option[String] = if ( recipeDuration.isEmpty ) None else Some(recipeDuration(0)(RECIPE_DURATION_COLUMNS(3)))
    val mainTags             : List[String]   = if ( recipeTags.isEmpty ) List() else List(recipeTags(0)(RECIPE_TAGS_COLUMNS(2)))
//    val mainStageStepId      : Option[String] = if ( recipeStage.isEmpty ) None else Some(recipeStage(RECIPE_STAGE_COLUMNS(2)))
//    val mainStageStepName    : Option[String] = if ( recipeStage.isEmpty ) None else Some(recipeStage(RECIPE_STAGE_COLUMNS(3)))
//    val mainStageDescription : Option[String] = if ( recipeStage.isEmpty ) None else Some(recipeStage(RECIPE_STAGE_COLUMNS(4)))

    // The final aggregated Recipe case class
    Some(Recipe(
        id               = recipe_id
      , name             = name
      , version          = version
      , mainIngredient   = mainIngredient
      , recipeType       = mainType
      , recipeStyle      = mainStyle
      , course           = mainCourse
      , description      = mainDescription
      , source           = mainSource
//      , recipeForPersons = Some(1)
      , author           = mainAuthor
//      , ingredientList   = None
      , rating           = mainRating
      , difficulty       = mainDifficulty
      , tags             = mainTags
//      , stages           = None
    ))
  }
}