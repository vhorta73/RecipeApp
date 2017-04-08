package com.app.recipe.Import.Vendor.TESCO.ProductMatch

import java.util.Currency
import java.util.Locale

import com.app.recipe.Log.RecipeLogging
import com.app.recipe.Import.Product.Units.Model.StandardUnits
import com.app.recipe.String.Utils._

/**
 * Class to extract from supplied string, the product quantity.
 * 
 * No specific field or area has been allocated with the product quantity, and 
 * the only place where this can be clearly found, is in the product title. To
 * work-out the product amount, this class primarily is focused on parsing the 
 * product titles, followed by other possible edge cases that may require some
 * different logic.
 */
class MatchQuantity(productString : String) extends RecipeLogging {

  /**
   * Returns the quantity and units found in given string.
   */
  def getMatch() : ( Double, StandardUnits.Units ) = {
    // regex for retrieving the ingredient quantity from the title
    // <div class="desc"><h1><span data-title="true">Nestle Cerelac Wheat With Milk Baby Food 1Kg</span>
    val titleRegex = """(?<=<div class="desc"><h1><span data-title="true">)[^<]*""".r

    // Interested only in finding the first match.
    val nameMatch = titleRegex.findFirstMatchIn(productString)

    // Setting the default values
    var quantity : Double = 0.0
    var units    : StandardUnits.Units = StandardUnits.UNIT
    
    // On most cases, the quantity is at the end.
    val endQuantity = "[0-9]+[a-zA-Z]+$".r
    val qty = endQuantity.findFirstMatchIn(nameMatch.get.toString())

    if ( ! qty.isEmpty ) {
      // We should now have a simple number + unit to split and assign
      quantity = qty.get.toString().toQuantity
      units = qty.get.toString().toStandardUnits
    }
    else {
      warn(s"Got NO quantity match for string: [$productString]")
    }

    ( quantity, units )
  }
  
  def test() : (Double, StandardUnits.Units) = ( 0.0, StandardUnits.UNIT )
}
