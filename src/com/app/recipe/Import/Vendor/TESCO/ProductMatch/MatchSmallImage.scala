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
    // https://img.tesco.com/Groceries/pi/209/5060162210209/IDShot_225x225.jpg
    
    // Regex on all links
    val imgSmallRegex = """(?<=href="|src=")[^"]*""".r

    // Match them all into an iterator
    val imageSmallUrl = imgSmallRegex.findAllMatchIn(productString)

    var smallImageUrl : String = ""
    if ( ! imageSmallUrl.isEmpty ) {
      // Find the list of a small image with contains string: IDShot_225x225
      var possibleChoices = imageSmallUrl.filter { link => link.toString().contains("IDShot_225x225") }
      if ( ! possibleChoices.isEmpty ) smallImageUrl = possibleChoices.next().toString()
      else info(s"Small image url not found for [$productString]")
    }
    else {
      info(s"Small image url not found for [$productString]")
    }
    smallImageUrl
  }
}
