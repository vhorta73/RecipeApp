package com.app.recipe.Database.SQL.Core.Ingredient

import java.sql.Timestamp

import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientNameRow
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientTableRow
import com.app.recipe.Database.SQL.Core.SQLGlobalMethods
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientAttributeRow

/**
 * Setting the methods that all children must implement and sharing logic 
 * amongst all of them.
 */
abstract class SQLIngredientTableAccess extends SQLIngredientCore with SQLGlobalMethods[com.app.recipe.Model.Ingredient] {

}