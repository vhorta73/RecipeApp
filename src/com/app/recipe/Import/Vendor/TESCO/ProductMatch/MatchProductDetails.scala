package com.app.recipe.Import.Vendor.TESCO.ProductMatch

import com.app.recipe.Import.Product.Model.ProductDetails
import com.app.recipe.Import.Product.Model.ProductNutrition
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.Details.MatchProductNutrition
import com.app.recipe.Log.RecipeLogging
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.Details.MatchProductNutrition

/**
 * Class to find the product nutrition information from the supplied product string.
 */
class MatchProductDetails() extends RecipeLogging {

  /**
   * Initialising all classes for each part of the product details.
   */
  private final val matchProductNutrition = new MatchProductNutrition()
  
  /**
   * The ProductNutrion matching process.
   */
  private final def getProductNutrition( string : String ) : List[ProductNutrition] = matchProductNutrition.getMatch(string)
  
  /**
   * Returns the list of product details objects from the supplied web page string.
   */
  def getMatch(productString : String) : List[ProductDetails] = {

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