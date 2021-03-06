package com.app.recipe.Import.Vendor.URL.Model

import java.net.URL

import scala.util.Random

import com.app.recipe.Import.Vendor.URL.URLBuilder

/**
 * Building a Vendor URL for Asda.
 */
object ASDAURLBuilder extends URLBuilder {
  /**
   * The searching url for Asda after which the searching string is added.
   */
  private val SEARCH_URL_STRING = "https://groceries.asda.com/search/"
  
  /**
   * The url to query when with a product id.
   */
  private val PRODUCT_URL_STRING = "https://groceries.asda.com/product/"+Random.nextFloat()+"/"+Random.nextFloat()+"/"
  
  /**
   * The built search URL for given string.
   */
  override def getURLForSerach(searchString : String) : URL = new URL(SEARCH_URL_STRING+searchString)
  
  /**
   * The built specific URL for a given product ID.
   */
  override def getURLForProductID(productID : String) : URL = new URL(PRODUCT_URL_STRING + productID)
}