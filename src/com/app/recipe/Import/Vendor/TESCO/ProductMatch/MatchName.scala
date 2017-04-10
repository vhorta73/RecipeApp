package com.app.recipe.Import.Vendor.TESCO.ProductMatch

import com.app.recipe.Log.RecipeLogging

/**
 * Class to find the name for the product from the supplied product string.
 */
class MatchName() extends RecipeLogging {
  /**
   * Returns the name of the product as displaying on the web page.
   */
  def getMatch(productString : String) : String = {
    if ( productString.length() == 0 ) {
      info("No string supplied to parse a name from")
      return ""
    }
    
    // regex for extra product title. Example to match:
    // <div class="desc"><h1><span data-title="true">Nestle Cerelac Wheat With Milk Baby Food 1Kg</span>
    val nameRegex = """(?<=<div class="desc"><h1><span data-title="true">)[^<]*""".r

    // Interested only in finding the first match.
    val nameMatch = nameRegex.findFirstMatchIn(productString)

    // If empty, this is sign of a bad matching and needs to be revised.
    if ( nameMatch.isEmpty && productString.length() > 0 )
      throw new IllegalStateException("No product name matched with line: ["+productString+"]")

    // Collect the matched name and return it as string.
    nameMatch.get.toString()
  }

}