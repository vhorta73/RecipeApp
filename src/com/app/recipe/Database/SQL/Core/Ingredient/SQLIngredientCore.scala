package com.app.recipe.Database.SQL.Core.Ingredient

import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientName
import com.app.recipe.Log.RecipeLogging
import com.app.recipe.Database.SQL.Core.Recipe.SQLTableAccess
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientAttribute
import com.app.recipe.Database.SQL.Core.Ingredient.Tables.IngredientSource

/**
 * The SQL Recipe Core abstract with all the shared implementations across all 
 * the other SQL objects that compose all possible actions.
 */
abstract trait SQLIngredientCore extends SQLTableAccess {

  /**
   * The ingredient table names.
   */
  protected final def getCoreDatabaseName()                  : String = "recipe_core"
  protected final def getIngredientTableName()               : String = "ingredient"
  protected final def getIngredientAttributeTableName()      : String = "ingredient_attribute"
  protected final def getIngredientAttributeGroupTableName() : String = "ingredient_attribute_group"
  protected final def getIngredientGroupTableName()          : String = "ingredient_group"
  protected final def getIngredientGroupLinkTableName()      : String = "ingredient_group_link"
  protected final def getIngredientNameTableName()           : String = "ingredient_name"
  protected final def getIngredientSourceTableName()         : String = "ingredient_source"
  protected final def getIngredientSourceGroupTableName()    : String = "ingredient_source_group"

  /**
   * The column names to be used to call on the found database values.
   */
  protected final def getIngredientColumns()                 : Array[String] = Array("id","ingredient_id","type","type_id","active","created_by","created_date","last_updated_by","last_updated_date")
  protected final def getIngredientAttributeColumns()        : Array[String] = Array("id","name","active","created_by","created_date","last_updated_by","last_updated_date")
  protected final def getIngredientAttributeGroupdColumns()  : Array[String] = Array("id","attribute_id","group_id","active","created_by","created_date","last_updated_by","last_updated_date")
  protected final def getIngredientGroupColumns()            : Array[String] = Array("id","name","type","active","created_by","created_date","last_updated_by","last_updated_date")
  protected final def getIngredientGroupLinkColumns()        : Array[String] = Array("id","ingredient_id","group_id","active","created_by","created_date","last_updated_by","last_updated_date")
  protected final def getIngredientNameColumns()             : Array[String] = Array("id","name","active","created_by","created_date","last_updated_by","last_updated_date")
  protected final def getIngredientSourceColumns()           : Array[String] = Array("id","name","active","created_by","created_date","last_updated_by","last_updated_date")
  protected final def getIngredientSourceGroupColumns()      : Array[String] = Array("id","source_id","group_id","active","created_by","created_date","last_updated_by","last_updated_date")

  /**
   * The instantiated ingredient classes for DB access on each.
   * 
   * @param tableName : String
   * @return Option[SQLIngredientTableAccess]
   */
  def getIngredientClass( tableName : String ) : Option[SQLIngredientTableAccess] = tableName match {
    case name if name.equals(getIngredientNameTableName())              => Some(new IngredientName())
    case name if name.equals(getIngredientAttributeTableName())         => Some(new IngredientAttribute())
    case name if name.equals(getIngredientSourceTableName())            => Some(new IngredientSource())
//    case name if name.equals(getIngredientGroupTableName())          => Some(new ())
//    case name if name.equals(getIngredientGroupLinkTableName())      => Some(new ())
//    case name if name.equals(getIngredientNameTableName())           => Some(new IngredientName())
//    case name if name.equals(getIngredientSourceTableName())         => Some(new ())
//    case name if name.equals(getIngredientSourceGroupTableName())    => Some(new ())
    case _ => throw new IllegalStateException(s"Not known table '$tableName'")
  }
}