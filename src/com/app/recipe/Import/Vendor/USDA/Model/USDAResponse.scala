package com.app.recipe.Import.Vendor.USDA.Model

import com.app.recipe.Import.Product.Model.ProductBase

/**
 * The USDA Report Full response class.
 */
case class USDAReportFullResponse( val report : USDAReport = null ) extends ProductBase {
  override val id : String = ""
}

/**
 * The USDA Report Basci response class.
 */
case class USDAReportBasicResponse( val report : USDAReport = null ) extends ProductBase {
  override val id : String = ""
}

case class USDAReport( 
    val sr        : String
  , val `type`    : String
  , val food      : USDAFood 
  , val sources   : Array[USDASources]
  , val footnotes : Array[String]
  , val langual   : Array[String]
)

case class USDASources(
    val id      : Int
  , val title   : String
  , val authors : String
  , val vol     : String
  , val iss     : String
  , val year    : String
)

case class USDAFood(
    val ndbno     : String
  , val name      : String
  , val ds        : String
  , val ru        : String
  , val nutrients : Array[USDANutrients]
) 

case class USDANutrients(
    val nutrient_id : String
  , val name        : String
  , val group       : String
  , val unit        : String
  , val value       : String
  , val sourcecode  : Any
  , val dp          : String
  , val se          : String
  , val measures    : Array[USDAMeasures]
)

case class USDAMeasures(
    val label : String
  , val eqv   : Double
  , val eunit : String
  , val qty   : Double
  , val value : String
)