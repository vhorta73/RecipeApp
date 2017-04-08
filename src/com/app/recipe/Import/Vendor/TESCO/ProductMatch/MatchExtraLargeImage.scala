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
    // regex for large source image
    // https://img.tesco.com/Groceries/pi/209/5060162210209/IDShot_540x540.jpg
    
    // Regex on all links
    val imgLargeRegex = """(?<=href="|src=")[^"]*""".r

    // Match them all into an iterator
    val imageLargeUrl = imgLargeRegex.findAllMatchIn(productString)

    var largeImageUrl : String = ""
    if ( ! imageLargeUrl.isEmpty ) {
      // Find the list of a small image with contains string: IDShot_225x225
      var possibleChoices = imageLargeUrl.filter { link => link.toString().contains("IDShot_540x540") }
      if ( ! possibleChoices.isEmpty ) largeImageUrl = possibleChoices.next().toString()
      else info(s"Large image url not found for [$productString]")
    }
    else {
      info(s"Large image url not found for [$productString]")
    }
    largeImageUrl
  }
}