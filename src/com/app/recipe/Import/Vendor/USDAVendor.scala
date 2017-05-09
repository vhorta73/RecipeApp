package com.app.recipe.Import.Vendor

import com.app.recipe.Import.Vendor.USDA.Model.USDAReportFullResponse
import com.app.recipe.Import.Vendor.USDA.Model.USDAReportBasicResponse
import com.app.recipe.Import.Vendor.USDA.Model.USDAReportStatsResponse

/**
 * This is the food retailer interface with the methods that all child 
 * retailers must implement. A new food vendor is to extend this interface
 * and add logic to the required methods.
 */
abstract class USDAVendor extends VendorBase {

  /**
   * Returns an USDAReportFullResponse for the given product id. 
   */
  def getReportFullProduct(productId: String) : USDAReportFullResponse

  /**
   * Returns an USDAReportFullResponse for the given product id. 
   */
  def getReportBasicProduct(productId: String) : USDAReportBasicResponse
  
  /**
   * Returns an USDAReportFullResponse for the given product id. 
   */
  def getReportStatsProduct(productId: String) : USDAReportStatsResponse

}