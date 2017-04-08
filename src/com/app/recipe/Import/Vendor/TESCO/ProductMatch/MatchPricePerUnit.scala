package com.app.recipe.Import.Vendor.TESCO.ProductMatch

import com.app.recipe.Log.RecipeLogging
import java.util.Currency
import com.app.recipe.Import.Product.Units.Model.StandardUnits._
import java.util.Locale
import com.app.recipe.Import.Product.Units.Model.StandardUnits

/**
 * Class to find the url for the product small image.
 */
class MatchPricePerUnit(productString : String) extends RecipeLogging {

  /**
   * Returns the url for the small image or empty string if nothing.
   */
  def getMatch() : (Double, Currency, Double, StandardUnits.Units) = {
    // regex for retrieving the ingredient price
    // <span class="linePriceAbbr">(£0.70/100g)</span>
    val priceRegex = """(?<=<span class="linePriceAbbr">\()[^\)<]*""".r
    val unitValueRegex = """([0-9.]+)[^a-z-A-Z]*""".r
    val unitNameRegex = """[a-z-A-Z]+""".r

    val pricePerUnit = priceRegex.findFirstMatchIn(productString)
    val priceList = pricePerUnit.get.toString().split("/")
    
    // This is the price, thus removing anything that is not a number
    val price   = priceList(0).replaceAll("[^0-9,.]", "") 
    var priceValue : Double = price.toDouble
    var priceCcy   : Currency = Currency.getInstance(Locale.UK)
    var unitValue  : Double = 0.0
    var unitName   : StandardUnits.Units = UNIT
    
    // This is the per unit or per value units.
    val perUnit = priceList(1) 
    val priceFound = unitValueRegex.findFirstMatchIn(perUnit)
    if ( priceFound.isEmpty ) {
      warn(s"No Price found for product: [$productString]")
    }
    else {
      unitValue = priceFound.get.toString.toDouble
      unitName  = StandardUnits.getUnit(unitNameRegex.findFirstMatchIn(perUnit).get.toString())
    }

    ( priceValue, priceCcy, unitValue, unitName )
  }
}
