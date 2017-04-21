package com.app.recipe.Database.SQL.Core.Recipe

import com.app.recipe.Log.RecipeLogging

/**
 * The SQL Recipe Core abstract with all the shared implementations across all 
 * the other SQL objects that compose all possible actions.
 */
abstract trait SQLRecipeCore extends RecipeLogging {
  /**
   * The column names to be used to call on the found database values.
   */
  protected final val RECIPE_NAME_COLUMNS            = Array("id","name","version","created_by","created_date","last_updated_by","last_updated_date")
  protected final val RECIPE_MAIN_INGREDIENT_COLUMNS = Array("id","recipe_id","main_ingredient","created_by","created_date","last_updated_by","last_updated_date")
  protected final val RECIPE_TYPE_COLUMNS            = Array("id","recipe_id","type","created_by","created_date","last_updated_by","last_updated_date")
  protected final val RECIPE_STYLE_COLUMNS           = Array("id","recipe_id","style","created_by","created_date","last_updated_by","last_updated_date")
  protected final val RECIPE_COURSE_COLUMNS          = Array("id","recipe_id","course","created_by","created_date","last_updated_by","last_updated_date")
  protected final val RECIPE_DESCRIPTION_COLUMNS     = Array("id","recipe_id","description","created_by","created_date","last_updated_by","last_updated_date")
  protected final val RECIPE_SOURCE_COLUMNS          = Array("id","recipe_id","source","created_by","created_date","last_updated_by","last_updated_date")
  protected final val RECIPE_AUTHOR_COLUMNS          = Array("id","recipe_id","author","created_by","created_date","last_updated_by","last_updated_date")
  protected final val RECIPE_RATING_COLUMNS          = Array("id","recipe_id","rating","created_by","created_date","last_updated_by","last_updated_date")
  protected final val RECIPE_DIFFICULTY_COLUMNS      = Array("id","recipe_id","difficulty","created_by","created_date","last_updated_by","last_updated_date")
  protected final val RECIPE_DURATION_COLUMNS        = Array("id","recipe_id","type","duration","created_by","created_date","last_updated_by","last_updated_date")
  protected final val RECIPE_TAGS_COLUMNS            = Array("id","recipe_id","tag","created_by","created_date","last_updated_by","last_updated_date")
  protected final val RECIPE_STAGE_COLUMNS           = Array("id","recipe_id","step_id","step_name","description","created_by","created_date","last_updated_by","last_updated_date")

  /**
   * The recipe table names.
   */
  def getCoreDatabaseName()           : String = "recipe_core"
  def getMainIngredientTableName()    : String = "recipe_main_ingredient"
  def getRecipeTypeTableName()        : String = "recipe_type"
  def getRecipeStyleTableName()       : String = "recipe_style"
  def getRecipeCourseTableName()      : String = "recipe_course"
  def getRecipeDescriptionTableName() : String = "recipe_description"
  def getRecipeSourceTableName()      : String = "recipe_source"
  def getRecipeAuthorTableName()      : String = "recipe_author"
  def getRecipeRatingTableName()      : String = "recipe_rating"
  def getRecipeDifficultyTableName()  : String = "recipe_difficulty"
  def getRecipeDurationTableName()    : String = "recipe_duration"
  def getRecipeTagTableName()         : String = "recipe_tag"
  def getRecipeStageTableName()       : String = "recipe_stage"
  
  /**
   * The list of table names.
   */
  def getCoreRecipeTableName() : String = "recipe"

}