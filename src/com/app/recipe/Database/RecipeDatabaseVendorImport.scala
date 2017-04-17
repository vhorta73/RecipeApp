package com.app.recipe.Database

import com.app.recipe.Database
import com.app.recipe.Import.Product.Model.ProductBase

/**
 * Abstract interface defining core information shared across all database 
 * protocols e.g. MySQL, MongoDB, etc...
 */
abstract trait RecipeDatabaseVendorImport extends RecipeDatabase {

  /**
   * The recipe database name for all imports.
   */
  def getImportRecipeDatabaseName() : String = "recipe_vendor"

  /**
   * Imports a given product list to the database. If any of the given products 
   * can be found on the database under same unique key composed by: product_id 
   * and imported_date, product is updated instead of inserted, and the updated
   * date will also change accordingly. Created_date would remain the same.
   **/
  def importProducts(productList : List[ProductBase])
  
  /**
   * The recipe database table base name for all product imports for 
   * each vendor which will add their personalised name as prefix to
   * this base name to make up the final table name.
   */
  def getVendorImportProductTableBaseName() : String = "product"

}
