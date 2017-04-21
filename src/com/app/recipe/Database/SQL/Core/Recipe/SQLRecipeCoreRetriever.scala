package com.app.recipe.Database.SQL.Core.Recipe

import com.app.recipe.Database.SQL.Core.Recipe.Modules.RecipeCoreRetrieveQuery
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Retriever.RecipeMainIngredient
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Retriever.RecipeMainIngredient
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Retriever.RecipeNameRetriever
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeNameRow
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.TableValueClass
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.TableValueClass
import com.app.recipe.Log.RecipeLogging
import com.app.recipe.Recipe.Model.Recipe
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeMainIngredientRow
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.TableValueClass
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Retriever.RecipeAuthor
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeAuthorRow

/**
 * The Recipe Core implementing public methods required by RecipeDatabase 
 * interface, returning the required built Recipe case classes.
 */
object SQLRecipeCoreRetriever extends SQLRecipeCore with RecipeLogging {

  /**
   * The Map of instantiations for the generic class to then call upon the 
   * respective table with respective columns and key to get the correct data
   * out, key'd on the array of column names. 
   */
  private final val recipeClassMap : Map[Array[String],RecipeCoreRetrieveQuery] = Map(
//      RECIPE_NAME_COLUMNS            -> new RecipeCoreRetrieveQuery(getCoreDatabaseName(),getCoreRecipeTableName(),       RECIPE_NAME_COLUMNS,            "id")
//     RECIPE_MAIN_INGREDIENT_COLUMNS -> new RecipeCoreRetrieveQuery(getCoreDatabaseName(),getMainIngredientTableName(),   RECIPE_MAIN_INGREDIENT_COLUMNS, "recipe_id")
     RECIPE_TYPE_COLUMNS            -> new RecipeCoreRetrieveQuery(getCoreDatabaseName(),getRecipeTypeTableName(),       RECIPE_TYPE_COLUMNS,            "recipe_id")
    , RECIPE_STYLE_COLUMNS           -> new RecipeCoreRetrieveQuery(getCoreDatabaseName(),getRecipeStyleTableName(),      RECIPE_STYLE_COLUMNS,           "recipe_id")
    , RECIPE_COURSE_COLUMNS          -> new RecipeCoreRetrieveQuery(getCoreDatabaseName(),getRecipeCourseTableName(),     RECIPE_COURSE_COLUMNS,          "recipe_id")
    , RECIPE_DESCRIPTION_COLUMNS     -> new RecipeCoreRetrieveQuery(getCoreDatabaseName(),getRecipeDescriptionTableName(),RECIPE_DESCRIPTION_COLUMNS,     "recipe_id")
    , RECIPE_SOURCE_COLUMNS          -> new RecipeCoreRetrieveQuery(getCoreDatabaseName(),getRecipeSourceTableName(),     RECIPE_SOURCE_COLUMNS,          "recipe_id")
//    , RECIPE_AUTHOR_COLUMNS          -> new RecipeCoreRetrieveQuery(getCoreDatabaseName(),getRecipeAuthorTableName(),     RECIPE_AUTHOR_COLUMNS,          "recipe_id")
    , RECIPE_RATING_COLUMNS          -> new RecipeCoreRetrieveQuery(getCoreDatabaseName(),getRecipeRatingTableName(),     RECIPE_RATING_COLUMNS,          "recipe_id")
    , RECIPE_DIFFICULTY_COLUMNS      -> new RecipeCoreRetrieveQuery(getCoreDatabaseName(),getRecipeDifficultyTableName(), RECIPE_DIFFICULTY_COLUMNS,      "recipe_id")
    , RECIPE_DURATION_COLUMNS        -> new RecipeCoreRetrieveQuery(getCoreDatabaseName(),getRecipeDurationTableName(),   RECIPE_DURATION_COLUMNS,        "recipe_id")
    , RECIPE_TAGS_COLUMNS            -> new RecipeCoreRetrieveQuery(getCoreDatabaseName(),getRecipeTagTableName(),        RECIPE_TAGS_COLUMNS,            "recipe_id")
    , RECIPE_STAGE_COLUMNS           -> new RecipeCoreRetrieveQuery(getCoreDatabaseName(),getRecipeStageTableName(),      RECIPE_STAGE_COLUMNS,           "recipe_id")
  )

  private final val recipeRetrieverClassMap : Map[Array[String],RetrieverCore] = Map(
      RECIPE_NAME_COLUMNS            -> new RecipeNameRetriever(getCoreDatabaseName())
    , RECIPE_MAIN_INGREDIENT_COLUMNS -> new RecipeMainIngredient(getCoreDatabaseName())
//    , RECIPE_TYPE_COLUMNS            -> new RecipeCoreRetrieveQuery(getCoreDatabaseName(),getRecipeTypeTableName(),       RECIPE_TYPE_COLUMNS,            "recipe_id")
//    , RECIPE_STYLE_COLUMNS           -> new RecipeCoreRetrieveQuery(getCoreDatabaseName(),getRecipeStyleTableName(),      RECIPE_STYLE_COLUMNS,           "recipe_id")
//    , RECIPE_COURSE_COLUMNS          -> new RecipeCoreRetrieveQuery(getCoreDatabaseName(),getRecipeCourseTableName(),     RECIPE_COURSE_COLUMNS,          "recipe_id")
//    , RECIPE_DESCRIPTION_COLUMNS     -> new RecipeCoreRetrieveQuery(getCoreDatabaseName(),getRecipeDescriptionTableName(),RECIPE_DESCRIPTION_COLUMNS,     "recipe_id")
//    , RECIPE_SOURCE_COLUMNS          -> new RecipeCoreRetrieveQuery(getCoreDatabaseName(),getRecipeSourceTableName(),     RECIPE_SOURCE_COLUMNS,          "recipe_id")
    , RECIPE_AUTHOR_COLUMNS          -> new RecipeAuthor(getCoreDatabaseName())
//    , RECIPE_RATING_COLUMNS          -> new RecipeCoreRetrieveQuery(getCoreDatabaseName(),getRecipeRatingTableName(),     RECIPE_RATING_COLUMNS,          "recipe_id")
//    , RECIPE_DIFFICULTY_COLUMNS      -> new RecipeCoreRetrieveQuery(getCoreDatabaseName(),getRecipeDifficultyTableName(), RECIPE_DIFFICULTY_COLUMNS,      "recipe_id")
//    , RECIPE_DURATION_COLUMNS        -> new RecipeCoreRetrieveQuery(getCoreDatabaseName(),getRecipeDurationTableName(),   RECIPE_DURATION_COLUMNS,        "recipe_id")
//    , RECIPE_TAGS_COLUMNS            -> new RecipeCoreRetrieveQuery(getCoreDatabaseName(),getRecipeTagTableName(),        RECIPE_TAGS_COLUMNS,            "recipe_id")
//    , RECIPE_STAGE_COLUMNS           -> new RecipeCoreRetrieveQuery(getCoreDatabaseName(),getRecipeStageTableName(),      RECIPE_STAGE_COLUMNS,           "recipe_id")
  )

  /**
   * The recipe by ID.
   */
  def getRecipeAggregatedById( id : Int ) : Option[Recipe] = {
    // The single row results
    val recipeName           : Option[TableValueClass] = recipeRetrieverClassMap(RECIPE_NAME_COLUMNS).getRowId(id)

    // Recipe Name is the core information for any recipe. Not having a row, 
    // means no data for this recipe.
    if ( recipeName.isEmpty ) return None 
    
    val recipeDescription    = recipeClassMap(RECIPE_DESCRIPTION_COLUMNS).getRecipeById(id)
    val recipeRating         = recipeClassMap(RECIPE_RATING_COLUMNS).getRecipeById(id)
    val recipeDifficulty     = recipeClassMap(RECIPE_DIFFICULTY_COLUMNS).getRecipeById(id)

    // The multiple row results
    val recipeMainIngredient : Option[List[TableValueClass]] = recipeRetrieverClassMap(RECIPE_MAIN_INGREDIENT_COLUMNS).getRecipeId(id)
    val recipeType           = recipeClassMap(RECIPE_TYPE_COLUMNS).getRecipeById(id)
    val recipeStyle          = recipeClassMap(RECIPE_STYLE_COLUMNS).getRecipeById(id)
    val recipeCourse         = recipeClassMap(RECIPE_COURSE_COLUMNS).getRecipeById(id)
    val recipeSource         = recipeClassMap(RECIPE_SOURCE_COLUMNS).getRecipeById(id)
    val recipeAuthor         : Option[List[TableValueClass]] = recipeRetrieverClassMap(RECIPE_AUTHOR_COLUMNS).getRecipeId(id)
    val recipeDuration       = recipeClassMap(RECIPE_DURATION_COLUMNS).getRecipeById(id)
    val recipeTags           = recipeClassMap(RECIPE_TAGS_COLUMNS).getRecipeById(id)
    val recipeStage          = recipeClassMap(RECIPE_STAGE_COLUMNS).getRecipeById(id)

    // TODO: Get data from methods where we set the correct return value and do required operations.
    val recipe_id            : Option[Int]          = get( recipeName.get.asInstanceOf[RecipeNameRow].id )
    val name                 : Option[String]       = get( recipeName.get.asInstanceOf[RecipeNameRow].name)
    val version              : Option[Int]          = Some(recipeName.get.asInstanceOf[RecipeNameRow].version)
    val mainIngredient       : Option[List[String]] = if ( recipeMainIngredient.isEmpty ) None 
                                                      else Some(recipeMainIngredient.get.toList.map { 
                                                        row => row.asInstanceOf[RecipeMainIngredientRow].main_ingredient })
    val mainType             : List[String]   = getListMapToListString(recipeType, RECIPE_TYPE_COLUMNS, List(2))
    val mainStyle            : List[String]   = getListMapToListString(recipeStyle, RECIPE_STYLE_COLUMNS, List(2))
    val mainCourse           : List[String]   = getListMapToListString(recipeCourse, RECIPE_COURSE_COLUMNS, List(2))
    val mainDescription      : String         = getListMapToListString(recipeDescription, RECIPE_DESCRIPTION_COLUMNS, List(2))(0)
    val mainSource           : List[String]   = getListMapToListString(recipeSource, RECIPE_SOURCE_COLUMNS, List(2))
    val mainAuthor           : Option[List[String]] = if ( recipeAuthor.isEmpty ) None
                                                      else Some(recipeAuthor.get.toList.map { 
                                                        row  => row.asInstanceOf[RecipeAuthorRow].author })
    val mainRating           : Int            = if ( recipeRating.size > 0 ) recipeRating(0)(RECIPE_RATING_COLUMNS(2)).toInt else 3 // TODO
    val mainDifficulty       : Int            = if ( recipeDifficulty.size > 0 ) recipeDifficulty(0)(RECIPE_DIFFICULTY_COLUMNS(2)).toInt else 3 // TODO
//    val mainDurationType     : Option[String] = if ( recipeDuration.isEmpty ) None else Some(recipeDuration(0)(RECIPE_DURATION_COLUMNS(2)))
//    val mainDuration         : Option[String] = if ( recipeDuration.isEmpty ) None else Some(recipeDuration(0)(RECIPE_DURATION_COLUMNS(3)))
//    val mainTags             : List[String]   = if ( recipeTags.isEmpty ) List() else List(recipeTags(0)(RECIPE_TAGS_COLUMNS(2)))
//    val mainStageStepId      : Option[String] = if ( recipeStage.isEmpty ) None else Some(recipeStage(RECIPE_STAGE_COLUMNS(2)))
//    val mainStageStepName    : Option[String] = if ( recipeStage.isEmpty ) None else Some(recipeStage(RECIPE_STAGE_COLUMNS(3)))
//    val mainStageDescription : Option[String] = if ( recipeStage.isEmpty ) None else Some(recipeStage(RECIPE_STAGE_COLUMNS(4)))

    // The final aggregated Recipe case class
    Some(Recipe(
        id               = recipe_id
      , name             = name
      , version          = version
      , mainIngredient   = mainIngredient
      , recipeType       = Some(mainType)
      , recipeStyle      = Some(mainStyle)
      , course           = Some(mainCourse)
      , description      = Some(mainDescription)
      , source           = Some(mainSource)
//      , recipeForPersons = Some(1)
      , author           = mainAuthor
//      , ingredientList   = None
      , rating           = Some(mainRating)
      , difficulty       = Some(mainDifficulty)
//      , tags             = mainTags
//      , stages           = None
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

  private def get[A]( value : A ) : Option[A] = value match {
    case v if ( v.isInstanceOf[Int] && v.asInstanceOf[Int] > 0 ) => Some(value) 
    case v if ( v.isInstanceOf[String] && v.asInstanceOf[String].length() > 0 ) => Some(value) 
//    case v if ( v.isInstanceOf[List[String]] && v.asInstanceOf[String].length() > 0 ) => Some(value) 
    case _ => None
  }
}