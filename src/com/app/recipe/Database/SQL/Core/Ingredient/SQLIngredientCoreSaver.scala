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

    // If no ingredient name, then there is nothing to do.
    if ( ingredient.name.isEmpty ) {
      error(s"Ingredient has no name: $ingredient")
      return None
    }

    // Look for the ingredient id by its name.
    val ingredientName = (new IngredientName()).getIngredientByName(ingredient.name.get)

    // To save any ingredient, an id must be supplied.
    if ( ingredientName.isEmpty ) {
      info(s"No ingredient matched name: '${ingredient.name.get}'")
      return None
    }

    var updatedIngredient = IngredientManager.add(Map("id"->ingredientName.get(0).id))(Some(ingredient)).get

    // Saving attributes and sources from the ingredient onto respective tables.
    val ingredientAttributes     = (new IngredientAttribute()).saveRecord(updatedIngredient)
    val ingredientSources        = (new IngredientSource()).saveRecord(updatedIngredient)
    val ingredientCore           = (new IngredientCore()).saveRecord(updatedIngredient)

    SQLIngredientCoreRetriever.getIngredientAggregatedById(updatedIngredient.id.get)
  }
}