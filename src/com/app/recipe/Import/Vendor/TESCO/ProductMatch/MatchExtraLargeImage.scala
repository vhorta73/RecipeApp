package com.app.recipe.Import.Vendor.TESCO.ProductMatch

import com.app.recipe.Log.RecipeLogging

/**
 * Class to find the url for the product extra large image.
 */
class MatchExtraLargeImage(productString : String) extends RecipeLogging {

  /**
   * Returns the url for the extra large image or empty string if nothing.
   */
  def getMatch() : String = {
    // regex for extra large source image
    // productImageExtraLargeButtonList"><li><a href="https://img.tesco.com/Groceries/pi/095/8410100099095/IDShot_540x540.jpg">Zoom</a></li>
    val imgExtraLargeRegex = """(?<=productImageExtraLargeButtonList"><li><a href=")[^"]*""".r
    var largeImage = imgExtraLargeRegex.findFirstMatchIn(productString).getOrElse("").toString()
    if ( largeImage.isEmpty() ) {
      val imgLargeRegex = """(?<=productImage"><a href=")[^"]*""".r
      largeImage = imgLargeRegex.findFirstMatchIn(productString).getOrElse("").toString()
    }
    largeImage
  }
}