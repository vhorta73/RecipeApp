package com.app.recipe.Import.Vendor.TESCO.ProductMatch

import com.app.recipe.Log.RecipeLogging
import java.util.Currency
import com.app.recipe.Import.Product.Units.Model.StandardUnits
import java.util.Locale

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

    // This is the per unit or per value units.
    val perUnit = priceList(1) 
    var unitValue  : Double = unitValueRegex.findFirstMatchIn(perUnit).get.toString.toDouble
    var unitName   : StandardUnits.Units = StandardUnits.getUnit(unitNameRegex.findFirstMatchIn(perUnit).get.toString())

    ( priceValue, priceCcy, unitValue, unitName )
  }
}
