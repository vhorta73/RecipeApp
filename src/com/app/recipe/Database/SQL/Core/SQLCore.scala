package com.app.recipe.Database.SQL.Core

import com.app.recipe.Log.RecipeLogging
import com.app.recipe.Recipe.Model._
import com.app.recipe.Database.SQL.Core.Recipe.SQLRecipeCore
import com.app.recipe.Database.RecipeDatabaseCore

/**
 * The Core object pointing each method call to the respective object that 
 * implements it.
 */
object SQLCore extends RecipeDatabaseCore with RecipeLogging {
//
//  /* ======================================================================= */
//  /*                              Ingredient                                 */
//  /* ======================================================================= */
//
//  /**
//   * Retrieving the ingredient by id.
//   */
//  def getIngredientById( id : Int ) : Ingredient = SQLRecipeCore.getIngredientById(id)
//  
//  /**
//   * Retrieving the Ingredient by name.
//   */
//  def getIngredientByName( name : String ) : List[Ingredient] = ???//SQLRecipeCore.getIngredientByName(name)
//
//  /**
//   * Getting the list of Ingredients that match the given filter.
//   */
//  def getIngredientByFilter( f : Ingredient => Boolean ) : List[Ingredient] = ???
//
//  /**
//   * Getting the list of Ingredients that match all the filters in the list.
//   */
//  def getIngredientByFilterList( fList : List[Ingredient => Boolean] ) : List[Ingredient] = ???
//
//

  /* ======================================================================= */
  /*                                 Recipe                                  */
  /* ======================================================================= */

  /**
   * The element Recipe search by id.
   */
  def getRecipeById( id : Int ) : Option[Recipe] = SQLRecipeCore.getRecipeAggregatedById(id)
  
//  /**
//   * The element Recipe search by name.
//   */
//  def getRecipeByName( name : String ) : List[Recipe] = ???
//
//  /**
//   * The element Recipe search by condition.
//   */
//  def getRecipeByFilter( f : Recipe => Boolean ) : List[Recipe] = ???
//
//  /**
//   * The element Recipe search by multiple conditions.
//   */
//  def getRecipeByFilterList( fList : List[Recipe => Boolean] ) : List[Recipe] = ???
}