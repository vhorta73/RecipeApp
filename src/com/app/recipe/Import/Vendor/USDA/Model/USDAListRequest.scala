package com.app.recipe.Import.Vendor.USDA.Model

import com.app.recipe.Import.Vendor.HTTP.USDAHttpListRequestType
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestFormat
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestQueryType
import com.app.recipe.Import.Vendor.HTTP.USDAHttpSortRequestType

/**
 * The http request class for a nutrient list from the USDA api.
 * 
 * @param format USDAHttpRequestFormat
 * @param queryType USDAHttpRequestQueryType
 * @param listType USDAHttpListRequestTy[e
 * @param maxItems String
 * @param offset String
 * @param sort USDAHttpSortRequestType
 */
case class USDAListRequest(
  val format    : USDAHttpRequestFormat.formatType           = USDAHttpRequestFormat.JSON,
  val queryType : USDAHttpRequestQueryType.requestQueryStyle = USDAHttpRequestQueryType.LIST,
  val listType  : USDAHttpListRequestType.listRequestType    = USDAHttpListRequestType.ALL_NUTRIENTS, 
  val maxItems  : String                                     = "500",
  val offset    : String                                     = "0",
  val sort      : USDAHttpSortRequestType.sortRequestType    = USDAHttpSortRequestType.ID
)