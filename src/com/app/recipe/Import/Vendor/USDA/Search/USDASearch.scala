package com.app.recipe.Import.Vendor.USDA.Search

import com.app.recipe.Import.Vendor.HTTP.HttpBuilderFactory
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestFormat
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestQueryType
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum
import com.app.recipe.Import.Vendor.USDA.Model.USDASearchRequest
import com.app.recipe.Import.Vendor.USDA.Model.USDASearchResponse
import com.app.recipe.Log.RecipeLogging
import com.google.gson.Gson

/**
 * United States Department of Agriculture vendor access object dealing with
 * the search query and responses.
 */
abstract trait USDASearch extends RecipeLogging {

  /**
   * Given an USDA ingredient ID, gets the respective ingredient details page,
   * parse it and returns a well defined USDA typical ingredient object.
   */
  def usdaSearch(query : USDASearchRequest) : USDASearchResponse = {
    val gson = new Gson
    val request = (
        USDAHttpRequestFormat.JSON,        // JSON / XML
        USDAHttpRequestQueryType.SEARCH,   // NUTRIENTS / SEARCH / LIST / REPORT 
        gson.toJson(query)
    )

    val usdaHttpObj = HttpBuilderFactory.get(VendorEnum.USDA)
    val response = usdaHttpObj.get(request).asString

    var usda = new USDASearchResponse
    if ( ! response.code.equals(200) ) {
      error(s"Could not download details for query $query")
    }
    else {
      usda = gson.fromJson(response.body, usda.getClass)
    }
    usda
  }
}