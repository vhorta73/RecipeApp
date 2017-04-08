package com.app.recipe.Database.SQL

import java.sql.Connection

import com.app.recipe.Database.RecipeDatabase
import com.app.recipe.Database.RecipeDatabase

/**
 * The SQL database object for SQL database access.
 */
object SQLDatabase extends RecipeDatabase { 

  /**
   * The database access handle.
   */
  private final val dbHandle = SQLDatabaseAccess.getHandle(this)

  /**
   * The SQL Database connection.
   */
  def getSQLHandle() : Connection = dbHandle
  
}