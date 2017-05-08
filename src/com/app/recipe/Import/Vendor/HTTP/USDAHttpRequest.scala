package com.app.recipe.Import.Vendor.HTTP

/**
 * The format types available for the USDA API.
 */
object USDAHttpRequestFormat extends Enumeration {
  type formatType = Value
  val JSON, XML = Value
}

/**
 * The types of query return available for the USDA API.
 */
object USDAHttpRequestType extends Enumeration {
  type requestType = Value
  val FULL, BASIC, STATS = Value 
}

/**
 * The types of query available for the USDA API.
 */
object USDAHttpRequestQueryType extends Enumeration {
  type requestQueryStyle = Value
  val LIST, REPORT, SEARCH, NUTRIENTS = Value
}
