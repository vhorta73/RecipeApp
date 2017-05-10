/**
 * Documentation source:
 * https://ndb.nal.usda.gov/ndb/doc/apilist/API-FOOD-REPORT.md
 */
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

}