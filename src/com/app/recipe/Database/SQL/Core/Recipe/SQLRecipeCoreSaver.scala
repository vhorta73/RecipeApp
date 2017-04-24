package com.app.recipe.Database.SQL.Core.Recipe

import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeName
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeNameRow
import com.app.recipe.Log.RecipeLogging
import com.app.recipe.Model.Recipe
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeAuthor
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeType


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
    val recipeName   = (new RecipeName()).saveRecord(recipe)
    val recipeAuthor = (new RecipeAuthor()).saveRecord(recipe)
    val recipeType   = (new RecipeType()).saveRecord(recipe)

    println("RecipeName: "+recipeName)
    println("RecipeAuthor: "+recipeAuthor)
    println("RecipeType: "+recipeType)

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