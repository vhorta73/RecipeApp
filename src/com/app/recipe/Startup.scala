package com.app.recipe

import com.app.recipe.Import.Vendor.URL.Model.VendorEnum
import com.app.recipe.Import.Vendor.USDA.USDAVendor
import com.app.recipe.Import.Vendor.VendorFactory
import com.app.recipe.Import.Vendor.USDA.List.USDAListRequest
import com.app.recipe.Import.Vendor.HTTP.USDAHttpListRequestType
import com.app.recipe.Import.Vendor.HTTP.USDAHttpSortRequestType

/**
 * The startup for the Recipe project.
 */
object Startup extends App {

  override def main(args: Array[String]): Unit = {

    val v = VendorFactory.get(VendorEnum.USDA).asInstanceOf[USDAVendor]
////  //    val response = v.getFoodReportStatsProduct("04037")
//    val request = USDANutrientRequest(
////        nutrients = Array(NutrientFactory.get(NutrientNames.CAFFEINE).nutriend_id), 
//        nutrients = Array(
//            NutrientFactory.get(NutrientNames.TOTAL_LIPID_FAT).nutriend_id,
//            NutrientFactory.get(NutrientNames.CHOLESTEROL).nutriend_id,
//            NutrientFactory.get(NutrientNames.ENERGY_KCAL).nutriend_id,
//            NutrientFactory.get(NutrientNames.ENERGY_KJ).nutriend_id,
//            NutrientFactory.get(NutrientNames.LACTOSE).nutriend_id,
//            NutrientFactory.get(NutrientNames.MAGNESIUM).nutriend_id,
//            NutrientFactory.get(NutrientNames.PROTEIN).nutriend_id,
//            NutrientFactory.get(NutrientNames.TOTAL_SUGARS).nutriend_id
//        ),
//        fg = Array(
//            FoodGroupFactory.get(FoodGroupNames.VEGETABLES_AND_VEGETABLES_PRODUCTS).food_group_id
//            ), 
//        max = "1500",
//        ndbno = "09040"        
//    )
    var offset : Int = 0
    for( i <- 183900.to(184000).by(50) ) {
      var response = v.getList(
        USDAListRequest(
          listType = USDAHttpListRequestType.FOOD, 
          maxItems = "50", 
          offset = s"$i",
          sort = USDAHttpSortRequestType.NAME
        )
      )
      response.list.item.foreach{ p => println(p.name)}
    }
//    println(response.list.item(0).name)
    
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
