package com.app.recipe.Import.Vendor

import com.app.recipe.Import.Product.Model.ProductImport
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
  def getProductImport( productId : String ) : ProductImport
  
  /**
   * To be implemented by vendors with their specific searching engine, 
   * to return a list of all ProductDetails found.
   */
  def search( productName : String ) : List[Product]
  
}