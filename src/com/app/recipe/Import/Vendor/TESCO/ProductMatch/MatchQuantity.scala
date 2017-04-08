package com.app.recipe.Import.Vendor.TESCO.ProductMatch

import com.app.recipe.Import.Product.Units.Model.StandardUnits
import com.app.recipe.Log.RecipeLogging
import com.app.recipe.String.Utils.StringUtils

/**
 * Class to extract from supplied string, the product quantity.
 * 
 * No specific field or area has been allocated with the product quantity, and 
 * the only place where this can be clearly found, is in the product title. To
 * work-out the product amount, this class primarily is focused on parsing the 
 * product titles, followed by other possible edge cases that may require some
 * different logic.
 */
class MatchQuantity() extends RecipeLogging {

  /**
   * The regex required for matching quantity, unit and multiple option operations.
   */
  private final val TITLE_REGEX = """(?<=<div class="desc"><h1><span data-title="true">)[^<]*""".r
  private final val END_QUANTITY_REGEX = "[0-9]+[a-zA-Z]+$".r
  private final val TIMES_END_QUANTITY_REGEX = "[0-9]+[a-zA-Z]+[0-9]+[a-zA-Z]+$".r

  /**
   * Returns the quantity and units found in given string.
   */
  def getMatch(productString : String) : ( Double, StandardUnits.Units ) = {
    // regex for retrieving the ingredient quantity from the title
    // <div class="desc"><h1><span data-title="true">Nestle Cerelac Wheat With Milk Baby Food 1Kg</span>
  
    // The quantity is derived from the title.
    val nameMatch = TITLE_REGEX.findFirstMatchIn(productString)
    
    // Announce that the default values will be used instead.
    // TODO: Use Optional Some
    if ( nameMatch.isEmpty ) warn(s"No match found for quantity from [$productString]")

    // Setting the default values
    var quantity : Double = 0.0
    var units    : StandardUnits.Units = StandardUnits.UNIT
    
    // On most cases, the quantity is at the end like: 180ml, 
    // but in some cases the quantity can be: 4X180ml
    var qty = END_QUANTITY_REGEX.findFirstMatchIn(nameMatch.get.toString())
    if ( ! qty.isEmpty ) {
      // We should now have a simple number + unit to split and assign
      quantity = qty.get.toString().toQuantity
      units = qty.get.toString().toStandardUnits
      
      var complexMatchedQty = TIMES_END_QUANTITY_REGEX.findFirstMatchIn(nameMatch.get.toString())
      // If we have a composed number, decompose it to calculate and update final value.
      if ( ! complexMatchedQty.isEmpty ) {
        
        val multiplierRegex = "[0-9]+[ xX]".r
        var multiplierValue = multiplierRegex.findFirstMatchIn(nameMatch.get.toString())
        
        if ( ! multiplierValue.isEmpty ) {
          // Ensure we only get numbers to multiply
          var multiplier = multiplierValue.get.toString().replaceAll("[^0-9]", "")
          if ( ! multiplier.isEmpty() ) {
            val multiply = multiplier.toDouble
            info(s"Quantity [${complexMatchedQty.get.toString()}] was calculated as: $quantity * $multiply = ${quantity * multiply} $units")
            quantity = quantity * multiply
          }
          else {
            warn(s"Found what looks list a complex quantity but could not find the multiplier: $productString]")
          }
        }
      }
    }
    else {
      warn(s"Got NO quantity match for string: [$productString]")
    }

    ( quantity, units )
  }
}