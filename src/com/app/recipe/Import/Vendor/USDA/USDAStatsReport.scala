package com.app.recipe.Import.Vendor.USDA

import com.app.recipe.Import.Vendor.HTTP.HttpBuilderFactory
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestFormat
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestQueryType
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestType
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum
import com.app.recipe.Import.Vendor.USDA.Model.USDAReportStatsResponse
import com.app.recipe.Log.RecipeLogging
import com.google.gson.Gson

/**
 * United States Department of Agriculture vendor access object dealing with
 * the stats report query and responses.
 */
abstract trait USDAStatsReport extends RecipeLogging {

  /**
   * Value class to indicate HTTP request which product id to query.
   */
  private final case class USDAId( var ndbno : String ) 

  /**
   * The static gson to convert objects to JSON and vice-versa.
   */
  private final val gson = new Gson
  
  /**
   * Given an USDA ingredient ID, gets the respective ingredient details page,
   * parse it and returns a well defined USDA typical ingredient object.
   */
  def getStatsReport(productId: String) : USDAReportStatsResponse = {
    val request = (
        USDAHttpRequestFormat.JSON,       // JSON / XML
        USDAHttpRequestQueryType.REPORT,  // NUTRIENTS / SEARCH / LIST / REPORT 
        USDAHttpRequestType.STATS,         // BASE / STATS / FULL
        gson.toJson(USDAId(productId))
    )
    
    val usdaHttpObj = HttpBuilderFactory.get(VendorEnum.USDA)
    val response = usdaHttpObj.get(request).asString

    var usda = new USDAReportStatsResponse
    if ( ! response.code.equals(200) ) {
      error(s"Could not download details for product $productId")
    }
    else {
      usda = gson.fromJson(response.body, usda.getClass)
    }
    usda
  }
}