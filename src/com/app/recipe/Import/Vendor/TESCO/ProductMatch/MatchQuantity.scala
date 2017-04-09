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
  // Matching: [...###UUU]
  private final val END_QUANTITY_REGEX = "([0-9]+)([a-zA-Z]+)$".r.unanchored
  // Matching: [...###UUU..]
  private final val UNSPACED_QUANTITY_REGEX = "([0-9]+)([a-zA-Z]+)".r.unanchored
  // Matching: [...### UUU..]
  private final val SPACED_QUANTITY_REGEX = "([0-9]+) ([a-zA-Z]+)".r.unanchored
  // Matching: [...##X###UUU..]
  private final val UNSPACED_QUANTITY_TIMES_REGEX = "([0-9]+)[X|x]([0-9]+)([a-zA-Z]+)".r.unanchored
  // Matching: [...## X ### UUU..]
  private final val SPACED_QUANTITY_TIMES_REGEX = "([0-9]+) [X|x] ([0-9]+) ([a-zA-Z]+)".r.unanchored
  // Matching: [...##X### UUU..]
  private final val LAST_SPACED_QUANTITY_TIMES_REGEX = "([0-9]+)[X|x]([0-9]+) ([a-zA-Z]+)".r.unanchored

  /**
   * Returns the quantity and units found in given string.
   */
  def getMatch(productString : String) : ( Double, StandardUnits.Units ) = {
    // regex for retrieving the ingredient quantity from the title
    // <div class="desc"><h1><span data-title="true">Nestle Cerelac Wheat With Milk Baby Food 1Kg</span>
  
    // The quantity is derived from the title.
    val nameMatch = TITLE_REGEX.findFirstMatchIn(productString)
    
    // Announce that the default values will be used instead.
    if ( nameMatch.isEmpty ) warn(s"No match found for quantity from [$productString]")

    // Setting the default values
    var quantity : Double = 1
    var units    : StandardUnits.Units = StandardUnits.Units

    // Take all the complexity down to matching regex patterns.
    nameMatch.get.toString() match {
      case LAST_SPACED_QUANTITY_TIMES_REGEX(factor, qty, unit)      => { 
        quantity = qty.toDouble; 
        var times : Double = factor.toDouble
          try {
            units = StandardUnits.getUnit(unit)
            quantity = times * quantity
          } catch { case _ : Throwable => {
            warn(s"Last spaced quantity times, ($qty, $factor, $unit) could not find units for: [${nameMatch.get.toString()}] Defaulting to Units"); 
            units = StandardUnits.Units 
          } }
      }
      case SPACED_QUANTITY_TIMES_REGEX(factor, qty, unit)      => { 
        quantity = qty.toDouble; 
        var times : Double = factor.toDouble
          try {
            units = StandardUnits.getUnit(unit)
            quantity = times * quantity
          } catch { case _ : Throwable => {
            warn(s"Spaced quantity times, ($qty, $factor, $unit) could not find units for: [${nameMatch.get.toString()}] Defaulting to Units"); 
            units = StandardUnits.Units 
          } }
      }
      case UNSPACED_QUANTITY_TIMES_REGEX(factor, qty, unit)      => { 
        quantity = qty.toDouble; 
        var times : Double = factor.toDouble
          try {
            units = StandardUnits.getUnit(unit)
            quantity = times * quantity
          } catch { case _ : Throwable => {
            warn(s"Unspaced quantity times, ($qty, $factor, $unit) could not find units for: [${nameMatch.get.toString()}] Defaulting to Units"); 
            units = StandardUnits.Units 
          } }
      }
      case END_QUANTITY_REGEX(qty, unit)      => { 
        quantity = qty.toDouble; 
          try {
            units = StandardUnits.getUnit(unit)
          } catch { case _ : Throwable => {
            warn(s"End quantity, ($qty, $unit) could not find units for: [${nameMatch.get.toString()}] Defaulting to Units"); 
            units = StandardUnits.Units 
          } }
      }
      case UNSPACED_QUANTITY_REGEX(qty, unit) => { 
        quantity = qty.toDouble; 
          try {
            units = StandardUnits.getUnit(unit)
          } catch { case _ : Throwable => {
            warn(s"Unspaced quantity, ($qty, $unit) could not find units for: [${nameMatch.get.toString()}] Defaulting to Units"); 
            units = StandardUnits.Units 
          } }
      }
      case SPACED_QUANTITY_REGEX(qty, unit)   => { 
        quantity = qty.toDouble; 
          try {
            units = StandardUnits.getUnit(unit)
          } catch { case _ : Throwable => {
            warn(s"Spaced quantity, ($qty, $unit) could not find units for: [${nameMatch.get.toString()}] Defaulting to Units"); 
            units = StandardUnits.Units 
          } }
        }
      case _ => warn(s"Do not know how to parse a quantity from [${nameMatch.get.toString()}]")
    }

    ( quantity, units )
  }
}