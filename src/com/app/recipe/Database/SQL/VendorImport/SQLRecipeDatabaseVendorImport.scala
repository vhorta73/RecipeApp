package com.app.recipe.Database.SQL.VendorImport

import com.app.recipe.Database.RecipeDatabaseVendorImport
import com.app.recipe.Database.SQL.VendorImport.Tesco.SQLTescoImport
import com.app.recipe.Import.Product.Model.ProductBase
import com.app.recipe.Import.Product.Model.ProductImport
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum

/**
 * The abstract interface implementing all the common method implementations that
 * are shared between all vendors.
 */
abstract trait SQLRecipeDatabaseVendorImport extends RecipeDatabaseVendorImport {

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