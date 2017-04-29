package com.app.recipe.Database.SQL.Core.Recipe.Tables

import java.sql.Time
import java.sql.Timestamp

import com.app.recipe.Database.Model.DatabaseGlobalVariables
import com.app.recipe.Database.SQL.Core.TableRow

/**
 * The Recipe Table Row
 */
trait RecipeTableRow extends TableRow

/**
 * The recipe table row.
 */
case class RecipeNameRow(
    id                : Int
  , name              : String
  , version           : Int
  , created_by        : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , created_date      : Timestamp = new Timestamp(System.currentTimeMillis())
  , last_updated_by   : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , last_updated_date : Timestamp = new Timestamp(System.currentTimeMillis())
) extends RecipeTableRow

/**
 * The recipe main ingredient table row.
 */
case class RecipeMainIngredientRow(
    id                : Int = 0
  , recipe_id         : Int
  , main_ingredient   : String
  , created_by        : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , created_date      : Timestamp = new Timestamp(System.currentTimeMillis())
  , last_updated_by   : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , last_updated_date : Timestamp = new Timestamp(System.currentTimeMillis())
) extends RecipeTableRow

/**
 * The recipe ingredient table row.
 */
case class RecipeIngredientRow(
    id                : Int = 0
  , recipe_id         : Int
  , ingredient_id     : Int
  , quantity          : Double
  , unit              : String
  , created_by        : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , created_date      : Timestamp = new Timestamp(System.currentTimeMillis())
  , last_updated_by   : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , last_updated_date : Timestamp = new Timestamp(System.currentTimeMillis())
) extends RecipeTableRow

/**
 * The recipe author table row.
 */
case class RecipeAuthorRow(
    id                : Int = 0
  , recipe_id         : Int
  , author            : String
  , created_by        : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , created_date      : Timestamp = new Timestamp(System.currentTimeMillis())
  , last_updated_by   : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , last_updated_date : Timestamp = new Timestamp(System.currentTimeMillis())
) extends RecipeTableRow

/**
 * The recipe cooking type table row.
 */
case class RecipeCookingTypeRow(
    id                : Int = 0
  , recipe_id         : Int
  , cooking_type      : String
  , created_by        : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , created_date      : Timestamp = new Timestamp(System.currentTimeMillis())
  , last_updated_by   : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , last_updated_date : Timestamp = new Timestamp(System.currentTimeMillis())
) extends RecipeTableRow

/**
 * The recipe kitchen utensil table row.
 */
case class RecipeUtensilsRow(
    id                : Int = 0
  , recipe_id         : Int
  , kitchen_utensil   : String
  , created_by        : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , created_date      : Timestamp = new Timestamp(System.currentTimeMillis())
  , last_updated_by   : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , last_updated_date : Timestamp = new Timestamp(System.currentTimeMillis())
) extends RecipeTableRow

/**
 * The recipe source table row.
 */
case class RecipeSourceRow(
    id                : Int = 0
  , recipe_id         : Int
  , source            : String
  , created_by        : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , created_date      : Timestamp = new Timestamp(System.currentTimeMillis())
  , last_updated_by   : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , last_updated_date : Timestamp = new Timestamp(System.currentTimeMillis())
) extends RecipeTableRow

/**
 * The recipe course table row.
 */
case class RecipeCourseRow(
    id                : Int = 0
  , recipe_id         : Int
  , course            : String
  , created_by        : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , created_date      : Timestamp = new Timestamp(System.currentTimeMillis())
  , last_updated_by   : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , last_updated_date : Timestamp = new Timestamp(System.currentTimeMillis())
) extends RecipeTableRow

/**
 * The recipe description table row.
 */
case class RecipeDescriptionRow(
    id                : Int = 0
  , recipe_id         : Int
  , description       : String
  , created_by        : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , created_date      : Timestamp = new Timestamp(System.currentTimeMillis())
  , last_updated_by   : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , last_updated_date : Timestamp = new Timestamp(System.currentTimeMillis())
) extends RecipeTableRow

/**
 * The recipe difficulty table row.
 */
case class RecipeDifficultyRow(
    id                : Int = 0
  , recipe_id         : Int
  , difficulty        : Int
  , created_by        : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , created_date      : Timestamp = new Timestamp(System.currentTimeMillis())
  , last_updated_by   : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , last_updated_date : Timestamp = new Timestamp(System.currentTimeMillis())
) extends RecipeTableRow

/**
 * The recipe duration table row.
 */
case class RecipeDurationRow(
    id                : Int = 0
  , recipe_id         : Int
  , Type              : String
  , duration          : Time
  , created_by        : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , created_date      : Timestamp = new Timestamp(System.currentTimeMillis())
  , last_updated_by   : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , last_updated_date : Timestamp = new Timestamp(System.currentTimeMillis())
) extends RecipeTableRow

/**
 * The recipe rating table row.
 */
case class RecipeRatingRow(
    id                : Int = 0
  , recipe_id         : Int
  , rating            : Int
  , created_by        : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , created_date      : Timestamp = new Timestamp(System.currentTimeMillis())
  , last_updated_by   : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , last_updated_date : Timestamp = new Timestamp(System.currentTimeMillis())
) extends RecipeTableRow

/**
 * The recipe stage table row.
 */
case class RecipeStageRow(
    id                : Int = 0
  , recipe_id         : Int
  , step_id           : Int
  , step_name         : String
  , description       : String
  , created_by        : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , created_date      : Timestamp = new Timestamp(System.currentTimeMillis())
  , last_updated_by   : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , last_updated_date : Timestamp = new Timestamp(System.currentTimeMillis())
) extends RecipeTableRow

/**
 * The recipe style table row.
 */
case class RecipeStyleRow(
    id                : Int = 0
  , recipe_id         : Int
  , style             : String
  , created_by        : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , created_date      : Timestamp = new Timestamp(System.currentTimeMillis())
  , last_updated_by   : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , last_updated_date : Timestamp = new Timestamp(System.currentTimeMillis())
) extends RecipeTableRow

/**
 * The recipe tag table row.
 */
case class RecipeTagRow(
    id                : Int = 0
  , recipe_id         : Int
  , tag               : String
  , created_by        : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , created_date      : Timestamp = new Timestamp(System.currentTimeMillis())
  , last_updated_by   : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , last_updated_date : Timestamp = new Timestamp(System.currentTimeMillis())
) extends RecipeTableRow

/**
 * The recipe type table row.
 */
case class RecipeTypeRow(
    id                : Int = 0
  , recipe_id         : Int
  , recipeType        : String
  , created_by        : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , created_date      : Timestamp = new Timestamp(System.currentTimeMillis())
  , last_updated_by   : String = DatabaseGlobalVariables.getDeaultSystemUsername()
  , last_updated_date : Timestamp = new Timestamp(System.currentTimeMillis())
) extends RecipeTableRow

