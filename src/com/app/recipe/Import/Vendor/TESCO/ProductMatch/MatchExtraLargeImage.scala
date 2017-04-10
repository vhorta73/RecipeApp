package com.app.recipe.Import.Vendor.TESCO.ProductMatch

import com.app.recipe.Log.RecipeLogging

/**
 * Class to find the url for the product extra large image.
 */
class MatchExtraLargeImage() extends RecipeLogging {

  /**
   * Regex on all links.
   * regex for large source image
   * https://img.tesco.com/Groceries/pi/209/5060162210209/IDShot_540x540.jpg
   */
  private final val IMAGE_LARGE_REGEX = """(?<=href="|src=")[^"]*""".r
  
  /**
   * The string that defines the large image url.
   */
  private final val LARGE_IMAGE_CONTAINS = "IDShot_540x540"

  /**
   * Returns the url for the extra large image or empty string if nothing.
   */
  def getMatch(productString : String) : String = {

    // Match them all into an iterator
    val imageLargeUrl = IMAGE_LARGE_REGEX.findAllMatchIn(productString)

    var largeImageUrl : String = ""
    if ( ! imageLargeUrl.isEmpty ) {

      var possibleChoices = imageLargeUrl.filter { link => link.toString().contains(LARGE_IMAGE_CONTAINS) }

      if ( ! possibleChoices.isEmpty ) largeImageUrl = possibleChoices.next().toString()
      else info(s"Large image url not found.")
    }
    else {
      info(s"Large image url not found.")
    }
    largeImageUrl
  }
}