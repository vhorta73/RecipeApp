package com.app.recipe.Import.Vendor.TESCO.ProductMatch

import com.app.recipe.Log.RecipeLogging

/**
 * Class to find match the suitable for vegetarians values from a given line to parse.
 */
class MatchSuitableForVegetarians() extends RecipeLogging {

  /**
   * Returns true if suitable for vegetarians is found in on the given string and false otherwise.
   */
  def getMatch(productString : String) : Boolean = {
    // regex for suitable for vegetarians string
    // "/groceries/UIAssets/I/Sites/Retail/Superstore/Online/Product/Logos/Vegetarian.jpg" alt="Suitable for vegetarians" />
    val suitableForVegetarianRegex = """("/groceries/UIAssets/I/Sites/Retail/Superstore/Online/Product/Logos/Vegetarian.jpg")""".r

    var isSuitableForVegetarians : Boolean = false
    
    if( suitableForVegetarianRegex.findFirstMatchIn(productString).isDefined ) {
      isSuitableForVegetarians = true
    }
    isSuitableForVegetarians
  }
}
