package com.app.recipe.Import.Vendor.TESCO.ProductMatch

import com.app.recipe.Log.RecipeLogging

/**
 * Class to find match the Halal values from a given line to parse.
 */
class MatchHalal(productString : String) extends RecipeLogging {

  /**
   * Returns true if Halal is found in on the given string and false otherwise.
   */
  def getMatch() : Boolean = {
    // regex for halal string
    val halalRegex = """(Halal)""".r

    var isHalal : Boolean = false
    
    if( halalRegex.findFirstMatchIn(productString).isDefined ) {
      isHalal = true
    }

    isHalal
  }
}