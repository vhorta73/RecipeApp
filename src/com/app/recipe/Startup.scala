package com.app.recipe

import com.app.recipe.Import.Vendor.URL.Model.VendorEnum
import com.app.recipe.Import.Vendor.VendorFactory


/**
 * The startup for the Recipe project.
 */
object Startup extends App {
  override def main(args: Array[String]): Unit = {
    
  val vendor = VendorFactory.get(VendorEnum.TESCO)
  val productList = vendor.search("banana")//"banana")
  }
//  println("got: "+productList.size+" products")
//  var ingredientListToUpdate : List[ProductImport] = Nil

//  val dbObj : RecipeDatabase = DatabaseFactory.getInstance(DatabaseMode.TESCO_IMPORT)
//  var saved = 0
//  for( product <- productList ) {
//    var ingredient = VendorFactory.get(VendorEnum.TESCO)
//      .getProductDetails(product.asInstanceOf[ProductImport].id)
//    dbObj.asInstanceOf[RecipeDatabaseVendorImport]
//      .importProducts(List(ingredient.asInstanceOf[ProductImport]))
//    saved = saved + 1
//    println(saved)
//    ingredientListToUpdate = product.asInstanceOf[ProductImport] :: ingredientListToUpdate
//  }
//  println("Saving ...")
//  dbObj.asInstanceOf[RecipeDatabaseVendorImport].importProducts(ingredientListToUpdate)
//  println("done")

  
//  val list = vendor.getProductDetails("260752306")
//  println(list)//
  
}
