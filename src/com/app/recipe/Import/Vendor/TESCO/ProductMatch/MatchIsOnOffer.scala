package com.app.recipe.Import.Vendor.TESCO.ProductMatch

import com.app.recipe.Log.RecipeLogging
import java.util.Currency
import com.app.recipe.Import.Product.Units.Model.StandardUnits._
import java.util.Locale
import com.app.recipe.Import.Product.Units.Model.StandardUnits

/**
 * Class to find if the product is on offer by looking at the offer icon url image.
 */
class MatchIsOnOffer(productString : String) extends RecipeLogging {

  /**
   * Checking if the product is on offer.
   */
  private final val ON_OFFER_REGEX = """This product is on offer""".r.unanchored

  /**
   * Returns true if the product is on offer and false otherwise.
   */
  def getMatch() : Boolean = {
    var isOnOffer : Boolean = false
    if ( ON_OFFER_REGEX.findFirstMatchIn(productString).isDefined ) 
      isOnOffer = true
    
    isOnOffer
  }
}
