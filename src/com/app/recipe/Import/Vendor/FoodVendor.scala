package com.app.recipe.Import.Vendor

import com.app.recipe.Import.Product.Model.ProductBase

/**
 * This is the food retailer interface with the methods that all child 
 * retailers must implement. A new food vendor is to extend this interface
 * and add logic to the required methods.
 */
abstract class FoodVendor extends VendorBase {

  /**
   * Method to return the product details, implemented by each vendor class.
   */
  def getProductImport( productId : String ) : ProductBase
  
  /**
   * To be implemented by vendors with their specific searching engine, 
   * to return a list of all ProductDetails found.
   */
  def search( productName : String ) : List[ProductBase]
  
}