package com.app.recipe.Import.Vendor.USDA

import com.app.recipe.Import.Product.Model.ProductBase

/**
 * The USDA response class.
 */
case class USDAResponse( val report : USDAReport = null ) extends ProductBase {
  override val id : String = ""
}

case class USDAReport( 
    val sr        : String
  , val `type`    : String
  , val food      : USDAFood
  , val footnotes : Array[String]
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
  , val measures    : Array[USDAMeasures]
)

case class USDAMeasures(
    val label : String
  , val eqv   : Double
  , val eunit : String
  , val qty   : Double
  , val value : String
)