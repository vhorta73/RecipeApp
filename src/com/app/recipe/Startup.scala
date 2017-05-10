package com.app.recipe

import com.app.recipe.Import.Vendor.USDA.NutrientsReport.NutrientNames
import com.app.recipe.Import.Vendor.USDA.NutrientsReport.NutrientFactory
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum
import com.app.recipe.Import.Vendor.USDA.USDAVendor
import com.app.recipe.Import.Vendor.VendorFactory
import com.app.recipe.Import.Vendor.USDA.Model.USDANutrientRequest


/**
 * The startup for the Recipe project.
 */
object Startup extends App {

  override def main(args: Array[String]): Unit = {

    val v = VendorFactory.get(VendorEnum.USDA).asInstanceOf[USDAVendor]
//  //    val response = v.getFoodReportStatsProduct("04037")
    val request = USDANutrientRequest(ndbno = "04037", nutrients = Array("203"), fg = Array("0500"))
    val response = v.getNutrientReport(request)
    println(response)
    
//    println(NutrientFactory.get(NutrientNames.STARCH))

    //    response.report.food.nutrients.foreach { nut => println(nut) }
//    val coreDB = DatabaseFactory.getInstance[RecipeDatabaseCore](DatabaseMode.CORE)

//    val r = Recipe(
//         id    = Some(1)
//       , name    = Some("Carrot cake")
//       , version = Some(0)
//    )
//
//    var updatedRecipe = RecipeManager.add(Map(
//        "author"      -> List(s"Vasco",s"Vasco Horta",s"Horta")
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
//      , "ingredient_list"  -> List(IngredientElement(10, 13.43,"ml"),IngredientElement(12, 52.2,"kg"))
//      , "cooking_types"      -> List("cooking1","type1")
//      , "kitchen_utensils"  -> List("oven1","microwave1")
//      , "version"    -> List("0")
//    ))(Some(r)).get

//    println(coreDB.saveRecord(updatedRecipe))

    
//    val i = Ingredient(
//   //    id        = Some(10) 
//      name      = Some("cheese")
//     , source    = Some(List("Tesco"))
//     , attribute = Some(List("Large"))
//    )
//
//    var updatedIngredient = IngredientManager.add(Map(
//       "attributes"   -> List(s"Organic")
//    ))(Some(i)).get
//
////    println(coreDB.saveRecord(i))
//    println(coreDB.saveRecord(updatedIngredient))
//
  }
}
