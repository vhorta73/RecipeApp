package com.app.recipe

import scala.concurrent.Await
import scala.util.Random

import com.app.recipe.Database.DatabaseFactory
import com.app.recipe.Database.Model.DatabaseMode
import com.app.recipe.Database.RecipeDatabaseCore
import com.app.recipe.Database.RecipeDatabaseCore
import scala.concurrent.Future
import scala.concurrent.impl.Future
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum
import com.app.recipe.Import.Vendor.VendorFactory


/**
 * The startup for the Recipe project.
 */
object Startup extends App {

  override def main(args: Array[String]): Unit = {
//    val times = 1000
//    val start = System.currentTimeMillis()
//    val list = List(
//      new Thread( new Runnable {def run() = { val coreDB = DatabaseFactory.getInstance[RecipeDatabaseCore](DatabaseMode.CORE); for( i <- 0 to times ) coreDB.getRecipeById(Random.nextInt()); val end = System.currentTimeMillis(); println(s"1 Start: $start, End: $end, Time: ${end - start}")  } })
//    , new Thread( new Runnable {def run() = { val coreDB = DatabaseFactory.getInstance[RecipeDatabaseCore](DatabaseMode.CORE); for( i <- 0 to times ) coreDB.getRecipeById(Random.nextInt()); val end = System.currentTimeMillis(); println(s"2 Start: $start, End: $end, Time: ${end - start}") } })
//    , new Thread( new Runnable {def run() = { val coreDB = DatabaseFactory.getInstance[RecipeDatabaseCore](DatabaseMode.CORE); for( i <- 0 to times ) coreDB.getRecipeById(Random.nextInt()); val end = System.currentTimeMillis(); println(s"3 Start: $start, End: $end, Time: ${end - start}") } })
//    , new Thread( new Runnable {def run() = { val coreDB = DatabaseFactory.getInstance[RecipeDatabaseCore](DatabaseMode.CORE); for( i <- 0 to times ) coreDB.getRecipeById(Random.nextInt()); val end = System.currentTimeMillis(); println(s"4 Start: $start, End: $end, Time: ${end - start}") } })
//    , new Thread( new Runnable {def run() = { val coreDB = DatabaseFactory.getInstance[RecipeDatabaseCore](DatabaseMode.CORE); for( i <- 0 to times ) coreDB.getRecipeById(Random.nextInt()); val end = System.currentTimeMillis(); println(s"5 Start: $start, End: $end, Time: ${end - start}") } })
//    , new Thread( new Runnable {def run() = { val coreDB = DatabaseFactory.getInstance[RecipeDatabaseCore](DatabaseMode.CORE); for( i <- 0 to times ) coreDB.getRecipeById(Random.nextInt()); val end = System.currentTimeMillis(); println(s"6 Start: $start, End: $end, Time: ${end - start}") } })
//    , new Thread( new Runnable {def run() = { val coreDB = DatabaseFactory.getInstance[RecipeDatabaseCore](DatabaseMode.CORE); for( i <- 0 to times ) coreDB.getRecipeById(Random.nextInt()); val end = System.currentTimeMillis(); println(s"7 Start: $start, End: $end, Time: ${end - start}") } })
//    , new Thread( new Runnable {def run() = { val coreDB = DatabaseFactory.getInstance[RecipeDatabaseCore](DatabaseMode.CORE); for( i <- 0 to times ) coreDB.getRecipeById(Random.nextInt()); val end = System.currentTimeMillis(); println(s"8 Start: $start, End: $end, Time: ${end - start}") } })
//    , new Thread( new Runnable {def run() = { val coreDB = DatabaseFactory.getInstance[RecipeDatabaseCore](DatabaseMode.CORE); for( i <- 0 to times ) coreDB.getRecipeById(Random.nextInt()); val end = System.currentTimeMillis(); println(s"8 Start: $start, End: $end, Time: ${end - start}") } })
//    , new Thread( new Runnable {def run() = { val coreDB = DatabaseFactory.getInstance[RecipeDatabaseCore](DatabaseMode.CORE); for( i <- 0 to times ) coreDB.getRecipeById(Random.nextInt()); val end = System.currentTimeMillis(); println(s"8 Start: $start, End: $end, Time: ${end - start}") } })
//    , new Thread( new Runnable {def run() = { val coreDB = DatabaseFactory.getInstance[RecipeDatabaseCore](DatabaseMode.CORE); for( i <- 0 to times ) coreDB.getRecipeById(Random.nextInt()); val end = System.currentTimeMillis(); println(s"8 Start: $start, End: $end, Time: ${end - start}") } })
//    , new Thread( new Runnable {def run() = { val coreDB = DatabaseFactory.getInstance[RecipeDatabaseCore](DatabaseMode.CORE); for( i <- 0 to times ) coreDB.getRecipeById(Random.nextInt()); val end = System.currentTimeMillis(); println(s"8 Start: $start, End: $end, Time: ${end - start}") } })
//    , new Thread( new Runnable {def run() = { val coreDB = DatabaseFactory.getInstance[RecipeDatabaseCore](DatabaseMode.CORE); for( i <- 0 to times ) coreDB.getRecipeById(Random.nextInt()); val end = System.currentTimeMillis(); println(s"8 Start: $start, End: $end, Time: ${end - start}") } })
//    , new Thread( new Runnable {def run() = { val coreDB = DatabaseFactory.getInstance[RecipeDatabaseCore](DatabaseMode.CORE); for( i <- 0 to times ) coreDB.getRecipeById(Random.nextInt()); val end = System.currentTimeMillis(); println(s"8 Start: $start, End: $end, Time: ${end - start}") } })
//    , new Thread( new Runnable {def run() = { val coreDB = DatabaseFactory.getInstance[RecipeDatabaseCore](DatabaseMode.CORE); for( i <- 0 to times ) coreDB.getRecipeById(Random.nextInt()); val end = System.currentTimeMillis(); println(s"8 Start: $start, End: $end, Time: ${end - start}") } })
//    , new Thread( new Runnable {def run() = { val coreDB = DatabaseFactory.getInstance[RecipeDatabaseCore](DatabaseMode.CORE); for( i <- 0 to times ) coreDB.getRecipeById(Random.nextInt()); val end = System.currentTimeMillis(); println(s"8 Start: $start, End: $end, Time: ${end - start}") } })
//    , new Thread( new Runnable {def run() = { val coreDB = DatabaseFactory.getInstance[RecipeDatabaseCore](DatabaseMode.CORE); for( i <- 0 to times ) coreDB.getRecipeById(Random.nextInt()); val end = System.currentTimeMillis(); println(s"8 Start: $start, End: $end, Time: ${end - start}") } })
//    , new Thread( new Runnable {def run() = { val coreDB = DatabaseFactory.getInstance[RecipeDatabaseCore](DatabaseMode.CORE); for( i <- 0 to times ) coreDB.getRecipeById(Random.nextInt()); val end = System.currentTimeMillis(); println(s"8 Start: $start, End: $end, Time: ${end - start}") } })
//    , new Thread( new Runnable {def run() = { val coreDB = DatabaseFactory.getInstance[RecipeDatabaseCore](DatabaseMode.CORE); for( i <- 0 to times ) coreDB.getRecipeById(Random.nextInt()); val end = System.currentTimeMillis(); println(s"8 Start: $start, End: $end, Time: ${end - start}") } })
//    , new Thread( new Runnable {def run() = { val coreDB = DatabaseFactory.getInstance[RecipeDatabaseCore](DatabaseMode.CORE); for( i <- 0 to times ) coreDB.getRecipeById(Random.nextInt()); val end = System.currentTimeMillis(); println(s"8 Start: $start, End: $end, Time: ${end - start}") } })
//    )
//    list.foreach { t => t.start()}

    
    val coreDB = DatabaseFactory.getInstance[RecipeDatabaseCore](DatabaseMode.CORE)
    println(coreDB.getRecipeById(3))
//    val newId = 2
    //coreDB.newRecipe(Map(
      //  "name" -> "My first Recipe",
        //"version" -> "3"
//        ))
//    println(s"Retrived id: "+coreDB.getNewRecipeId("My first Recipes", 2))//.getRecipeById(2)) 

    //    for( i <- 0 to 100000 ) coreDB.getRecipeById(Random.nextInt())
//    coreDB.getIngredient("Banana") : List[Ingredient]
//    val ingredientsList = coreDB.asInstanceOf[SQLRecipeDatabaseCore].getIngredients()
//    println(s"List: $ingredientsList")

    
//    val coreDB = DatabaseFactory.getInstance(DatabaseMode.CORE)
//    coreDB.getIngredient("Banana") : List[Ingredient]
//    val ingredientsList = coreDB.asInstanceOf[SQLRecipeDatabaseCore].getIngredients()
//    println(s"List: $ingredientsList")
    
//    val vendor = VendorFactory.get(VendorEnum.TESCO)
//    val productList = vendor.search("vegetarian")
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
