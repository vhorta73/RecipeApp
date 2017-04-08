package com.app.recipe.Database.Model

/**
 * The Database model, defines the available methods and queries that 
 * can be used. These may share internally some logic, but the model,
 * separates functionality by the methods that are made available.
 */
object DatabaseMode extends Enumeration {
  type Mode = Value
  
  val TESCO_IMPORT = Value
  
}