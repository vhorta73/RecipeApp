package com.app.recipe.Database.SQL.Core.Recipe

import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeName
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeNameRow
import com.app.recipe.Log.RecipeLogging
import com.app.recipe.Model.Recipe
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeAuthor
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeType
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeStyle
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeTag
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeStage
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeDuration
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeCourse
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeDescription
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeDifficulty
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeMainIngredient
import com.app.recipe.Model.RecipeRating
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeRating
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeSource
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeIngredient


/**
 * The Recipe Core Saver implementing required methods set by RecipeDatabaseCore 
 * interface, to save data onto the database.
 */
object SQLRecipeCoreSaver extends SQLRecipeCore with RecipeLogging {

  /**
   * The aggregated version of Recipe.
   */
  def getRecipeByNameAndVersion( name : String, version : Int ) : Option[Recipe] = SQLRecipeCoreRetriever.getRecipeAggregatedById(version)

  def saveRecipe( recipe : Recipe ) : Option[Recipe] = {
    // TODO: Here we only deal with separated tables and need to aggregate them.
    val recipeName           = (new RecipeName()).saveRecord(recipe)
    val recipeAuthor         = (new RecipeAuthor()).saveRecord(recipe)
    val recipeCourse         = (new RecipeCourse()).saveRecord(recipe)
    val recipeDescription    = (new RecipeDescription()).saveRecord(recipe)
    val recipeDifficulty     = (new RecipeDifficulty()).saveRecord(recipe)
    val recipeDuration       = (new RecipeDuration()).saveRecord(recipe)
    val recipeIngredient     = (new RecipeIngredient()).saveRecord(recipe)
    val recipeMainIngredient = (new RecipeMainIngredient()).saveRecord(recipe)
    val recipeRating         = (new RecipeRating()).saveRecord(recipe)
    val recipeSource         = (new RecipeSource()).saveRecord(recipe)
    val recipeStages         = (new RecipeStage()).saveRecord(recipe)
    val recipeType           = (new RecipeType()).saveRecord(recipe)
    val recipeStyle          = (new RecipeStyle()).saveRecord(recipe)
    val recipeTags           = (new RecipeTag()).saveRecord(recipe)

    println(s"RecipeName: $recipeName")
//    println(s"RecipeAuthor: $recipeAuthor")
//    println(s"RecipeCourse: $recipeCourse")
//    println(s"RecipeDescription: $recipeDescription")
//    println(s"RecipeDifficulty: $recipeDifficulty")
//    println(s"RecipeDuration: $recipeDuration")
//    println(s"RecipeMainIngredient: $recipeMainIngredient")
//    println(s"RecipeRating: $recipeRating")
//    println(s"RecipeSource: $recipeSource")
    println(s"RecipeIngredient: $recipeIngredient")

//    println(s"RecipeType: $recipeType")
//    println(s"RecipeStyle: $recipeStyle")
//    println(s"RecipeTags: $recipeTags")
//    println(s"RecipeStages: $recipeStages")

    SQLRecipeCoreRetriever.getRecipeAggregatedById(recipe.id.get)
    
//    .get.getRecipeByNameAndVersion(name, version)
//    var finalRecipe : Recipe = null
//    if ( recipe.isEmpty ) {
//      (new RecipeName()).saveRecord(RecipeNameRow(0,name,version))
//      recipe = (new RecipeName()).getRecipeByNameAndVersion(name, version)
//      finalRecipe = Recipe(
//          id = recipe.get.id
//      )
//      
//    }
////    .saveRecord(RecipeNameRow(
////    id                = 4
////  , name              = "My Second Recipe"
////  , version           = 1
////        )
////     )
//     Some(recipe.get.asInstanceOf[Recipe])
    None
  }
}