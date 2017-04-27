package com.app.recipe

import com.app.recipe.Database.DatabaseFactory
import com.app.recipe.Database.Model.DatabaseMode
import com.app.recipe.Database.RecipeDatabaseCore
import com.app.recipe.Model.Recipe
import com.app.recipe.Model.RecipeManager
import com.app.recipe.Model.Stage
import com.app.recipe.Model.Duration
import java.sql.Time
import com.app.recipe.Model.IngredientElement


/**
 * The startup for the Recipe project.
 */
object Startup extends App {

  override def main(args: Array[String]): Unit = {
//for ( i <- 1 to 1000) {
    val coreDB = DatabaseFactory.getInstance[RecipeDatabaseCore](DatabaseMode.CORE)

    val r = Recipe(
         id    = Some(1)
       , name    = Some("Carrot cake")
       , version = Some(0)
    )

    var updatedRecipe = RecipeManager.add(Map(
        "author"      -> List(s"Vasco",s"Vasco Horta",s"Horta")
//      , "recipeType"  -> List("Cake","Plain")
//      , "recipeStyle" -> List("w/garlic")
//      , "tags"        -> List("tag1","description1","tag3","description 2")
//      , "stages"      -> List(Stage(10,"tag1","description1"),Stage(2,"tag3","description 2"))
//      , "duration"    -> List(Duration("preparation", Time.valueOf("00:05:00")))
//      , "course"      -> List("main","dessert")
//      , "difficulty"  -> 2
//      , "main_ingredient"  -> List("")
//      , "description" -> "Recipe description 2"
//      , "rating"  -> 4
//      , "source"  -> List("Portugal","Tesco","Belgium Cow")
      , "ingredient_list"  -> List(IngredientElement(10, 13.43,"ml"),IngredientElement(12, 52.2,"kg"))
//      , "version"    -> List("0")
    ))(Some(r)).get

    coreDB.saveRecord(updatedRecipe)

    println(coreDB.getRecipeById(1))
//    println(coreDB.saveRecord(updatedRecipe))
//}
    //.getRecipeById(3))
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
