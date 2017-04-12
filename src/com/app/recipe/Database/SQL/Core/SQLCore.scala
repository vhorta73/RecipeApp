package com.app.recipe.Database.SQL.Core

import com.app.recipe.Log.RecipeLogging

/**
 * This object implements the defined trait SQL methods and connects to the
 * SQL database to apply required operations.
 */
object SQLCore extends SQLRecipeDatabaseCore with RecipeLogging {

  override def getIngredients() : List[Product] = Nil
}