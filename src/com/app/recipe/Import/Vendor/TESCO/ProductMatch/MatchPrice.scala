package com.app.recipe.Import.Vendor.TESCO.ProductMatch

import java.util.Currency
import java.util.Locale

import com.app.recipe.Log.RecipeLogging

/**
 * Class to find the url for the product small image.
 */
class MatchPrice() extends RecipeLogging {

  /**
   * Find the price.
   * E.g.: <span class="linePrice">Â£6.99</span>
   */
  private final val PRICE_REGEX = """(?<=<span class="linePrice">)[^<]*""".r.unanchored

  /**
   * Stripping out the price from found string.
   * Match: [£0.90] returns 0.90
   */
  private final val MATCH_VALUE_REGEX = "([0-9.]+)$".r.unanchored

  /**
   * Returns the url for the small image or empty string if nothing.
   */
  def getMatch(productString : String) : (Double, Currency ) = {

    val priceMatch = PRICE_REGEX.findFirstMatchIn(productString)
    var priceCcy   : Currency = Currency.getInstance(Locale.UK)
    var priceValue : Double = 0.0

    if ( ! priceMatch.isEmpty ) {
      priceMatch.get.toString() match {
        case MATCH_VALUE_REGEX(price) => {
          priceValue = price.toDouble
        }
        case _ => {
          warn(s"No price found for given string: [${priceMatch.get.toString()}]")
        }
      }
    }
    else {
      warn(s"No price found.")
    }

    ( priceValue, priceCcy )
  }
}
