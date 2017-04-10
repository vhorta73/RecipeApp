package com.app.recipe.Import.Vendor.TESCO.ProductMatch

import com.app.recipe.Log.RecipeLogging
import java.util.Currency
import com.app.recipe.Import.Product.Units.Model.StandardUnits._
import java.util.Locale
import com.app.recipe.Import.Product.Units.Model.StandardUnits

/**
 * Class to find the url for the product small image.
 */
class MatchPricePerUnit() extends RecipeLogging {

  /**
   * Retrieving from the product details web site page the price per unit string.
   * E.g.: <span class="linePriceAbbr">(£0.70/100g)</span>
   */
  private final val PRICE_REGEX = """(?<=<span class="linePriceAbbr">\()[^\)<]*""".r.unanchored
  private final val NOT_AVAILABLE = """Sorry, this product is currently not available""".r.unanchored

  /**
   * Matching the price per unit to then be parsed it into standard recipe format.
   */
  // From [£0.63/kg], matching 0.63 and Kg
  private final val POUND_VALUE_BAR_UNIT_REGEX = """([0-9.]+)/([a-z-A-Z]+)""".r.unanchored
  // From [£0.28/100ml], matching 0.28 and 100 and ml
  private final val POUND_VALUE_BAR_VALUE_UNIT_REGEX = """([0-9.]+)/([0-9.]+)([a-z-A-Z]+)""".r.unanchored
  // From [£0.31/each], matching 0.31 and Unit
  private final val POUND_VALUE_BAR_EACH_REGEX = """([0-9.]+)/each""".r.unanchored

  /**
   * Returns the url for the small image or empty string if nothing.
   */
  def getMatch(productString : String) : (Double, Currency, Double, StandardUnits.Units) = {

    // Tesco always display the price in GBP
    var priceCcy   : Currency = Currency.getInstance(Locale.UK)
    var priceValue : Double = 0
    var unitValue  : Double = 1                  // Always the unit unless stated otherwise.
    var unitName   : StandardUnits.Units = Units // Units by default

    var pricePerUnit = PRICE_REGEX.findFirstMatchIn(productString)
    if ( pricePerUnit.isEmpty ) {
      var notAvailable = NOT_AVAILABLE.findFirstMatchIn(productString)
      if ( notAvailable.isDefined ) {
        warn(s"Product is not available")
        return ( priceValue, priceCcy, unitValue, unitName )
      }
    }

    pricePerUnit.get.toString() match {
      case POUND_VALUE_BAR_EACH_REGEX(price) => {
        priceValue = price.toDouble
        unitValue = 1
        unitName   = StandardUnits.getUnit("unit")
      }
      case POUND_VALUE_BAR_UNIT_REGEX(v,u) => {
        priceValue = v.toDouble
        try {
          unitName   = StandardUnits.getUnit(u)
        } catch {
          case _ : Throwable => {
            warn(s"[value/unit] Not able to match [${pricePerUnit.get.toString()}]. Will set: ($priceValue $priceCcy per $unitValue, $unitName)")
          }
        }
      }
      case POUND_VALUE_BAR_VALUE_UNIT_REGEX(price,value,u) => {
        priceValue = price.toDouble
        unitValue = value.toDouble
        try {
          unitName   = StandardUnits.getUnit(u)
        } catch {
          case _ : Throwable => {
            warn(s"[value/value unit] Not able to match [${pricePerUnit.get.toString()}]. Will set: ($priceValue $priceCcy per $unitValue, $unitName)")
          }
        }
      }
      case _ => warn(s"Do not know how to parse the price par unit for: [${pricePerUnit.get.toString()}]")
    }

    ( priceValue, priceCcy, unitValue, unitName )
  }
}
