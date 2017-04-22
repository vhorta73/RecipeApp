package com.app.recipe.Database.SQL.Core

import com.app.recipe.Database.RecipeDatabaseCore
import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeCoreSaver
import com.app.recipe.Log.RecipeLogging
import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeCoreRetriever
import com.app.recipe.Model._

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
   * 
   * @param id : Int
   * @returns Option[Recipe]
   */
  def getRecipeById( id : Int ) : Option[Recipe] = SQLRecipeCoreRetriever.getRecipeAggregatedById(id)

  /**
   * Get a new recipe Id after passing a name and version number.
   * If the name and version already exists, it will return the 
   * existing one.
   * 
   * @param name : String
   * @param version : Int
   * @returns Option[Int]
   */
  def getNewRecipeId( name : String, version : Int ) : Option[Int] = SQLRecipeCoreSaver.getNewRecipeId(name, version)

  /**
   * Updates the supplied recipe to the database, inserting if new.
   * 
   * @param recipe : Recipe
   * @returns Option[Recipe]
   */
  def update( recipe : Recipe ) : Option[Recipe] = SQLRecipeCoreSaver.update( recipe )

}