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
import com.app.recipe.Model.Ingredient
import com.app.recipe.Model.IngredientManager


/**
 * The startup for the Recipe project.
 */
object Startup extends App {

  override def main(args: Array[String]): Unit = {

    val coreDB = DatabaseFactory.getInstance[RecipeDatabaseCore](DatabaseMode.CORE)

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

    
    val i = Ingredient(
       id        = Some(1) 
     , name      = Some("banana")
     , source    = Some(List("Jamaica"))
     , attribute = Some(List("Yellow","Big"))
    )

    var updatedIngredient = IngredientManager.add(Map(
        "sources"      -> List(s"Vasco")
      , "attributes"   -> List(s"Vasco")
    ))(Some(i)).get

//    println(coreDB.saveRecord(i))
    println(coreDB.saveRecord(updatedIngredient))

  }
}
