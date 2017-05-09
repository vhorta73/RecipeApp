package com.app.recipe.Import.Vendor.USDA

import com.app.recipe.Import.Vendor.HTTP.HttpBuilderFactory
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum
import com.app.recipe.Import.Vendor.USDAVendor
import com.google.gson.Gson
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestFormat
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestQueryType
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestType
import com.app.recipe.Import.Vendor.USDA.Model.USDAReportFullResponse
import com.app.recipe.Import.Vendor.USDA.Model.USDAReportBasicResponse
import com.app.recipe.Import.Vendor.USDA.Model.USDAReportStatsResponse

/**
 * United States Department of Agriculture vendor access object.
 */
object Usda extends USDAVendor with USDAFullReport with USDABasicReport with USDAStatsReport {

  /**
   * Given an USDA ingredient ID, gets the respective ingredient details page,
   * parse it and returns a well defined USDA typical ingredient object.
   */
  override def getReportFullProduct(productId: String) : USDAReportFullResponse = getFullReport(productId)
  
  /**
   * Given an USDA ingredient ID, gets the respective ingredient details page,
   * parse it and returns a well defined USDA typical ingredient object.
   */
  override def getReportBasicProduct(productId: String) : USDAReportBasicResponse = getBasicReport(productId)

  /**
   * Given an USDA ingredient ID, gets the respective ingredient details page,
   * parse it and returns a well defined USDA typical ingredient object.
   */
  override def getReportStatsProduct(productId: String) : USDAReportStatsResponse = getStatsReport(productId)
}


