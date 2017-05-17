package com.app.recipe.Import.Vendor.USDA.List

import com.app.recipe.Import.Vendor.HTTP.HttpBuilderFactory
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum
import com.google.gson.Gson
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestFormat
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestQueryType
import com.app.recipe.Import.Vendor.HTTP.USDAHttpSortRequestType
import com.app.recipe.Import.Vendor.HTTP.USDAHttpListRequestType
import scalaj.http.HttpResponse

/**
 * United States Department of Agriculture vendor access object dealing with
 * the nutrient list query and responses.
 */
abstract trait USDALists extends USDAList {

  /**
   * Given an USDA list request, returns back the respective nutrient list.
   */
  def getUSDAList(request: USDAListRequest ) : USDAListResponse = {
    val gson = new Gson
    val usdaHttpObj = HttpBuilderFactory.get(VendorEnum.USDA)
    val response = usdaHttpObj.get((
         request.format
       , request.queryType
       , request.listType
       , request.maxItems
       , request.offset
       , request.sort
     )).asString

    var usda : USDAListResponse = USDAListResponse()
    if ( ! response.code.equals(200) ) {
      error(s"Could not download details for request $request")
    }
    else {
      usda = gson.fromJson(response.body, usda.getClass)
    }
    usda
  }
}