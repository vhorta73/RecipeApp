package com.app.recipe.Import.Vendor.USDA.FoodReport

import com.app.recipe.Log.RecipeLogging
import com.google.gson.Gson

/**
 * United States Department of Agriculture vendor access object dealing with
 * the basic report query and responses.
 */
abstract trait USDAFoodReport extends RecipeLogging {

  /**
   * Value class to indicate HTTP request which product id to query.
   */
  protected final case class USDAId( var ndbno : String ) 

  /**
   * The static gson to convert objects to JSON and vice-versa.
   */
  protected final val gson = new Gson
  
}