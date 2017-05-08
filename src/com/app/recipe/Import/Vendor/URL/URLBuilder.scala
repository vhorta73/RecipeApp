package com.app.recipe.Import.Vendor.URL

import java.net.URL

/**
 * The URL builder interface defining all methods and variables
 * that must be set on all URL vendor defined builders.
 */
trait URLBuilder {
  /**
   * The built search URL for given string.
   */
  def getURLForSerach(searchString : String) : URL
  
  /**
   * The built specific URL for a given product ID.
   */
  def getURLForProductID(productID : String) : URL
}