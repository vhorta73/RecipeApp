package com.app.recipe.Database

/**
 * RecipeDatabase interface is the top class where all global definitions are 
 * set, to be used across all child classes.
 * 
 * This is the parent of all types, and any new methods added here, must
 * also be implemented here, and will be seen by all inheriting classes.
 */
abstract trait RecipeDatabase {
    
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