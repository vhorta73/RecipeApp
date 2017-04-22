package com.app.recipe.Database.SQL.Core.Recipe

import com.app.recipe.Log.RecipeLogging

/**
 * The SQL Recipe Core abstract with all the shared implementations across all 
 * the other SQL objects that compose all possible actions.
 */
abstract trait SQLRecipeCore extends RecipeLogging {

  /**
   * The recipe table names.
   */
  def getCoreDatabaseName()              : String = "recipe_core"
  def getRecipeNameTableName()           : String = "recipe"
  def getRecipeMainIngredientTableName() : String = "recipe_main_ingredient"
  def getRecipeTypeTableName()           : String = "recipe_type"
  def getRecipeStyleTableName()          : String = "recipe_style"
  def getRecipeCourseTableName()         : String = "recipe_course"
  def getRecipeDescriptionTableName()    : String = "recipe_description"
  def getRecipeSourceTableName()         : String = "recipe_source"
  def getRecipeAuthorTableName()         : String = "recipe_author"
  def getRecipeRatingTableName()         : String = "recipe_rating"
  def getRecipeDifficultyTableName()     : String = "recipe_difficulty"
  def getRecipeDurationTableName()       : String = "recipe_duration"
  def getRecipeTagTableName()            : String = "recipe_tag"
  def getRecipeStageTableName()          : String = "recipe_stage"

  /**
   * The column names to be used to call on the found database values.
   */
  def getRecipeNameColumns()           : Array[String] = Array("id","name","version","created_by","created_date","last_updated_by","last_updated_date")
  def getRecipeMainIngredientColumns() : Array[String] = Array("id","recipe_id","main_ingredient","created_by","created_date","last_updated_by","last_updated_date")
  def getRecipeTypeColumns()           : Array[String] = Array("id","recipe_id","type","created_by","created_date","last_updated_by","last_updated_date")
  def getRecipeStyleColumns()          : Array[String] = Array("id","recipe_id","style","created_by","created_date","last_updated_by","last_updated_date")
  def getRecipeCourseColumns()         : Array[String] = Array("id","recipe_id","course","created_by","created_date","last_updated_by","last_updated_date")
  def getRecipeDescriptionColumns()    : Array[String] = Array("id","recipe_id","description","created_by","created_date","last_updated_by","last_updated_date")
  def getRecipeSourceColumns()         : Array[String] = Array("id","recipe_id","source","created_by","created_date","last_updated_by","last_updated_date")
  def getRecipeAuthorColumns()         : Array[String] = Array("id","recipe_id","author","created_by","created_date","last_updated_by","last_updated_date")
  def getRecipeRatingColumns()         : Array[String] = Array("id","recipe_id","rating","created_by","created_date","last_updated_by","last_updated_date")
  def getRecipeDifficultyColumns()     : Array[String] = Array("id","recipe_id","difficulty","created_by","created_date","last_updated_by","last_updated_date")
  def getRecipeDurationColumns()       : Array[String] = Array("id","recipe_id","type","duration","created_by","created_date","last_updated_by","last_updated_date")
  def getRecipeTagColumns()            : Array[String] = Array("id","recipe_id","tag","created_by","created_date","last_updated_by","last_updated_date")
  def getRecipeStageColumns()          : Array[String] = Array("id","recipe_id","step_id","step_name","description","created_by","created_date","last_updated_by","last_updated_date")

}