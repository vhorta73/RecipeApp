package com.app.recipe.Import.Vendor.USDA.FoodReport

import com.app.recipe.Import.Vendor.HTTP.HttpBuilderFactory
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestFormat
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestQueryType
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestType
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum
import com.app.recipe.Import.Vendor.USDA.Model.USDAFoodReportBasicResponse
import com.google.gson.Gson
import com.app.recipe.Import.Vendor.USDA.Model.USDAFoodReportStatsResponse
import com.app.recipe.Import.Vendor.USDA.Model.USDAFoodReportFullResponse
import com.app.recipe.Log.RecipeLogging
import com.app.recipe.Import.Product.Model.ProductBase
import scalaj.http.HttpRequest
import com.app.recipe.Import.Vendor.HTTP.HttpBuilder
import scalaj.http.HttpResponse
import com.app.recipe.Import.Product.Model.ProductBase

/**
 * United States Department of Agriculture vendor access object dealing with
 * the basic report query and responses.
 */
abstract trait USDAFoodReports extends RecipeLogging {

  /**
   * Value class to indicate HTTP request which product id to query.
   */
  private final case class USDAId( var ndbno : String ) 

  /**
   * Given an USDA ingredient ID, gets the respective ingredient details page,
   * parse it and returns a well defined USDA typical ingredient object.
   */
  def getUSDABasicReport(productId: String) : USDAFoodReportBasicResponse = {
    val gson = new Gson
    val response = getResponse(
        USDAHttpRequestFormat.JSON,        // JSON / XML
        USDAHttpRequestQueryType.REPORT,   // NUTRIENTS / SEARCH / LIST / REPORT 
        USDAHttpRequestType.STATS,         // BASE / STATS / FULL
        gson.toJson(USDAId(productId))
    )
    
    var usda = new USDAFoodReportBasicResponse
    if ( ! response.code.equals(200) ) {
      error(s"Could not download details for product $productId")
    }
    else {
      usda = gson.fromJson(response.body, usda.getClass)
    }
    usda
  }
  
  /**
   * Given an USDA ingredient ID, gets the respective ingredient details page,
   * parse it and returns a well defined USDA typical ingredient object.
   */
  def getUSDAFullReport(productId: String) : USDAFoodReportFullResponse = {
    val gson = new Gson
    val response = getResponse(
        USDAHttpRequestFormat.JSON,       // JSON / XML
        USDAHttpRequestQueryType.REPORT,  // NUTRIENTS / SEARCH / LIST / REPORT 
        USDAHttpRequestType.FULL,         // BASE / STATS / FULL
        gson.toJson(USDAId(productId))
    )
    
    var usda = new USDAFoodReportFullResponse
    if ( ! response.code.equals(200) ) {
      error(s"Could not download details for product $productId")
    }
    else {
      usda = gson.fromJson(response.body, usda.getClass)
    }
    usda
  }

  /**
   * Given an USDA ingredient ID, gets the respective ingredient details page,
   * parse it and returns a well defined USDA typical ingredient object.
   */
  def getUSDAStatsReport(productId: String) : USDAFoodReportStatsResponse = {
    val gson = new Gson
    val response = getResponse(
        USDAHttpRequestFormat.JSON,       // JSON / XML
        USDAHttpRequestQueryType.REPORT,  // NUTRIENTS / SEARCH / LIST / REPORT 
        USDAHttpRequestType.STATS,        // BASE / STATS / FULL
        gson.toJson(USDAId(productId))
    )
    
    var usda = new USDAFoodReportStatsResponse
    if ( ! response.code.equals(200) ) {
      error(s"Could not download details for product $productId")
    }
    else {
      usda = gson.fromJson(response.body, usda.getClass)
    }
    usda
  }

  /**
   * The http response for the given request.
   */
  private final def getResponse( request : Any ) : HttpResponse[String] = {
    val usdaHttpObj = HttpBuilderFactory.get(VendorEnum.USDA)
    usdaHttpObj.get(request).asString
  }
}