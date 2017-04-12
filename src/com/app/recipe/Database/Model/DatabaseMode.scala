package com.app.recipe.Database.Model

/**
 * The Database model, defines the available methods and queries that 
 * can be used. These may share internally some logic, but the model,
 * separates functionality by the methods that are made available.
 */
object DatabaseMode extends Enumeration {
  type Mode = Value

  // The Vendor database access
  val TESCO_IMPORT = Value

  // Admin database access
  val ADMIN = Value

  // Core database access
  val CORE = Value

}