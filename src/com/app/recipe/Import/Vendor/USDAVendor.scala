package com.app.recipe.Import.Vendor

import com.app.recipe.Import.Vendor.USDA.Model.USDAFoodReportFullResponse
import com.app.recipe.Import.Vendor.USDA.Model.USDAFoodReportBasicResponse
import com.app.recipe.Import.Vendor.USDA.Model.USDAFoodReportStatsResponse

/**
 * This is the food retailer interface with the methods that all child 
 * retailers must implement. A new food vendor is to extend this interface
 * and add logic to the required methods.
 */
abstract class USDAVendor extends VendorBase {

  /**
   * Returns an USDAFoodReportFullResponse for the given product id. 
   */
  def getFoodReportFullProduct(productId: String) : USDAFoodReportFullResponse

  /**
   * Returns an USDAFoodReportFullResponse for the given product id. 
   */
  def getFoodReportBasicProduct(productId: String) : USDAFoodReportBasicResponse
  
  /**
   * Returns an USDAFoodReportFullResponse for the given product id. 
   */
  def getFoodReportStatsProduct(productId: String) : USDAFoodReportStatsResponse

  /**
   * Returns an USDANutrientReportFullResponse for the given product id. 
   */
//  def getNutrientReportStatsProduct(productId: String*) : USDANutrientReportStatsResponse

}