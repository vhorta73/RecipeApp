package com.app.recipe.Import.Vendor.USDA

import com.app.recipe.Import.Vendor.USDA.FoodReport.USDABasicReport
import com.app.recipe.Import.Vendor.USDA.FoodReport.USDAFullReport
import com.app.recipe.Import.Vendor.USDA.FoodReport.USDAStatsReport
import com.app.recipe.Import.Vendor.USDA.Model.USDAFoodReportBasicResponse
import com.app.recipe.Import.Vendor.USDA.Model.USDAFoodReportFullResponse
import com.app.recipe.Import.Vendor.USDA.Model.USDAFoodReportStatsResponse
import com.app.recipe.Import.Vendor.USDA.Model.USDANutrientReportResponse
import com.app.recipe.Import.Vendor.USDA.Model.USDANutrientRequest
import com.app.recipe.Import.Vendor.USDA.NutrientsReport.USDANutrientReportImpl

/**
 * United States Department of Agriculture vendor access object.
 */
object Usda extends USDAVendor with USDAFullReport with USDABasicReport with USDAStatsReport 
  with USDANutrientReportImpl {

  /**
   * Given an USDA ingredient ID, gets the respective ingredient details page,
   * parse it and returns a well defined USDA typical ingredient object.
   */
  override def getFoodReportFullProduct(productId: String) : USDAFoodReportFullResponse = getFullReport(productId)
  
  /**
   * Given an USDA ingredient ID, gets the respective ingredient details page,
   * parse it and returns a well defined USDA typical ingredient object.
   */
  override def getFoodReportBasicProduct(productId: String) : USDAFoodReportBasicResponse = getBasicReport(productId)

  /**
   * Given an USDA ingredient ID, gets the respective ingredient details page,
   * parse it and returns a well defined USDA typical ingredient object.
   */
  override def getFoodReportStatsProduct(productId: String) : USDAFoodReportStatsResponse = getStatsReport(productId)

  /**
   * Given an USDA ingredient ID, gets the respective ingredient details page,
   * parse it and returns a well defined USDA typical ingredient object.
   */
  override def getNutrientReport(request: USDANutrientRequest) : USDANutrientReportResponse = getNutrientsReport(request)
}


