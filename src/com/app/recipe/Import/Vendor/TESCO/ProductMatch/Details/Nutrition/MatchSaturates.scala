package com.app.recipe.Import.Vendor.TESCO.ProductMatch.Details.Nutrition

import com.app.recipe.Import.Product.Model.ProductNutrition
import com.app.recipe.Import.Product.Model.Saturates
import com.app.recipe.Import.Product.Units.Model.StandardUnits
import com.app.recipe.Import.Product.Units.Model.StandardUnits.Units
import com.app.recipe.Log.RecipeLogging
import com.app.recipe.String.Utils._
/**
 * Class to find match the saturation values from a given line to parse.
 * The line may contain more than one saturation levels, which in case will
 * return more than one element.
 */
class MatchSaturates() extends RecipeLogging {

  /**
   * Returns the Fat case class with the detailed values as displaying on the web page.
   */
  def getMatch(productString : String) : List[ProductNutrition] = {

    // regex for finding the saturation values per 100g
    val valuesRegex = """(?<=td>)([0-9<>.]+)[^</td|^g<]*""".r

    // Interested only in finding the first match.
    val valuesMatch = valuesRegex.findFirstMatchIn(productString)

    // If empty, this is sign of a bad matching and needs to be revised.
    if ( valuesMatch.isEmpty )
      throw new IllegalStateException("No Saturation values matched line: ["+productString+"]")

    // Find out which units to use.
    var energyUnits : Units = getUnit(productString)

    // If units did not match, a new rule is required
    if ( energyUnits == null )
      throw new IllegalStateException("No Saturation units matched line: ["+productString+"]")

    // Return first match as a list.
    return List(Saturates(valuesMatch.get.toString().toQuantity,energyUnits))
  }

  /**
   * Matching criteria for fat on a given line. It must match only line that
   * are certain of containing the saturation values. By default returns false.
   */
  def isSaturates(productString : String) : Boolean = {
    productString match {
      case x if x.contains("Saturates") => true
      case x if x.contains("saturates") => true
      case _ => false
    }
  }
  
  /**
   * Look for possible fat units. Usually is represented in grams.
   */
  private final def getUnit(productString : String) : Units = {
    productString match {
      case x if x.contains("g<") => StandardUnits.g
      case x if x.contains("(g)") => StandardUnits.g
      case _ => throw new IllegalArgumentException("No Saturation unit found in line: ["+productString+"]")
    }
  }
}