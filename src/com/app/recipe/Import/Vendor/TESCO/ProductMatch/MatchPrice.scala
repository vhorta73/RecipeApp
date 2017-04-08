package com.app.recipe.Import.Vendor.TESCO.ProductMatch

import java.util.Currency
import java.util.Locale

import com.app.recipe.Log.RecipeLogging

/**
 * Class to find the url for the product small image.
 */
class MatchPrice(productString : String) extends RecipeLogging {

  /**
   * Returns the url for the small image or empty string if nothing.
   */
  def getMatch() : (Double, Currency ) = {
    // regex for retrieving the ingredient price
    // <span class="linePrice">£6.99</span>
    val priceRegex = """(?<=<span class="linePrice">)[^<]*""".r
    val unitValueRegex = """([0-9.]+)[^a-z-A-Z]*""".r
    val unitNameRegex = """[a-z-A-Z]+""".r

    val price = priceRegex.findFirstMatchIn(productString)
    var priceCcy   : Currency = Currency.getInstance(Locale.UK)
    var priceValue : Double = 0.0
    
    if ( price.isEmpty ) {
      error(s"No price found in:[$productString]")
    }
    else {
      // Prices only contain numbers, '.' and ','. Anything else, is removed.
      priceValue = price.get.toString().replaceAll("[^0-9,.]", "").toDouble
    }

    ( priceValue, priceCcy )
  }
}
