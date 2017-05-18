package com.app.recipe.Import.Vendor.USDA

import com.app.recipe.Import.Vendor.USDA.Model.USDAListRequest
import com.app.recipe.Import.Vendor.USDA.Model.USDAListResponse
import com.app.recipe.Import.Vendor.USDA.List.USDALists
import com.app.recipe.Import.Vendor.USDA.Model.USDAFoodReportBasicResponse
import com.app.recipe.Import.Vendor.USDA.Model.USDAFoodReportFullResponse
import com.app.recipe.Import.Vendor.USDA.Model.USDAFoodReportStatsResponse
import com.app.recipe.Import.Vendor.USDA.Model.USDANutrientReportResponse
import com.app.recipe.Import.Vendor.USDA.Model.USDANutrientRequest
import com.app.recipe.Import.Vendor.USDA.NutrientsReport.USDANutrientReports
import com.app.recipe.Import.Vendor.USDA.FoodReport.USDAFoodReports
import com.app.recipe.Import.Vendor.USDA.Model.USDASearchResponse
import com.app.recipe.Import.Vendor.USDA.Search.USDASearch
import com.app.recipe.Import.Vendor.USDA.Model.USDASearchRequest

/**
 * United States Department of Agriculture vendor access object.
 */
object Usda extends USDAVendor with USDAFoodReports with USDANutrientReports with USDALists 
  with USDASearch {

  /**
   * Given an USDA ingredient ID, gets the respective ingredient details page,
   * parse it and returns a well defined USDA typical ingredient object.
   */
  override def getFoodReportFullProduct(productId: String) : USDAFoodReportFullResponse = getUSDAFullReport(productId)
  
  /**
   * Given an USDA ingredient ID, gets the respective ingredient details page,
   * parse it and returns a well defined USDA typical ingredient object.
   */
  override def getFoodReportBasicProduct(productId: String) : USDAFoodReportBasicResponse = getUSDABasicReport(productId)

  /**
   * Given an USDA ingredient ID, gets the respective ingredient details page,
   * parse it and returns a well defined USDA typical ingredient object.
   */
  override def getFoodReportStatsProduct(productId: String) : USDAFoodReportStatsResponse = getUSDAStatsReport(productId)

  /**
   * Given an USDA ingredient ID, gets the respective ingredient details page,
   * parse it and returns a well defined USDA typical ingredient object.
   */
  override def getNutrientReport(request: USDANutrientRequest) : USDANutrientReportResponse = getUSDANutrientsReport(request)

  /**
   * Given an USDA ingredient ID, gets the respective ingredient details page,
   * parse it and returns a well defined USDA typical ingredient object.
   */
  override def getList(request: USDAListRequest) : USDAListResponse = getUSDAList(request)
  
  /**
   * Given an USDA Search query, returns the searched elements as USDASearchResponse.
   */
  override def search(request: USDASearchRequest) : USDASearchResponse = usdaSearch(request)
  
}