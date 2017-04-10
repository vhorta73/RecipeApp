package com.app.recipe.Import.Vendor.TESCO.ProductMatch

import com.app.recipe.Log.RecipeLogging

/**
 * Class to find the url for the product small image.
 */
class MatchSmallImage() extends RecipeLogging {

  /**
   * The small image regex.
   * regex for small source image
   * https://img.tesco.com/Groceries/pi/209/5060162210209/IDShot_225x225.jpg
   */
  private final val IMAGE_SMALL_REGEX = """(?<=href="|src=")[^"]*""".r

  /**
   * The string that defines a small image url.
   */
  private final val SMALL_IMAGE_CONTAINS = "IDShot_225x225"

  /**
   * Returns the url for the small image or empty string if nothing.
   */
  def getMatch(productString : String) : String = {

    // Match them all into an iterator
    val imageSmallUrl = IMAGE_SMALL_REGEX.findAllMatchIn(productString)

    var smallImageUrl : String = ""

    if ( ! imageSmallUrl.isEmpty ) {

      var possibleChoices = imageSmallUrl.filter { link => link.toString().contains(SMALL_IMAGE_CONTAINS) }
      
      if ( ! possibleChoices.isEmpty ) smallImageUrl = possibleChoices.next().toString()
      else info(s"Small image url not found.")
    }
    else {
      info(s"Small image url not found.")
    }
    smallImageUrl
  }
}
