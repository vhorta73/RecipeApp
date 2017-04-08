package com.app.recipe.Import.Vendor.URL.Model

import java.net.URL

/**
 * Building a Vendor URL for Tesco.
 */
object TESCOURLBuilder extends URLBuilder {
  /**
   * The searching url for Tesco after which the searching string is added.
   */
  private val SEARCH_URL_STRING = "https://www.tesco.com/groceries/product/search/default.aspx?search=Search&searchBox="
  
  /**
   * The url to query when with a product id.
   */
  private val PRODUCT_URL_STRING = "https://www.tesco.com/groceries/product/details/?id="
  
  /**
   * The built search URL for given string.
   */
  override def getURLForSerach(searchString : String) : URL = new URL(SEARCH_URL_STRING+searchString)
  
  /**
   * The built specific URL for a given product ID.
   */
  override def getURLForProductID(productID : String) : URL = new URL(PRODUCT_URL_STRING + productID)
}