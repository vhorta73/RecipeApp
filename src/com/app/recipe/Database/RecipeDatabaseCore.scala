package com.app.recipe.Database

import com.app.recipe.Model.Recipe
import com.app.recipe.Database.SQL.Core.Recipe.SaverCore

/**
 * The interface exposing all methods and variables that must be implemented 
 * across all existing core database protocols. 
 */
abstract trait RecipeDatabaseCore extends RecipeDatabase {

//  /* ======================================================================= */
//  /*                              Ingredient                                 */
//  /* ======================================================================= */
//
//  /**
//   * Retrieving the ingredient by id.
//   */
//  def getIngredientById( id : Int ) : Ingredient
//  
//  /**
//   * Retrieving the Ingredient by name.
//   */
//  def getIngredientByName( name : String ) : List[Ingredient]
//
//  /**
//   * Getting the list of Ingredients that match the given filter.
//   */
//  def getIngredientByFilter( f : Ingredient => Boolean ) : List[Ingredient]
//
//  /**
//   * Getting the list of Ingredients that match all the filters in the list.
//   */
//  def getIngredientByFilterList( fList : List[Ingredient => Boolean] ) : List[Ingredient]
//
//
//
  /* ======================================================================= */
  /*                                 Recipe                                  */
  /* ======================================================================= */

  /**
   * The element Recipe search by id.
   * 
   * @param id : Int
   * @returns Option[Recipe]
   */
  def getRecipeById( id : Int ) : Option[Recipe]

  /**
   * Get a new recipe Id after passing a name and version number.
   * If the name and version already exists, it will return the 
   * existing one.
   * 
   * @param name : String
   * @param version : Int
   * @returns recipe_id : Int
   */
  def getNewRecipeId( name : String, version : Int ) : Option[Int]

//  /**
//   * The element Recipe search by name.
//   */
//  def getRecipeByName( name : String ) : List[Recipe]
//
//  /**
//   * The element Recipe search by condition.
//   */
//  def getRecipeByFilter( f : Recipe => Boolean ) : List[Recipe]
//
//  /**
//   * The element Recipe search by multiple conditions.
//   */
//  def getRecipeByFilterList( fList : List[Recipe => Boolean] ) : List[Recipe]
}