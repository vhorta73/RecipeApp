package com.app.recipe.Import.Vendor.USDA.Model

import com.app.recipe.Import.Product.Model.ProductBase

/**
 * Value class to indicate HTTP request which product id to query.
 */
case class USDANutrientRequest( 
    var nutrients : Array[String] = Array()
  , var fg        : Array[String] = Array()
  , var ndbno     : String        = ""
  , var max       : String        = ""
  , var offset    : String        = ""
)

/**
 * The USDA Nutrients Report response class.
 */
case class USDANutrientReportResponse( val report : String = null ) extends ProductBase {
  override val id : String = ""
}
