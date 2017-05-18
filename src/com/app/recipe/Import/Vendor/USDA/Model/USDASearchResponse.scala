package com.app.recipe.Import.Vendor.USDA.Model

/**
 * The search response classes.
 */
case class USDASearchResponse( val list : USDASearchList = null )

case class USDASearchList(
    val q : String = ""
  , val sr : String = ""
  , val ds : String = ""
  , val start : Int = 0
  , val end  : Int = 50
  , val total : Int = 0
  , val group : String = ""
  , val sort : String = ""
  , val item : Array[USDASearchItem] = null
)

case class USDASearchItem(
    val offset : Int = 0
  , val group : String = ""
  , val name : String = ""
  , val ndbno : String = ""
  , val ds : String = ""
)