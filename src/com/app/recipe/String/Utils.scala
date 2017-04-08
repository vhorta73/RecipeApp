package com.app.recipe.String

/**
 * Extending the String functionality to ease the normal processes inherent 
 * from the Recipe Application.
 */
object Utils {
  
  /**
   * Ensuring this gets picked up by String.
   */
  implicit class StringUtils(val s : String) {
    
  }
}