package com.app.recipe.Import.Vendor.USDA.Model

import com.app.recipe.Import.Vendor.HTTP.USDAHttpSearchSortType

/**
 * Value class to indicate HTTP request which product id to query.
 */
case class USDASearchRequest( 
    var q      : String = "" 
  , var ds     : String = ""
  , var fg     : String = ""
  , var sort   : String = "r"
  , var max    : String = "50"
  , var offset : String = "0"
) 

