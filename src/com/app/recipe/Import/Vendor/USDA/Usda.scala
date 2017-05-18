package com.app.recipe.Import.Vendor.USDA

import com.app.recipe.Import.Vendor.USDA.List.USDAListRequest
import com.app.recipe.Import.Vendor.USDA.List.USDAListResponse
import com.app.recipe.Import.Vendor.USDA.List.USDALists
import com.app.recipe.Import.Vendor.USDA.Model.USDAFoodReportBasicResponse
import com.app.recipe.Import.Vendor.USDA.Model.USDAFoodReportFullResponse
import com.app.recipe.Import.Vendor.USDA.Model.USDAFoodReportStatsResponse
import com.app.recipe.Import.Vendor.USDA.Model.USDANutrientReportResponse
import com.app.recipe.Import.Vendor.USDA.Model.USDANutrientRequest
import com.app.recipe.Import.Vendor.USDA.NutrientsReport.USDANutrientReportImpl
import com.app.recipe.Import.Vendor.USDA.FoodReport.USDAFoodReports

/**
 * United States Department of Agriculture vendor access object.
 */
object Usda extends USDAVendor with USDAFoodReports with USDANutrientReportImpl with USDALists {

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
}