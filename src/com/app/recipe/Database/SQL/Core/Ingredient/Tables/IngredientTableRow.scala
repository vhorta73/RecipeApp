package com.app.recipe.Database.SQL.Core.Ingredient.Tables

import java.sql.Time
import java.sql.Timestamp

import com.app.recipe.Database.Model.DatabaseGlobalVariables
import com.app.recipe.Database.SQL.Core.TableRow

/**
 * The main interface for all table objects.
 */
trait IngredientTableRow extends TableRow

/**
 * The ingredient name table row.
 */
case class IngredientNameRow(
    id                : Int    = 0
  , name              : String
  , created_by        : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , created_date      : Timestamp = new Timestamp(System.currentTimeMillis())
  , last_updated_by   : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , last_updated_date : Timestamp = new Timestamp(System.currentTimeMillis())
) extends IngredientTableRow

/**
 * The ingredient core table row.
 */
case class IngredientCoreRow(
    id                : Int    = 0
  , ingredient_id     : Int
  , Type              : String
  , type_id           : Int
  , created_by        : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , created_date      : Timestamp = new Timestamp(System.currentTimeMillis())
  , last_updated_by   : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , last_updated_date : Timestamp = new Timestamp(System.currentTimeMillis())
) extends IngredientTableRow


/**
 * The ingredient attribute table row.
 */
case class IngredientAttributeRow(
    id                : Int    = 0
  , name              : String
  , created_by        : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , created_date      : Timestamp = new Timestamp(System.currentTimeMillis())
  , last_updated_by   : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , last_updated_date : Timestamp = new Timestamp(System.currentTimeMillis())
) extends IngredientTableRow

/**
 * The ingredient source table row.
 */
case class IngredientSourceRow(
    id                : Int    = 0
  , name              : String
  , created_by        : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , created_date      : Timestamp = new Timestamp(System.currentTimeMillis())
  , last_updated_by   : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , last_updated_date : Timestamp = new Timestamp(System.currentTimeMillis())
) extends IngredientTableRow

