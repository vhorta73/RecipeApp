package com.app.recipe.Database.SQL.Core.Model

import java.time.LocalDateTime
import com.app.recipe.Database.Model.DatabaseGlobalVariables

trait CoreProduct

case class Ingredient(
     id : Int
    ,name : String
    ,created_date : LocalDateTime = LocalDateTime.now()
    ,created_by : String = DatabaseGlobalVariables.getDeaultSystemUsername()
    ,last_updated : LocalDateTime = LocalDateTime.now()
    ,updated_by : String = DatabaseGlobalVariables.getDeaultSystemUsername()
) extends CoreProduct

case class Attribute(
    id : Int
    ,name : String
    ,created_date : LocalDateTime = LocalDateTime.now()
    ,created_by : String
    ,last_updated : LocalDateTime = LocalDateTime.now()
    ,updated_by : String
) extends CoreProduct

case class Source(
    id : Int
    ,name : String
    ,created_date : LocalDateTime = LocalDateTime.now()
    ,created_by : String
    ,last_updated : LocalDateTime = LocalDateTime.now()
    ,updated_by : String
) extends CoreProduct

case class IngredientAttribute(
    id : Int
    ,ingredient_id : Int
    ,attribute_id : Int
    ,created_date : LocalDateTime = LocalDateTime.now()
    ,created_by : String
    ,last_updated : LocalDateTime = LocalDateTime.now()
    ,updated_by : String
) extends CoreProduct

case class IngredientSource(
    id : Int
    ,ingredient_id : Int
    ,source_id : Int
    ,created_date : LocalDateTime = LocalDateTime.now()
    ,created_by : String
    ,last_updated : LocalDateTime = LocalDateTime.now()
    ,updated_by : String
) extends CoreProduct
