package com.app.recipe.Database.SQL.Core.Recipe

import com.app.recipe.Log.RecipeLogging
import com.app.recipe.Model.Recipe

/**
 * The Recipe Core Saver implementing required methods set by RecipeDatabaseCore 
 * interface, to save data onto the database.
 */
object SQLRecipeCoreSaver extends SQLRecipeCore with RecipeLogging {

  def getNewRecipeId( name : String, version : Int ) : Option[Int] = {
    None
  }
  def update( recipe : Recipe ) : Option[Recipe] = {
    None
  }
}