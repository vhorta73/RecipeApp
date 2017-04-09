package com.app.recipe.Import.Vendor.TESCO.ProductMatch

import com.app.recipe.Log.RecipeLogging
import java.util.Currency
import com.app.recipe.Import.Product.Units.Model.StandardUnits._
import java.util.Locale
import com.app.recipe.Import.Product.Units.Model.StandardUnits

/**
 * Class to find the url for the product small image.
 */
class MatchIsAvailable(productString : String) extends RecipeLogging {

  /**
   * Checking if the product is not available.
   */
  private final val NOT_AVAILABLE = """Sorry, this product is currently not available""".r.unanchored

  /**
   * Returns true if the product is available and false otherwise.
   */
  def getMatch() : Boolean = {

    var isAvailable : Boolean = true
    
    if ( NOT_AVAILABLE.findFirstMatchIn(productString).isDefined ) 
      isAvailable = false
    
    isAvailable
  }
}
