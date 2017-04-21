package com.app.recipe.Database.SQL.Core.Recipe.Modules.Tables

import java.sql.Date
import java.sql.Timestamp

import com.app.recipe.Database.Model.DatabaseGlobalVariables

/**
 * The main interface for all table objects.
 */
trait TableValueClass

/**
 * The recipe table row.
 */
case class RecipeNameRow(
    id                : Int
  , name              : String
  , version           : Int
  , created_by        : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , created_date      : Timestamp
  , last_updated_by   : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , last_updated_date : Timestamp
) extends TableValueClass

/**
 * The recipe main ingredient table row.
 */
case class RecipeMainIngredientRow(
    id                : Int
  , recipe_id         : Int
  , main_ingredient   : String
  , created_by        : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , created_date      : Timestamp
  , last_updated_by   : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , last_updated_date : Timestamp
) extends TableValueClass

/**
 * The recipe author table row.
 */
case class RecipeAuthorRow(
    id                : Int
  , recipe_id         : Int
  , author            : String
  , created_by        : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , created_date      : Timestamp
  , last_updated_by   : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , last_updated_date : Timestamp
) extends TableValueClass



