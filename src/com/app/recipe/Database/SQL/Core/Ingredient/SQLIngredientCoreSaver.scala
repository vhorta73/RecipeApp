package com.app.recipe.Database.SQL.Core.Ingredient

import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientNameRow
import com.app.recipe.Model.IngredientManager
import com.app.recipe.Model.Ingredient
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientName
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientCore
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientSource
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientAttribute

/**
 * The Ingredient Core Saver implementing required methods set by IngredientDatabaseCore 
 * interface, to save data onto the database.
 */
object SQLIngredientCoreSaver extends SQLIngredientCore {

  /**
   * Saving the ingredient to all respective tables.
   * Ingredient must have an id, and the ingredient name must be known.
   * 
   * @param Ingredient
   * @return Option[Ingredient]
   */
  def saveIngredient( ingredient : Ingredient ) : Option[Ingredient] = {
    
    // To save any ingredient, an id must be supplied.
    if ( ingredient.id.isEmpty ) {
      info(s"No ingredient.id supplied for ingredient $ingredient")
      return None
    }

    // Look for the ingredient name.
    val ingredientName           = (new IngredientName()).saveRecord(ingredient)

    // If no ingredient name, then there is nothing linked to it.
    if ( ingredientName.isEmpty ) {
      info(s"Ingredient name was not found for ingredient $ingredient")
      return None
    }

    // Given we have saved/updated the ingredient name, we can now check which id we have.
    val ingredientByName = (new IngredientName).getIngredientByName(ingredient.name.get)

    // Saving attributes and sources from the ingredient onto respective tables.
    val ingredientAttributes     = (new IngredientAttribute()).saveRecord(ingredient)
    val ingredientSources        = (new IngredientSource()).saveRecord(ingredient)
    val ingredientCore           = (new IngredientCore()).saveRecord(ingredient)

    SQLIngredientCoreRetriever.getIngredientAggregatedById(ingredient.id.get)
  }
}