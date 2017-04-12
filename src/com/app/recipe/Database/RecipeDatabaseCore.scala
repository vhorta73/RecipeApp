package com.app.recipe.Database

/**
 * The interface implementing all shared methods and variables that are shared 
 * across all core database protocol system classes. Defining methods for each 
 * database protocol to implement accordingly to each protocol requirements.
 */
abstract trait RecipeDatabaseCore extends RecipeDatabase {
  
  /**
   * The core database name to be used.
   */
  private final val CORE_DATABASE_NAME         : String = "recipe_core"
  
  /**
   * The core ingredient table name.
   */
  private final val CORE_INGREDIENT_TABLE_NAME : String = "ingredient"
  
  /**
   * The core attribute table name.
   */
  private final val CORE_ATTRIBUTE_TABLE_NAME  : String = "attribute"
  
  /**
   * The core source table name.
   */
  private final val CORE_SOURCE_TABLE_NAME     : String = "source"

  /**
   * The core database name for all core tables.
   */
  def getCoreDatabaseName() : String = CORE_DATABASE_NAME

  /**
   * The recipe database table base name for all ingredients 
   */
  def getIngredientTableBaseName() : String = CORE_INGREDIENT_TABLE_NAME

  /**
   * The recipe database table base name for all sources
   */
  def getSourceTableBaseName() : String = CORE_SOURCE_TABLE_NAME

  /**
   * The recipe database table base name for all attributes 
   */
  def getAttributeTableBaseName() : String = CORE_ATTRIBUTE_TABLE_NAME

}
