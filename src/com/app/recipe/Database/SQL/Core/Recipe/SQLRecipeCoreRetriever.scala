package com.app.recipe.Database.SQL.Core.Recipe

import com.app.recipe.Database.SQL.Core.Recipe.Modules.Retriever.RecipeAuthor
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Retriever.RecipeCourse
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Retriever.RecipeDescription
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Retriever.RecipeDifficulty
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Retriever.RecipeDuration
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Retriever.RecipeMainIngredient
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Retriever.RecipeNameRetriever
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Retriever.RecipeRating
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Retriever.RecipeSource
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Retriever.RecipeStage
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Retriever.RecipeStyle
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Retriever.RecipeTag
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Retriever.RecipeType
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeAuthorRow
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeCourseRow
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeDescriptionRow
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeDifficultyRow
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeDurationRow
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeMainIngredientRow
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeNameRow
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeRatingRow
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeSourceRow
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeStageRow
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeStyleRow
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeTagRow
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.RecipeTypeRow
import com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables.TableValueClass
import com.app.recipe.Log.RecipeLogging
import com.app.recipe.Recipe.Model.Duration
import com.app.recipe.Recipe.Model.Recipe
import com.app.recipe.Recipe.Model.Stage

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
  private final val recipeRetrieverClassMap : Map[Array[String],RetrieverCore] = Map(
      RECIPE_NAME_COLUMNS            -> new RecipeNameRetriever(getCoreDatabaseName())
    , RECIPE_MAIN_INGREDIENT_COLUMNS -> new RecipeMainIngredient(getCoreDatabaseName())
    , RECIPE_TYPE_COLUMNS            -> new RecipeType(getCoreDatabaseName())
    , RECIPE_STYLE_COLUMNS           -> new RecipeStyle(getCoreDatabaseName())
    , RECIPE_COURSE_COLUMNS          -> new RecipeCourse(getCoreDatabaseName())
    , RECIPE_DESCRIPTION_COLUMNS     -> new RecipeDescription(getCoreDatabaseName())
    , RECIPE_SOURCE_COLUMNS          -> new RecipeSource(getCoreDatabaseName())
    , RECIPE_AUTHOR_COLUMNS          -> new RecipeAuthor(getCoreDatabaseName())
    , RECIPE_RATING_COLUMNS          -> new RecipeRating(getCoreDatabaseName())
    , RECIPE_DIFFICULTY_COLUMNS      -> new RecipeDifficulty(getCoreDatabaseName())
    , RECIPE_DURATION_COLUMNS        -> new RecipeDuration(getCoreDatabaseName())
    , RECIPE_TAGS_COLUMNS            -> new RecipeTag(getCoreDatabaseName())
    , RECIPE_STAGE_COLUMNS           -> new RecipeStage(getCoreDatabaseName())
  )

  /**
   * The recipe by ID.
   */
  def getRecipeAggregatedById( id : Int ) : Option[Recipe] = {
    // The single row results
    val recipeName           : Option[TableValueClass] = recipeRetrieverClassMap(RECIPE_NAME_COLUMNS).getRowId(id)

    // Recipe Name is the core information for any recipe. Not having a row, means no data for this recipe.
    if ( recipeName.isEmpty ) return None 
    
    val recipeDescription    : Option[List[TableValueClass]] = recipeRetrieverClassMap(RECIPE_DESCRIPTION_COLUMNS).getRecipeId(id)
    val recipeRating         : Option[List[TableValueClass]] = recipeRetrieverClassMap(RECIPE_RATING_COLUMNS).getRecipeId(id)
    val recipeDifficulty     : Option[List[TableValueClass]] = recipeRetrieverClassMap(RECIPE_DIFFICULTY_COLUMNS).getRecipeId(id)

    // The multiple row results
    val recipeMainIngredient : Option[List[TableValueClass]] = recipeRetrieverClassMap(RECIPE_MAIN_INGREDIENT_COLUMNS).getRecipeId(id)
    val recipeType           : Option[List[TableValueClass]] = recipeRetrieverClassMap(RECIPE_TYPE_COLUMNS).getRecipeId(id)
    val recipeStyle          : Option[List[TableValueClass]] = recipeRetrieverClassMap(RECIPE_STYLE_COLUMNS).getRecipeId(id)
    val recipeCourse         : Option[List[TableValueClass]] = recipeRetrieverClassMap(RECIPE_COURSE_COLUMNS).getRecipeId(id)
    val recipeSource         : Option[List[TableValueClass]] = recipeRetrieverClassMap(RECIPE_SOURCE_COLUMNS).getRecipeId(id)
    val recipeAuthor         : Option[List[TableValueClass]] = recipeRetrieverClassMap(RECIPE_AUTHOR_COLUMNS).getRecipeId(id)
    val recipeDuration       : Option[List[TableValueClass]] = recipeRetrieverClassMap(RECIPE_DURATION_COLUMNS).getRecipeId(id)
    val recipeTag            : Option[List[TableValueClass]] = recipeRetrieverClassMap(RECIPE_TAGS_COLUMNS).getRecipeId(id)
    val recipeStage          : Option[List[TableValueClass]] = recipeRetrieverClassMap(RECIPE_STAGE_COLUMNS).getRecipeId(id)

    // Each column comes from one or many rows from different tables.
    // Methods are called to aggregate each parameter to the expected value and type.
    Some(Recipe(
        id               = getRecipeId( recipeName )
      , name             = getName( recipeName )
      , version          = getVersion( recipeName )
      , mainIngredient   = getMainIngredient( recipeMainIngredient )
      , recipeType       = getType( recipeType )
      , recipeStyle      = getStyle( recipeStyle )
      , course           = getCourse( recipeCourse )
      , description      = getDescription( recipeDescription )
      , source           = getSource( recipeSource )
//      , recipeForPersons = Some(1)
      , author           = getAuthor( recipeAuthor )
//      , ingredientList   = None
      , rating           = getRating( recipeRating )
      , difficulty       = getDifficulty( recipeDifficulty )
      , duration         = getDuration( recipeDuration )
      , tags             = getTag( recipeTag ) 
      , stages           = getStage( recipeStage )
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

  /*************************************************************************************
   *                                                                                   *
   *    The methods that will parse complex data into Recipe respective column data.   *
   *                                                                                   * 
   *************************************************************************************/

  /**
   * The recipe id.
   * 
   * @param recipeName : Option[TableValueClass]
   * @returns Option[Int]
   */
  private final def getRecipeId( recipeName : Option[TableValueClass] ) : Option[Int] = 
    if ( recipeName.isEmpty ) None else Some( recipeName.get.asInstanceOf[RecipeNameRow].id )

  /**
   * The recipe name.
   * 
   * @param recipeName : Option[TableValueClass]
   * @returns Option[String]
   */
  private final def getName( recipeName : Option[TableValueClass] ) : Option[String] = 
    if ( recipeName.isEmpty ) None else Some( recipeName.get.asInstanceOf[RecipeNameRow].name )

  /**
   * The recipe version.
   * 
   * @param recipeName : Option[TableValueClass]
   * @returns Option[Int]
   */
  private final def getVersion( recipeName : Option[TableValueClass] ) : Option[Int] =
    Some(recipeName.get.asInstanceOf[RecipeNameRow].version)
  
  /**
   * The recipe main ingredient.
   * 
   * @param recipeIngredient : Option[List[TableValueClass]]
   * @returns Option[List[String]]
   */
  private final def getMainIngredient( mainIngredient : Option[List[TableValueClass]] ) : Option[List[String]] = 
    if ( mainIngredient.isEmpty ) None else Some(mainIngredient.get.toList
      .map { row => row.asInstanceOf[RecipeMainIngredientRow].main_ingredient })

  /**
   * The recipe type.
   * 
   * @param recipeType : Option[List[TableValueClass]]
   * @returns Option[List[String]]
   */
  private final def getType( recipeType : Option[List[TableValueClass]] ) : Option[List[String]] =
    if ( recipeType.isEmpty ) None else Some( recipeType.get.toList
      .map { row => row.asInstanceOf[RecipeTypeRow].Type })

  /**
   * The recipe style.
   * 
   * @param recipeStyle : Option[List[TableValueClass]]
   * @returns Option[List[String]]
   */
  private final def getStyle( recipeStyle : Option[List[TableValueClass]] ) : Option[List[String]] =
    if ( recipeStyle.isEmpty ) None else Some( recipeStyle.get.toList
      .map { row => row.asInstanceOf[RecipeStyleRow].style })
  
  /**
   * The recipe course.
   * 
   * @param recipeCourse : Option[List[TableValueClass]]
   * @returns Option[List[String]]
   */
  private final def getCourse( recipeCourse : Option[List[TableValueClass]] ) : Option[List[String]] =
    if ( recipeCourse.isEmpty ) None else Some( recipeCourse.get.toList
      .map { row => row.asInstanceOf[RecipeCourseRow].course })

  /**
   * The recipe description.
   * 
   * @param recipeDescription : Option[List[TableValueClass]]
   * @returns Option[String]
   */
  private final def getDescription( recipeDescription : Option[List[TableValueClass]] ) : Option[String] = {
    var string : String = ""
    if ( recipeDescription.isEmpty ) None 
    else Some( recipeDescription.get.toList.foreach { row => string += row.asInstanceOf[RecipeDescriptionRow].description })
    Some(string)
  }

  /**
   * The recipe source.
   * 
   * @param recipeSource : Option[List[TableValueClass]]
   * @returns Option[List[String]]
   */
  private final def getSource( recipeSource : Option[List[TableValueClass]] ) : Option[List[String]] =
    if ( recipeSource.isEmpty ) None else Some(recipeSource.get.toList
      .map { row => row.asInstanceOf[RecipeSourceRow].source })

  /**
   * The recipe author.
   * 
   * @param recipeAuthor : Option[List[TableValueClass]]
   * @returns Option[List[String]]
   */
  private final def getAuthor( recipeAuthor : Option[List[TableValueClass]] ) : Option[List[String]] =
    if ( recipeAuthor.isEmpty ) None else Some(recipeAuthor.get.toList
       .map { row  => row.asInstanceOf[RecipeAuthorRow].author })

  /**
   * The recipe rating.
   * 
   * @param recipeRating : Option[List[TableValueClass]]
   * @returns Option[Int]
   */
  private final def getRating( recipeRating : Option[List[TableValueClass]] ) : Option[Int] =
    if ( recipeRating.isEmpty ) None 
    else Some( recipeRating.get(0).asInstanceOf[RecipeRatingRow].rating)

  /**
   * The recipe difficulty.
   * 
   * @param recipeDifficulty : Option[List[TableValueClass]]
   * @returns Option[Int]
   */
  private final def getDifficulty( recipeDifficulty : Option[List[TableValueClass]] ) : Option[Int] =
    if ( recipeDifficulty.isEmpty ) None else Some(recipeDifficulty.get(0).asInstanceOf[RecipeDifficultyRow].difficulty)

  /**
   * The recipe tag.
   * 
   * @param recipeTag : Option[List[TableValueClass]]
   * @returns Option[List[String]]
   */
  private final def getTag( recipeTag : Option[List[TableValueClass]] ) : Option[List[String]] =
    if ( recipeTag.isEmpty ) None else Some(recipeTag.get.toList
        .map{ row => row.asInstanceOf[RecipeTagRow].tag })

  /**
   * The recipe stage.
   * 
   * @param recipeStage : Option[List[TableValueClass]]
   * @returns Option[List[String]]
   */
  private final def getStage( recipeStage : Option[List[TableValueClass]] ) : Option[List[String]] = {
      var finalStage : List[String] = List()
      recipeStage.get.toList.foreach { 
        row => {
          finalStage = List(Stage(
              row.asInstanceOf[RecipeStageRow].step_id,
              row.asInstanceOf[RecipeStageRow].step_name,
              row.asInstanceOf[RecipeStageRow].description)
              .toString) ::: finalStage
        }
      }
      Some(finalStage)
  }

  /**
   * The recipe duration.
   * 
   * @param recipeDuration : Option[List[TableValueClass]]
   * @returns Option[List[String]]
   */
  private final def getDuration( recipeDuration : Option[List[TableValueClass]] ) : Option[List[String]] = {
      var finalDuration : List[String] = List()
      recipeDuration.get.toList.foreach { 
        row => {
          finalDuration = List(Duration(
              row.asInstanceOf[RecipeDurationRow].Type,
              row.asInstanceOf[RecipeDurationRow].duration)
              .toString) ::: finalDuration
        }
      }
      Some(finalDuration)
  }
}