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

/**
 * The types of query available for the USDA list API.
 */
object USDAHttpListRequestType extends Enumeration {
  type listRequestType = Value
  val FOOD, ALL_NUTRIENTS, SPECIALTY_NUTRIENTS, STANDARD_RELEASE_NUTRIENTS_ONLY, FOOD_GROUP = Value
}

/**
 * The sort types of query available for the USDA list API.
 */
object USDAHttpSortRequestType extends Enumeration {
  type sortRequestType = Value
  val NAME, ID = Value
}