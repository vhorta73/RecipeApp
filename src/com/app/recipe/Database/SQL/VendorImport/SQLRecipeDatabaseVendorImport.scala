package com.app.recipe.Database.SQL.VendorImport

import com.app.recipe.Import.Product.Model.ProductBase
import com.app.recipe.Import.Product.Model.ProductImport
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum
import com.app.recipe.Database.SQL.VendorImport.Tesco.SQLTescoImport

/**
 * The interface describing all the methods that must be implemented for each
 * respective database system.
 */
abstract trait SQLRecipeDatabaseVendorImport extends RecipeDatabaseVendorImport {

  /**
   * The recipe database name for all imports.
   */
  def getImportRecipeDatabaseName() : String = "recipe"
  
  /**
   * The recipe database table base name for all product imports for 
   * each vendor which will add their personalised name as prefix to
   * this base name to make up the final table name.
   */
  def getVendorImportProductTableBaseName() : String = "product_import"


  /**
   * The ingredients that are due to be updated.
   */
//  def getProductsToUpdate() : List[ProductBase] 
//{
//    val statement = SQLDatabase.getSQLHandle().createStatement()
//    val resultSet = statement.executeQuery("SELECT * FROM recipe.ingredient")
//      while ( resultSet.next() ) {
//        val id = resultSet.getString("id")
//        val name = resultSet.getString("name")
//        val vendor = resultSet.getString("vendor")
//        val user = resultSet.getString("user")
//        println("id = " + id )
//        println("name = " + name   )
//        println("vendor = " + vendor )
//      }
//    Nil
//  }
  

  /**
   * Imports a given product list to the database. If any of the given products 
   * can be found on the database under same unique key composed by: product_id 
   * and imported_date, product is updated instead of inserted, and the updated
   * date will also change accordingly. Created_date would remain the same.
   **/
  override def importProducts(productList : List[ProductBase]) {
    // Save each product on the right table.
    // TODO: Converted into Akka actors on multiple vendors.
    for( product <- productList ) {
      val vendorProduct = product.asInstanceOf[ProductImport]
      val vendor = vendorProduct.vendor
      vendor match {
        case VendorEnum.TESCO => SQLTescoImport.importProduct(vendorProduct)
        case _ => throw new IllegalArgumentException("Vendor not known")
      }
    }
  }
}