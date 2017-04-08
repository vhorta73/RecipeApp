package com.app.recipe.Import.Vendor.Model

import com.typesafe.scalalogging.LazyLogging
import com.app.recipe.Log.RecipeLogging

/**
 * This is the top interface with the methods that all child classes must
 * implement. A new vendor is to extend this interface and add logic to 
 * the required methods.
 */
abstract class VendorBase extends RecipeLogging {

  /**
   * Method to return the product details, implemented by each vendor class.
   */
  def getProductDetails( productId : String ) : Product
  
  /**
   * To be implemented by vendors with their specific searching engine, 
   * to return a list of all ProductDetails found.
   */
  def search( productName : String ) : List[Product]
  
}