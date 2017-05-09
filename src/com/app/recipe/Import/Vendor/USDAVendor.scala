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
   * Returns an USDAReportFullResponse for the given product id. 
   */
  def getReportFullProduct(productId: String) : USDAFoodReportFullResponse

  /**
   * Returns an USDAReportFullResponse for the given product id. 
   */
  def getReportBasicProduct(productId: String) : USDAFoodReportBasicResponse
  
  /**
   * Returns an USDAReportFullResponse for the given product id. 
   */
  def getReportStatsProduct(productId: String) : USDAFoodReportStatsResponse

}