package com.app.recipe.Database.SQL.Core.Recipe

import com.app.recipe.Log.RecipeLogging
import com.app.recipe.Recipe.Model.Recipe

/**
 * The Recipe Core Saver implementing required methods set by RecipeDatabaseCore 
 * interface, to save data onto the database.
 */
object SQLRecipeCoreSaver extends SQLRecipeCore with RecipeLogging {

  /**
   * Instantiating the static class for query the database regarding the
   * recipe name and version.
   */
//  private final val recipeNameVersionQuery = new RecipeCoreRetrieveQuery(getCoreDatabaseName(), getCoreRecipeTableName(), RECIPE_NAME_COLUMNS, "name", "version")

  /**
   * Instantiating the static class for saving recipes to the database from 
   * given name and version.
   */
//  private final val recipeNameVersionSaveQuery = new RecipeCoreSavingQuery(getCoreDatabaseName(), getCoreRecipeTableName())

  /**
   * Get a new recipe Id after passing a name and version number.
   * If the name and version already exists, it will return the existing one.
   */
  def getNewRecipeId( name : String, version : Int ) : Int = {
//    recipeNameVersionQuery.getRecipeByNameAndVersion(name, version) match {
//      case x if x.isEmpty   => recipeNameVersionSaveQuery.newRecipe(name,version); getNewRecipeId(name, version)
//      case x if x.size > 1  => throw new IllegalArgumentException(s"Too many records found for recipe '$name' and version '$version'")
//      case x if x.size == 1 => x(0)(RECIPE_NAME_COLUMNS(0)).toInt
//    }
    1
  }
}