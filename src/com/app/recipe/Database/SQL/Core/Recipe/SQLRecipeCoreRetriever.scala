package com.app.recipe.Database.SQL.Core.Recipe

import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeAuthor
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeAuthorRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeCourse
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeCourseRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeDescription
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeDescriptionRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeDifficulty
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeDifficultyRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeDuration
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeDurationRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeMainIngredient
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeMainIngredientRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeName
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeNameRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeRating
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeRatingRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeSource
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeSourceRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeStage
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeStageRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeStyle
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeStyleRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeTag
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeTagRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeType
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeTypeRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.TableRow
import com.app.recipe.Log.RecipeLogging
import com.app.recipe.Model.Duration
import com.app.recipe.Model.Recipe
import com.app.recipe.Model.Stage


/**
 * The Recipe Core implementing public methods required by RecipeDatabase 
 * interface, returning the required built Recipe case classes.
 */
object SQLRecipeCoreRetriever extends SQLRecipeCore with RecipeLogging {

  /**
   * The recipe by ID.
   */
  def getRecipeAggregatedById( id : Int ) : Option[Recipe] = {
    // The single row results
    val recipeName           : Option[TableRow] = getRecipeClass[RecipeName](getRecipeNameTableName()).getRowId(id)

    // Recipe Name is the core information for any recipe. Not having a row, means no data for this recipe.
    if ( recipeName.isEmpty ) return None 
    
    val recipeDescription    : Option[List[TableRow]] = getRecipeClass[RecipeDescription](getRecipeDescriptionTableName()).getRecipeId(id)
    val recipeRating         : Option[List[TableRow]] = getRecipeClass[RecipeRating](getRecipeRatingTableName()).getRecipeId(id)
    val recipeDifficulty     : Option[List[TableRow]] = getRecipeClass[RecipeDifficulty](getRecipeDifficultyTableName()).getRecipeId(id)

    // The multiple row results
    val recipeMainIngredient : Option[List[TableRow]] = getRecipeClass[RecipeMainIngredient](getRecipeMainIngredientTableName()).getRecipeId(id)
    val recipeType           : Option[List[TableRow]] = getRecipeClass[RecipeType](getRecipeTypeTableName()).getRecipeId(id)
    val recipeStyle          : Option[List[TableRow]] = getRecipeClass[RecipeStyle](getRecipeStyleTableName()).getRecipeId(id)
    val recipeCourse         : Option[List[TableRow]] = getRecipeClass[RecipeCourse](getRecipeCourseTableName()).getRecipeId(id)
    val recipeSource         : Option[List[TableRow]] = getRecipeClass[RecipeSource](getRecipeSourceTableName()).getRecipeId(id)
    val recipeAuthor         : Option[List[TableRow]] = getRecipeClass[RecipeAuthor](getRecipeAuthorTableName()).getRecipeId(id)
    val recipeDuration       : Option[List[TableRow]] = getRecipeClass[RecipeDuration](getRecipeDurationTableName()).getRecipeId(id)
    val recipeTag            : Option[List[TableRow]] = getRecipeClass[RecipeTag](getRecipeTagTableName()).getRecipeId(id)
    val recipeStage          : Option[List[TableRow]] = getRecipeClass[RecipeStage](getRecipeStageTableName()).getRecipeId(id)

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
  
  /**
   * Overriding the super getRecipeClass to deal with the optional.
   */
  def getRecipeClass[A]( tableName : String ) : A = super.getRecipeClass(tableName).get.asInstanceOf[A]


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
  private final def getRecipeId( recipeName : Option[TableRow] ) : Option[Int] = 
    if ( recipeName.isEmpty ) None else Some( recipeName.get.asInstanceOf[RecipeNameRow].id )

  /**
   * The recipe name.
   * 
   * @param recipeName : Option[TableValueClass]
   * @returns Option[String]
   */
  private final def getName( recipeName : Option[TableRow] ) : Option[String] = 
    if ( recipeName.isEmpty ) None else Some( recipeName.get.asInstanceOf[RecipeNameRow].name )

  /**
   * The recipe version.
   * 
   * @param recipeName : Option[TableValueClass]
   * @returns Option[Int]
   */
  private final def getVersion( recipeName : Option[TableRow] ) : Option[Int] =
    Some(recipeName.get.asInstanceOf[RecipeNameRow].version)
  
  /**
   * The recipe main ingredient.
   * 
   * @param recipeIngredient : Option[List[TableValueClass]]
   * @returns Option[List[String]]
   */
  private final def getMainIngredient( mainIngredient : Option[List[TableRow]] ) : Option[List[String]] = 
    if ( mainIngredient.isEmpty ) None else Some(mainIngredient.get.toList
      .map { row => row.asInstanceOf[RecipeMainIngredientRow].main_ingredient })

  /**
   * The recipe type.
   * 
   * @param recipeType : Option[List[TableValueClass]]
   * @returns Option[List[String]]
   */
  private final def getType( recipeType : Option[List[TableRow]] ) : Option[List[String]] =
    if ( recipeType.isEmpty ) None else Some( recipeType.get.toList
      .map { row => row.asInstanceOf[RecipeTypeRow].recipeType })

  /**
   * The recipe style.
   * 
   * @param recipeStyle : Option[List[TableValueClass]]
   * @returns Option[List[String]]
   */
  private final def getStyle( recipeStyle : Option[List[TableRow]] ) : Option[List[String]] =
    if ( recipeStyle.isEmpty ) None else Some( recipeStyle.get.toList
      .map { row => row.asInstanceOf[RecipeStyleRow].style })
  
  /**
   * The recipe course.
   * 
   * @param recipeCourse : Option[List[TableValueClass]]
   * @returns Option[List[String]]
   */
  private final def getCourse( recipeCourse : Option[List[TableRow]] ) : Option[List[String]] =
    if ( recipeCourse.isEmpty ) None else Some( recipeCourse.get.toList
      .map { row => row.asInstanceOf[RecipeCourseRow].course })

  /**
   * The recipe description.
   * 
   * @param recipeDescription : Option[List[TableValueClass]]
   * @returns Option[String]
   */
  private final def getDescription( recipeDescription : Option[List[TableRow]] ) : Option[String] = {
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
  private final def getSource( recipeSource : Option[List[TableRow]] ) : Option[List[String]] =
    if ( recipeSource.isEmpty ) None else Some(recipeSource.get.toList
      .map { row => row.asInstanceOf[RecipeSourceRow].source })

  /**
   * The recipe author.
   * 
   * @param recipeAuthor : Option[List[TableValueClass]]
   * @returns Option[List[String]]
   */
  private final def getAuthor( recipeAuthor : Option[List[TableRow]] ) : Option[List[String]] =
    if ( recipeAuthor.isEmpty ) None else Some(recipeAuthor.get.toList
       .map { row  => row.asInstanceOf[RecipeAuthorRow].author })

  /**
   * The recipe rating.
   * 
   * @param recipeRating : Option[List[TableValueClass]]
   * @returns Option[Int]
   */
  private final def getRating( recipeRating : Option[List[TableRow]] ) : Option[Int] =
    if ( recipeRating.isEmpty ) None 
    else Some( recipeRating.get(0).asInstanceOf[RecipeRatingRow].rating)

  /**
   * The recipe difficulty.
   * 
   * @param recipeDifficulty : Option[List[TableValueClass]]
   * @returns Option[Int]
   */
  private final def getDifficulty( recipeDifficulty : Option[List[TableRow]] ) : Option[Int] =
    if ( recipeDifficulty.isEmpty ) None else Some(recipeDifficulty.get(0).asInstanceOf[RecipeDifficultyRow].difficulty)

  /**
   * The recipe tag.
   * 
   * @param recipeTag : Option[List[TableValueClass]]
   * @returns Option[List[String]]
   */
  private final def getTag( recipeTag : Option[List[TableRow]] ) : Option[List[String]] =
    if ( recipeTag.isEmpty ) None else Some(recipeTag.get.toList
        .map{ row => row.asInstanceOf[RecipeTagRow].tag })

  /**
   * The recipe stage.
   * 
   * @param recipeStage : Option[List[TableValueClass]]
   * @returns Option[List[Stage]]
   */
  private final def getStage( recipeStage : Option[List[TableRow]] ) : Option[List[Stage]] = {
      var finalStage : List[Stage] = List()
      if ( recipeStage.isEmpty ) return None
      recipeStage.get.toList.foreach { 
        row => {
          finalStage = List(Stage(
              row.asInstanceOf[RecipeStageRow].step_id,
              row.asInstanceOf[RecipeStageRow].step_name,
              row.asInstanceOf[RecipeStageRow].description)
              ) ::: finalStage
        }
      }
      Some(finalStage)
  }

  /**
   * The recipe duration.
   * 
   * @param recipeDuration : Option[List[TableValueClass]]
   * @returns Option[List[Duration]]
   */
  private final def getDuration( recipeDuration : Option[List[TableRow]] ) : Option[List[Duration]] = {
      var finalDuration : List[Duration] = List()
      if ( recipeDuration.isEmpty ) return None
      recipeDuration.get.toList.foreach { 
        row => {
          finalDuration = List(Duration(
              row.asInstanceOf[RecipeDurationRow].Type,
              row.asInstanceOf[RecipeDurationRow].duration)
              ) ::: finalDuration
        }
      }
      Some(finalDuration)
  }
}