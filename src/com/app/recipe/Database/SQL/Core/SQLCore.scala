package com.app.recipe.Database.SQL.Core

import com.app.recipe.Database.RecipeDatabaseCore
import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeCoreRetriever
import com.app.recipe.Log.RecipeLogging
import com.app.recipe.Recipe.Model.Recipe

/**
 * The Core object pointing each method call to the respective object that 
 * implements it.
 */
object SQLCore extends RecipeDatabaseCore with RecipeLogging {

  /* ======================================================================= */
  /*                                 Recipe                                  */
  /* ======================================================================= */

  /**
   * Retrieving the complete recipe structure from the database by recipe id.
   */
  def getRecipeById( id : Int ) : Option[Recipe] = SQLRecipeCoreRetriever.getRecipeAggregatedById(id)

  /**
   * Creates a new Recipe from a Map and returns the created recipe_id.
   * If another recipe already exists with same name and version, will 
   * return that id instead.
   */
//  def getNewRecipeId( name : String, version : Int ) : Int = SQLRecipeCoreSaver.getNewRecipeId(name, version)
}