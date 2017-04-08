package com.app.recipe.Import.Vendor.TESCO.ProductMatch

import com.app.recipe.Import.Product.Model.ProductDetails
import com.app.recipe.Import.Product.Model.ProductNutrition
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.Details.MatchProductNutrition
import com.app.recipe.Log.RecipeLogging

/**
 * Class to find the product nutrition information from the supplied product string.
 */
class MatchProductDetails(productString : String) extends RecipeLogging {

  /**
   * The ProductNutrion matching process.
   */
  private final def getProductNutrition( string : String ) : List[ProductNutrition] = new MatchProductNutrition(string).getMatch()
  
  /**
   * Returns the list of product details objects from the supplied web page string.
   */
  def getMatch() : List[ProductDetails] = {

    // The product details are composed of many different sub-types.
    // Please see Import.Product.ProductDetails for more information.
    
    // Initialise the final list to be returned.
    var finalList : List[ProductDetails] = List()

    // Add here subsequent product details to be added to final list.
    finalList = getProductNutrition(productString) ::: finalList
    // TODO: Add the Allergens

    finalList
  }
}