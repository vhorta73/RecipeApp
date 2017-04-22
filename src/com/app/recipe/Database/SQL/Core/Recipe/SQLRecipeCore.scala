package com.app.recipe.Database.SQL.Core.Recipe

import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeAuthor
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeAuthor
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeCourse
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeCourse
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeDescription
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeDifficulty
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeDuration
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeMainIngredient
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeName
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeName
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeRating
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeSource
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeStage
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeStyle
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeTag
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeType
import com.app.recipe.Log.RecipeLogging

/**
 * The SQL Recipe Core abstract with all the shared implementations across all 
 * the other SQL objects that compose all possible actions.
 */
abstract trait SQLRecipeCore extends RecipeLogging {

  /**
   * The recipe table names.
   */
  protected final def getCoreDatabaseName()              : String = "recipe_core"
  protected final def getRecipeNameTableName()           : String = "recipe"
  protected final def getRecipeMainIngredientTableName() : String = "recipe_main_ingredient"
  protected final def getRecipeTypeTableName()           : String = "recipe_type"
  protected final def getRecipeStyleTableName()          : String = "recipe_style"
  protected final def getRecipeCourseTableName()         : String = "recipe_course"
  protected final def getRecipeDescriptionTableName()    : String = "recipe_description"
  protected final def getRecipeSourceTableName()         : String = "recipe_source"
  protected final def getRecipeAuthorTableName()         : String = "recipe_author"
  protected final def getRecipeRatingTableName()         : String = "recipe_rating"
  protected final def getRecipeDifficultyTableName()     : String = "recipe_difficulty"
  protected final def getRecipeDurationTableName()       : String = "recipe_duration"
  protected final def getRecipeTagTableName()            : String = "recipe_tag"
  protected final def getRecipeStageTableName()          : String = "recipe_stage"

  /**
   * The column names to be used to call on the found database values.
   */
  protected final def getRecipeNameColumns()           : Array[String] = Array("id","name","version","created_by","created_date","last_updated_by","last_updated_date")
  protected final def getRecipeMainIngredientColumns() : Array[String] = Array("id","recipe_id","main_ingredient","created_by","created_date","last_updated_by","last_updated_date")
  protected final def getRecipeTypeColumns()           : Array[String] = Array("id","recipe_id","type","created_by","created_date","last_updated_by","last_updated_date")
  protected final def getRecipeStyleColumns()          : Array[String] = Array("id","recipe_id","style","created_by","created_date","last_updated_by","last_updated_date")
  protected final def getRecipeCourseColumns()         : Array[String] = Array("id","recipe_id","course","created_by","created_date","last_updated_by","last_updated_date")
  protected final def getRecipeDescriptionColumns()    : Array[String] = Array("id","recipe_id","description","created_by","created_date","last_updated_by","last_updated_date")
  protected final def getRecipeSourceColumns()         : Array[String] = Array("id","recipe_id","source","created_by","created_date","last_updated_by","last_updated_date")
  protected final def getRecipeAuthorColumns()         : Array[String] = Array("id","recipe_id","author","created_by","created_date","last_updated_by","last_updated_date")
  protected final def getRecipeRatingColumns()         : Array[String] = Array("id","recipe_id","rating","created_by","created_date","last_updated_by","last_updated_date")
  protected final def getRecipeDifficultyColumns()     : Array[String] = Array("id","recipe_id","difficulty","created_by","created_date","last_updated_by","last_updated_date")
  protected final def getRecipeDurationColumns()       : Array[String] = Array("id","recipe_id","type","duration","created_by","created_date","last_updated_by","last_updated_date")
  protected final def getRecipeTagColumns()            : Array[String] = Array("id","recipe_id","tag","created_by","created_date","last_updated_by","last_updated_date")
  protected final def getRecipeStageColumns()          : Array[String] = Array("id","recipe_id","step_id","step_name","description","created_by","created_date","last_updated_by","last_updated_date")

  /**
   * The instantiated recipe classes for DB access on each.
   * 
   * @param tableName : String
   * @return Option[SQLRecipeCore]
   */
  def getRecipeClass( tableName : String ) : Option[SQLRecipeTableAccess] = tableName match {
    case name if name.equals(getRecipeAuthorTableName())         => Some(new RecipeAuthor())
    case name if name.equals(getRecipeCourseTableName())         => Some(new RecipeCourse())
    case name if name.equals(getRecipeDescriptionTableName())    => Some(new RecipeDescription())
    case name if name.equals(getRecipeDifficultyTableName())     => Some(new RecipeDifficulty())
    case name if name.equals(getRecipeDurationTableName())       => Some(new RecipeDuration())
    case name if name.equals(getRecipeMainIngredientTableName()) => Some(new RecipeMainIngredient())
    case name if name.equals(getRecipeNameTableName())           => Some(new RecipeName())
    case name if name.equals(getRecipeRatingTableName())         => Some(new RecipeRating())
    case name if name.equals(getRecipeSourceTableName())         => Some(new RecipeSource())
    case name if name.equals(getRecipeStageTableName())          => Some(new RecipeStage())
    case name if name.equals(getRecipeStyleTableName())          => Some(new RecipeStyle())
    case name if name.equals(getRecipeTagTableName())            => Some(new RecipeTag())
    case name if name.equals(getRecipeTypeTableName())           => Some(new RecipeType())
    case _ => throw new IllegalStateException(s"Not known table '$tableName'")
  }
}