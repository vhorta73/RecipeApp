package com.app.recipe.Database.SQL.Core.Ingredient

import com.app.recipe.Model.Ingredient
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientNameRow
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientAttribute
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientSource
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientName
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientCore
import com.app.recipe.Model.IngredientManager
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientName

/**
 * The Ingredient Core Saver implementing required methods set by IngredientDatabaseCore 
 * interface, to save data onto the database.
 */
object SQLIngredientCoreSaver extends SQLIngredientCore {

  def saveIngredient( ingredient : Ingredient ) : Option[Ingredient] = {
    // TODO: Each table stores part of the recipe. Set Akka actors to process these in parallel.
    val ingredientName           = (new IngredientName()).saveRecord(ingredient)
    var ing = (new IngredientName).getIngredientByName(ingredient.name.get)
    println("GOT: "+ing)
var updatedIn = IngredientManager.add(Map( "id" -> ing.get(0).asInstanceOf[IngredientNameRow].id))(Some(ingredient)).get

println(s"UPDATED: $updatedIn")
//    val ingredientAttributes     = (new IngredientAttribute()).saveRecord(updatedIn)
//    val ingredientSources        = (new IngredientSource()).saveRecord(updatedIn)
    val ingredientCore           = (new IngredientCore()).saveRecord(updatedIn)
    println(ingredientCore)
    SQLIngredientCoreRetriever.getIngredientAggregatedById(ingredientName.get.asInstanceOf[List[IngredientNameRow]](0).id)
  }
}