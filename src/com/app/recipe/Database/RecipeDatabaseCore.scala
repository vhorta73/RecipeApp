package com.app.recipe.Database

import com.app.recipe.Model.Ingredient
import com.app.recipe.Model.Recipe

/**
 * The interface exposing all methods and variables that must be implemented 
 * across all existing core database protocols. 
 */
abstract trait RecipeDatabaseCore extends RecipeDatabase {

  /* ======================================================================= */
  /*                              Ingredient                                 */
  /* ======================================================================= */

  /**
   * Retrieving the ingredient by id.
   */
  def getIngredientById( id : Int ) : Option[Ingredient]
  
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
  /**
   * Saving an ingredient record by insert or update if existing.
   * 
   * @param ingredient
   * @return Option[Ingredient]
   */
  def saveRecord( ingredient : Ingredient ) : Option[Ingredient]


  /* ======================================================================= */
  /*                                 Recipe                                  */
  /* ======================================================================= */

  /**
   * The element Recipe search by id.
   * 
   * @param id : Int
   * @return Option[Recipe]
   */
  def getRecipeById( id : Int ) : Option[Recipe]

  /**
   * Get a recipe from existing or create a new.
   * 
   * @param name 
   * @param version 
   * @return Option[Recipe]
   */
  def getRecipe( name : String, version : Int ) : Option[Recipe]

  /**
   * Saving a recipe record by insert or update if existing.
   * 
   * @param recipe
   * @return Option[Recipe]
   */
  def saveRecord( recipe : Recipe ) : Option[Recipe]

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