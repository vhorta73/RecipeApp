package com.app.recipe.String

import com.app.recipe.Import.Product.Units.Model.StandardUnits
import com.app.recipe.Import.Product.Units.Model.StandardUnits
import com.app.recipe.Log.RecipeLogging

/**
 * Extending the String functionality to ease the normal processes inherent 
 * from the Recipe Application.
 */
object Utils extends RecipeLogging {
  
  /**
   * Ensuring this gets picked up by String.
   */
  implicit class StringUtils(val s : String) {
    
    /**
     * This is meant to be used with strings like 100G, returning the 100 value 
     * part only as a Double.
     */
    def toQuantity() : Double = {
      val unitValueRegex = """([0-9.]+)[^a-z-A-Z]*""".r
      val value = unitValueRegex.findFirstMatchIn(s)
      value.get.toString().toDouble
    }
    
    /**
     * For strings like 400g, this will return the g as StandardUnits.Units.
     */
    def toStandardUnits() : StandardUnits.Units = {
      // Look for the key letters in the string
      val unitNameRegex = """[a-z-A-Z]+""".r
      val finalValues = unitNameRegex.findFirstMatchIn(s)

      if ( finalValues.isEmpty ) 
        throw new IllegalStateException(s"Not able to parse StandardUnits from [$s]")

      StandardUnits.getUnit(finalValues.get.toString())
    }
    
  }
}