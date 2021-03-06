package com.app.recipe.Import.Vendor.USDA.NutrientsReport

import com.app.recipe.Import.Vendor.HTTP.HttpBuilderFactory
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestFormat
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestQueryType
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum
import com.app.recipe.Import.Vendor.USDA.Model.USDANutrientReportResponse
import com.app.recipe.Import.Vendor.USDA.Model.USDANutrientRequest
import com.google.gson.Gson

/**
 * United States Department of Agriculture vendor access object dealing with
 * the basic report query and responses.
 */
abstract trait USDANutrientReports extends USDANutrientReport {

  /**
   * Given an USDA ingredient ID, gets the respective ingredient details page,
   * parse it and returns a well defined USDA typical ingredient object.
   */
  def getUSDANutrientsReport(nutrientRequest: USDANutrientRequest) : USDANutrientReportResponse = {
    val gson = new Gson
    val request = (
        USDAHttpRequestFormat.JSON,          // JSON / XML
        USDAHttpRequestQueryType.NUTRIENTS,  // NUTRIENTS / SEARCH / LIST / REPORT 
        gson.toJson(nutrientRequest)
    )

    val usdaHttpObj = HttpBuilderFactory.get(VendorEnum.USDA)
    val response = usdaHttpObj.get(request).asString

    var usda = new USDANutrientReportResponse
    if ( ! response.code.equals(200) ) {
      error(s"Could not download details for nutrient request: $nutrientRequest")
    }
    else {
      usda = gson.fromJson(response.body, usda.getClass)
    }
    usda
  }
}