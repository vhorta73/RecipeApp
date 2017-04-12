package com.app.recipe.Database.Model

object DatabaseGlobalVariables {
  
  /**
   * The value used across the default update and created by.
   */
  private val SYSTEM_USERNAME = "system"

  def getDeaultSystemUsername() : String = SYSTEM_USERNAME

}