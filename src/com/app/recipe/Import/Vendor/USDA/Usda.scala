package com.app.recipe.Import.Vendor.USDA

import com.app.recipe.Import.Vendor.HTTP.HttpBuilderFactory
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum
import com.app.recipe.Import.Vendor.USDAVendor
import com.google.gson.Gson
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestFormat
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestQueryType
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestType

/**
 * United States Department of Agriculture vendor access object.
 */
object Usda extends USDAVendor {

  /**
   * Value class to indicate HTTP request which product id to query.
   */
  case class USDAId( var ndbno : String ) 

  /**
   * The static gson to convert objects to JSON and vice-versa.
   */
  final val gson = new Gson
  
  /**
   * Given an USDA ingredient ID, gets the respective ingredient details page,
   * parse it and returns a well defined USDA typical ingredient object.
   */
  override def getReportFullProduct(productId: String) : USDAReportFullResponse = {
    val request = (
        USDAHttpRequestFormat.JSON,       // JSON / XML
        USDAHttpRequestQueryType.REPORT,  // NUTRIENTS / SEARCH / LIST / REPORT 
        USDAHttpRequestType.FULL,         // BASE / STATS / FULL
        gson.toJson(USDAId(productId))
    )
    
    val usdaHttpObj = HttpBuilderFactory.get(VendorEnum.USDA)
    val response = usdaHttpObj.get(request).asString

    var usda = new USDAReportFullResponse
    if ( ! response.code.equals(200) ) {
      error(s"Could not download details for product $productId")
    }
    else {
      usda = gson.fromJson(response.body, usda.getClass)
    }
    usda
  }

//  /**
//   * Given an USDA ingredient ID, gets the respective ingredient details page,
//   * parse it and returns a well defined USDA typical ingredient object.
//   */
//  override def getReportBasicProduct(productId: String) : USDAResponse = {
//    val request = (
//        USDAHttpRequestFormat.JSON,       // JSON / XML
//        USDAHttpRequestQueryType.REPORT,  // NUTRIENTS / SEARCH / LIST / REPORT 
//        USDAHttpRequestType.BASIC,        // BASIC / STATS / FULL
//        gson.toJson(USDAId(productId))
//    )
//    
//    val usdaHttpObj = HttpBuilderFactory.get(VendorEnum.USDA)
//    val response = usdaHttpObj.get(request).asString
//
//    var usda = new USDAResponse
//    if ( ! response.code.equals(200) ) {
//      error(s"Could not download details for product $productId")
//    }
//    else {
//      usda = gson.fromJson(response.body, usda.getClass)
//    }
//    usda
//  }
//
//  /**
//   * Given an USDA ingredient ID, gets the respective ingredient details page,
//   * parse it and returns a well defined USDA typical ingredient object.
//   */
//  override def getReportStatsProduct(productId: String) : USDAResponse = {
//    val request = (
//        USDAHttpRequestFormat.JSON,       // JSON / XML
//        USDAHttpRequestQueryType.REPORT,  // NUTRIENTS / SEARCH / LIST / REPORT 
//        USDAHttpRequestType.STATS,        // BASE / STATS / FULL
//        gson.toJson(USDAId(productId))
//    )
//    
//    val usdaHttpObj = HttpBuilderFactory.get(VendorEnum.USDA)
//    val response = usdaHttpObj.get(request).asString
//
//    var usda = new USDAResponse
//    if ( ! response.code.equals(200) ) {
//      error(s"Could not download details for product $productId")
//    }
//    else {
//      usda = gson.fromJson(response.body, usda.getClass)
//    }
//    usda
//  }
//
//
}


