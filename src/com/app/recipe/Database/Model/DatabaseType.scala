package com.app.recipe.Database.Model

/**
 * Database types available for the Factory to instantiate, allowing the caller
 * to call for the recipe application available methods that each will implement.
 */
object DatabaseType extends Enumeration {
  type Databases = Value
  
  val SQL = Value
}