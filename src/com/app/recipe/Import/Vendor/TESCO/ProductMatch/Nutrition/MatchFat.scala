package com.app.recipe.Import.Vendor.TESCO.ProductMatch.Nutrition

import com.app.recipe.Import.Product.Nutrition.Model.Fat
import com.app.recipe.Import.Product.Nutrition.Model.NutritionInformation
import com.app.recipe.Import.Product.Units.Model.StandardUnits._
import com.app.recipe.Import.Product.Units.Model.StandardUnits
import com.app.recipe.Import.Product.Units.Model.StandardUnits.Units

/**
 * Class to find match the fat values from a given line to parse.
 * The line may contain more than one fat levels, which in case will
 * return more than one element.
 */
class MatchFat(productString : String) {
  /**
   * Returns the Fat case class with the detailed values as displaying on the web page.
   */
  def getMatch() : List[NutritionInformation] = {
    // regex for finding the fat values per 100g
    val valuesRegex = """(?<=td>)(?:\d+)[^</td|^g<]*""".r

    // Interested only in finding the first match.
    val valuesMatch = valuesRegex.findFirstMatchIn(productString)

    // If empty, this is sign of a bad matching and needs to be revised.
    if ( valuesMatch.isEmpty )
      throw new IllegalStateException("No Fat values matched line: ["+productString+"]")

    // Find out which units to use.
    var energyUnits : Units = getUnit()

    // If units did not match, a new rule is required
    if ( energyUnits == null )
      throw new IllegalStateException("No Fat units matched line: ["+productString+"]")

    // Return first match as a list.
    return List(Fat(valuesMatch.get.toString().toDouble,energyUnits))
  }

  /**
   * Matching criteria for fat on a given line. It must match only line that
   * are certain of containing the fat values. By default returns false.
   */
  def isFat() : Boolean = {
    productString match {
      case x if x.contains("Fat") => true
      case _ => false
    }
  }
  
  /**
   * Look for possible fat units. Usually is represented in grams.
   */
  private final def getUnit() : Units = {
    productString match {
      case x if x.contains("(g)") => StandardUnits.g
      case x if x.contains("g<") => StandardUnits.g
      case _ => throw new IllegalArgumentException("No Fat unit found in line: ["+productString+"]")
    }
  }
}