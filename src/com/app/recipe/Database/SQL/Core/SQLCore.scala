package com.app.recipe.Database.SQL.Core

import com.app.recipe.Database.RecipeDatabaseCore
import com.app.recipe.Database.SQL.Core.Ingredient.SQLIngredientCoreRetriever
import com.app.recipe.Database.SQL.Core.Ingredient.SQLIngredientCoreSaver
import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeCoreRetriever
import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeCoreSaver
import com.app.recipe.Log.RecipeLogging
import com.app.recipe.Model._

/**
 * The Core object pointing each method call to the respective object that 
 * implements it.
 */
object SQLCore extends RecipeDatabaseCore with RecipeLogging {

  /* ======================================================================= */
  /*                              Ingredient                                 */
  /* ======================================================================= */

  /**
   * Retrieving the ingredient by id.
   * 
   * @param id : Int
   * @return Option[Ingredient]
   */
  def getIngredientById( id : Int ) : Option[Ingredient] = SQLIngredientCoreRetriever.getIngredientAggregatedById(id)

  /**
   * Saves an ingredient onto the database. 
   * If an ingredient with the same unique key exists, it will update instead.
   * Returns the Ingredient option object.
   * 
   * @param ingredient : Ingredient
   * @returns Option[Ingredient]
   */
  def saveRecord( ingredient : Ingredient ) : Option[Ingredient] = SQLIngredientCoreSaver.saveIngredient( ingredient )

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
   * @returns Option[Recipe]
   */
  def getRecipe( name : String, version : Int ) : Option[Recipe] = SQLRecipeCoreSaver.getRecipeByNameAndVersion(name, version)

  /**
   * Saves a recipe onto the database. 
   * If a recipe with the same unique key exists, it will update instead.
   * Returns the Recipe option object.
   * 
   * @param recipe : Recipe
   * @returns Option[Recipe]
   */
  def saveRecord( recipe : Recipe ) : Option[Recipe] = SQLRecipeCoreSaver.saveRecipe( recipe )

}