package com.app.recipe.Database.SQL.Core.Recipe

import java.sql.Time
import java.sql.Timestamp

import scala.collection.immutable.HashMap

import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeAuthorRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeCourseRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeDescriptionRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeDifficultyRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeDurationRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeMainIngredientRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeNameRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeRatingRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeSourceRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeStageRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeStyleRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeTagRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.RecipeTypeRow
import com.app.recipe.Database.SQL.Core.Recipe.Tables.TableRow
import com.app.recipe.Database.SQL.SQLDatabaseHandle

/**
 * Setting the methods that all children must implement and sharing logic 
 * amongst all of them.
 */
abstract class SQLRecipeTableAccess extends SQLTableAccess {
  // TODO: Know who is requesting these methods...
  protected final val last_updated_by : String = "Me"

  /**
   * Method to get one or more rows by recipe id.
   * //TODO: This method will cause issues if core will have tables without recipe id...
   */
  def getRecipeId( id : Int ) : Option[List[TableRow]]
  
  /**
   * Returning the respective object depending on the table and data supplied.
   */
  protected def getObject( data : Map[String,String], table : String ) : TableRow = table match {
    case "recipe" => {
      val created_date      : Timestamp = Timestamp.valueOf(data("created_date").toString())
      val last_updated_date : Timestamp = Timestamp.valueOf(data("last_updated_date").toString())
      RecipeNameRow( 
          id                = data("id").toInt
        , name              = data("name") 
        , version           = data("version").toInt 
        , created_by        = data("created_by")
        , created_date      = created_date
        , last_updated_by   = data("last_updated_by") 
        , last_updated_date = last_updated_date
      )
    }
    case "recipe_main_ingredient" => {
      val created_date      : Timestamp = Timestamp.valueOf(data("created_date").toString())
      val last_updated_date : Timestamp = Timestamp.valueOf(data("last_updated_date").toString())
      RecipeMainIngredientRow( 
          id                = data("id").toInt
        , recipe_id         = data("recipe_id").toInt 
        , main_ingredient   = data("main_ingredient") 
        , created_by        = data("created_by")
        , created_date      = created_date
        , last_updated_by   = data("last_updated_by") 
        , last_updated_date = last_updated_date
      )
    }
    case "recipe_author" => {
      val created_date      : Timestamp = Timestamp.valueOf(data("created_date").toString())
      val last_updated_date : Timestamp = Timestamp.valueOf(data("last_updated_date").toString())
      RecipeAuthorRow( 
          id                = data("id").toInt
        , recipe_id         = data("recipe_id").toInt 
        , author            = data("author") 
        , created_by        = data("created_by")
        , created_date      = created_date
        , last_updated_by   = data("last_updated_by") 
        , last_updated_date = last_updated_date
      )
    }
    case "recipe_source" => {
      val created_date      : Timestamp = Timestamp.valueOf(data("created_date").toString())
      val last_updated_date : Timestamp = Timestamp.valueOf(data("last_updated_date").toString())
      RecipeSourceRow( 
          id                = data("id").toInt
        , recipe_id         = data("recipe_id").toInt 
        , source            = data("source") 
        , created_by        = data("created_by")
        , created_date      = created_date
        , last_updated_by   = data("last_updated_by") 
        , last_updated_date = last_updated_date
      )
    }
    case "recipe_course" => {
      val created_date      : Timestamp = Timestamp.valueOf(data("created_date").toString())
      val last_updated_date : Timestamp = Timestamp.valueOf(data("last_updated_date").toString())
      RecipeCourseRow( 
          id                = data("id").toInt
        , recipe_id         = data("recipe_id").toInt 
        , course            = data("course") 
        , created_by        = data("created_by")
        , created_date      = created_date
        , last_updated_by   = data("last_updated_by") 
        , last_updated_date = last_updated_date
      )
    }
    case "recipe_description" => {
      val created_date      : Timestamp = Timestamp.valueOf(data("created_date").toString())
      val last_updated_date : Timestamp = Timestamp.valueOf(data("last_updated_date").toString())
      RecipeDescriptionRow( 
          id                = data("id").toInt
        , recipe_id         = data("recipe_id").toInt 
        , description       = data("description") 
        , created_by        = data("created_by")
        , created_date      = created_date
        , last_updated_by   = data("last_updated_by") 
        , last_updated_date = last_updated_date
      )
    }
    case "recipe_difficulty" => {
      val created_date      : Timestamp = Timestamp.valueOf(data("created_date").toString())
      val last_updated_date : Timestamp = Timestamp.valueOf(data("last_updated_date").toString())
      RecipeDifficultyRow( 
          id                = data("id").toInt
        , recipe_id         = data("recipe_id").toInt 
        , difficulty        = data("difficulty").toInt
        , created_by        = data("created_by")
        , created_date      = created_date
        , last_updated_by   = data("last_updated_by") 
        , last_updated_date = last_updated_date
      )
    }
    case "recipe_duration" => {
      val created_date      : Timestamp = Timestamp.valueOf(data("created_date").toString())
      val last_updated_date : Timestamp = Timestamp.valueOf(data("last_updated_date").toString())
      val duration          : Time      = Time.valueOf(data("duration").toString()) 
      RecipeDurationRow( 
          id                = data("id").toInt
        , recipe_id         = data("recipe_id").toInt 
        , Type              = data("type") 
        , duration          = duration
        , created_by        = data("created_by")
        , created_date      = created_date
        , last_updated_by   = data("last_updated_by") 
        , last_updated_date = last_updated_date
      )
    }
    case "recipe_rating" => {
      val created_date      : Timestamp = Timestamp.valueOf(data("created_date").toString())
      val last_updated_date : Timestamp = Timestamp.valueOf(data("last_updated_date").toString())
      RecipeRatingRow( 
          id                = data("id").toInt
        , recipe_id         = data("recipe_id").toInt 
        , rating            = data("rating").toInt
        , created_by        = data("created_by")
        , created_date      = created_date
        , last_updated_by   = data("last_updated_by") 
        , last_updated_date = last_updated_date
      )
    }
    case "recipe_stage" => {
      val created_date      : Timestamp = Timestamp.valueOf(data("created_date").toString())
      val last_updated_date : Timestamp = Timestamp.valueOf(data("last_updated_date").toString())
      RecipeStageRow( 
          id                = data("id").toInt
        , recipe_id         = data("recipe_id").toInt 
        , step_id           = data("step_id").toInt 
        , step_name         = data("step_name") 
        , description       = data("description") 
        , created_by        = data("created_by")
        , created_date      = created_date
        , last_updated_by   = data("last_updated_by") 
        , last_updated_date = last_updated_date
      )
    }
    case "recipe_style" => {
      val created_date      : Timestamp = Timestamp.valueOf(data("created_date").toString())
      val last_updated_date : Timestamp = Timestamp.valueOf(data("last_updated_date").toString())
      RecipeStyleRow( 
          id                = data("id").toInt
        , recipe_id         = data("recipe_id").toInt 
        , style             = data("style")
        , created_by        = data("created_by")
        , created_date      = created_date
        , last_updated_by   = data("last_updated_by") 
        , last_updated_date = last_updated_date
      )
    }
    case "recipe_tag" => {
      val created_date      : Timestamp = Timestamp.valueOf(data("created_date").toString())
      val last_updated_date : Timestamp = Timestamp.valueOf(data("last_updated_date").toString())
      RecipeTagRow( 
          id                = data("id").toInt
        , recipe_id         = data("recipe_id").toInt 
        , tag               = data("tag")
        , created_by        = data("created_by")
        , created_date      = created_date
        , last_updated_by   = data("last_updated_by") 
        , last_updated_date = last_updated_date
      )
    }
    case "recipe_type" => {
      val created_date      : Timestamp = Timestamp.valueOf(data("created_date").toString())
      val last_updated_date : Timestamp = Timestamp.valueOf(data("last_updated_date").toString())
      RecipeTypeRow( 
          id                = data("id").toInt
        , recipe_id         = data("recipe_id").toInt 
        , recipeType        = data("type")
        , created_by        = data("created_by")
        , created_date      = created_date
        , last_updated_by   = data("last_updated_by") 
        , last_updated_date = last_updated_date
      )
    }
    case e => throw new IllegalStateException(s"Not known object table: $e")
  }
}