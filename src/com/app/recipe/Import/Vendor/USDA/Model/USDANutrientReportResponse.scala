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
case class USDANutrientReportResponse( val report : USDANutrientsReport = null ) extends ProductBase {
  override val id : String = ""
}

case class USDANutrientsReport(
    val sr     : String
  , val groups : Array[USDANutrientsGroups]
  , val subset : String
  , val end    : Int
  , val start  : Int
  , val total  : Int
  , val foods  : Array[USDANutrientsFoods]
)

case class USDANutrientsGroups(
    val id           : String
  , val descriptions : String
)

case class USDANutrientsFoods(
    val ndbno     : String
  , val name      : String
  , val weight    : Double
  , val measure   : String
  , val nutrients : Array[USDANutrientsNutrients]
)

case class USDANutrientsNutrients(
    val nutrient_id : String
  , val nutrient    : String
  , val unit        : String
  , val value       : String
  , val gm          : Double
)