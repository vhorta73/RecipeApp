package com.app.recipe.Import.Vendor.TESCO.ProductMatch

import com.app.recipe.Log.RecipeLogging

/**
 * Class to find match the Halal values from a given line to parse.
 */
class MatchHalal() extends RecipeLogging {

  /**
   * Returns true if Halal is found in on the given string and false otherwise.
   */
  def getMatch(productString : String) : Boolean = {
    // regex for halal string
    val halalRegex = """(Halal)""".r.unanchored

    var isHalal : Boolean = false
    
    if( halalRegex.findFirstMatchIn(productString).isDefined ) {
      isHalal = true
    }

    isHalal
  }
}