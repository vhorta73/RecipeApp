package com.app.recipe.Import.Vendor.USDA

import com.app.recipe.Import.Vendor.HTTP.HttpBuilderFactory
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum
import com.app.recipe.Import.Vendor.USDAVendor
import com.google.gson.Gson
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestFormat
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestQueryType
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestType
import com.app.recipe.Import.Vendor.USDA.Model.USDAFoodReportFullResponse
import com.app.recipe.Import.Vendor.USDA.Model.USDAFoodReportBasicResponse
import com.app.recipe.Import.Vendor.USDA.Model.USDAFoodReportStatsResponse

/**
 * United States Department of Agriculture vendor access object.
 */
object Usda extends USDAVendor with USDAFullReport with USDABasicReport with USDAStatsReport {

  /**
   * Given an USDA ingredient ID, gets the respective ingredient details page,
   * parse it and returns a well defined USDA typical ingredient object.
   */
  override def getReportFullProduct(productId: String) : USDAFoodReportFullResponse = getFullReport(productId)
  
  /**
   * Given an USDA ingredient ID, gets the respective ingredient details page,
   * parse it and returns a well defined USDA typical ingredient object.
   */
  override def getReportBasicProduct(productId: String) : USDAFoodReportBasicResponse = getBasicReport(productId)

  /**
   * Given an USDA ingredient ID, gets the respective ingredient details page,
   * parse it and returns a well defined USDA typical ingredient object.
   */
  override def getReportStatsProduct(productId: String) : USDAFoodReportStatsResponse = getStatsReport(productId)
}


