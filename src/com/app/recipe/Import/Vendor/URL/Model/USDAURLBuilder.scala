package com.app.recipe.Import.Vendor.URL.Model

import java.net.URL

/**
 * Building a Vendor URL for USDA, the United Stated Department of Agriculture.
 */
object USDAURLBuilder extends URLBuilder {
  /**
   * The searching url for USDA after which the searching string is added.
   */
  private val SEARCH_URL_STRING = "https://api.nal.usda.gov/ndb/reports/V2?ndbno=01009&ndbno=01009&ndbno=45202763&ndbno=35193&type=b&format=json&api_key=DEMO_KEY"
  
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