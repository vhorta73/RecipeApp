package com.app.recipe.Import.Vendor.TESCO.ProductMatch

import com.app.recipe.Log.RecipeLogging

/**
 * Class to find the url for the product small image.
 */
class MatchSmallImage(productString : String) extends RecipeLogging {

  /**
   * Returns the url for the small image or empty string if nothing.
   */
  def getMatch() : String = {
    // regex for small source image
    // title="Click to view extra large image"><img src="https://img.tesco.com/Groceries/pi/095/8410100099095/IDShot_225x225.jpg" alt="
    val imgSmallRegex = """(?<=title="Click to view extra large image"><img src=")[^"]*""".r
    val imageSmalUrl = imgSmallRegex.findFirstMatchIn(productString).getOrElse("").toString()
    
    if ( imageSmalUrl.isEmpty() ) {
      warn(s"No small large image found")
    }
    
    imageSmalUrl
  }
}
