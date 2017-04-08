package com.app.recipe.Database.SQL.VendorImport

import com.app.recipe.Import.Product.Model.ProductBase
import com.app.recipe.Database.RecipeDatabase

/**
 * The interface describing all the methods that must be implemented for each
 * respective database system.
 */
abstract trait RecipeDatabaseVendorImport extends RecipeDatabase {

//  /**
//   * The product due to be updated based on the time they have been stale.
//   */
//  def getProductsToUpdate() : List[ProductBase]

  /**
   * Imports a given product list to the database. If any of the given products 
   * can be found on the database under same unique key composed by: product_id 
   * and imported_date, product is updated instead of inserted, and the updated
   * date will also change accordingly. Created_date would remain the same.
   **/
  def importProducts(productList : List[ProductBase])

}
