package com.app.recipe.Import.Vendor.USDA.FoodReport

import com.app.recipe.Import.Vendor.HTTP.HttpBuilderFactory
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestType
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestFormat
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestQueryType
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum
import com.app.recipe.Import.Vendor.USDA.Model.USDAFoodReportFullResponse
import com.google.gson.Gson

/**
 * United States Department of Agriculture vendor access object dealing with
 * the full report query and responses.
 */
abstract trait USDAFullReport extends USDAFoodReport {

  /**
   * Given an USDA ingredient ID, gets the respective ingredient details page,
   * parse it and returns a well defined USDA typical ingredient object.
   */
  def getFullReport(productId: String) : USDAFoodReportFullResponse = {
    val gson = new Gson
    val request = (
        USDAHttpRequestFormat.JSON,       // JSON / XML
        USDAHttpRequestQueryType.REPORT,  // NUTRIENTS / SEARCH / LIST / REPORT 
        USDAHttpRequestType.FULL,         // BASE / STATS / FULL
        gson.toJson(USDAId(productId))
    )
    
    val usdaHttpObj = HttpBuilderFactory.get(VendorEnum.USDA)
    val response = usdaHttpObj.get(request).asString

    var usda = new USDAFoodReportFullResponse
    if ( ! response.code.equals(200) ) {
      error(s"Could not download details for product $productId")
    }
    else {
      usda = gson.fromJson(response.body, usda.getClass)
    }
    usda
  }
}