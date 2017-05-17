package com.app.recipe.Import.Vendor.USDA.List

/**
 * The Nutrient List Response
 */
case class USDAListResponse( val list : USDANutrientsList = null )
case class USDANutrientsList(
    val lt : String
  , val start : Int
  , val end : Int
  , val total : Int
  , val sr : String
  , val sort : String
  , val item : Array[USDANutrientsItem]
)

case class USDANutrientsItem(
    val offset : Int
  , val id : String
  , val name : String
)
