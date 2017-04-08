package com.app.recipe.Import.Vendor.TESCO.ProductMatch

import com.app.recipe.Import.Product.Nutrition.Model._
import com.app.recipe.Import.Product.Nutrition.Model.ProductInformation
import com.app.recipe.Import.Product.Units.Model.StandardUnits._
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.Nutrition.MatchEnergy
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.Nutrition.MatchFat
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.Nutrition.MatchFat
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.Nutrition.MatchSaturates
import com.app.recipe.Log.RecipeLogging

/**
 * Class to find the product nutrition information from the supplied product string.
 */
class MatchNutritionInformation(productString : String) extends RecipeLogging {

  /**
   * List of all methods to capture nutrition values from a string line.
   */
  private final def isEnergy(string : String)     : Boolean = new MatchEnergy(string).isEnergy()
  private final def isFat(string : String)        : Boolean = new MatchFat(string).isFat()
  private final def isSaturates(string : String)  : Boolean = new MatchSaturates(string).isSaturates()
  private final def getEnergy(string : String)    : List[ProductInformation] = new MatchEnergy(string).getMatch()
  private final def getFat(string : String)       : List[ProductInformation] = new MatchFat(string).getMatch()
  private final def getSaturates(string : String) : List[ProductInformation] = new MatchSaturates(string).getMatch()
// TODO: Allergens?...  
  /**
   * Returns the list of nutrition information objects with the data displaying
   * on the product details web page.
   */
  def getMatch() : List[ProductInformation] = {

    // Reduce the string length to the Nutrition table.
    val nutritionTableRegex = """table>(.*)</table""".r
    val nutritionTable = nutritionTableRegex.findFirstIn(productString)
debug(nutritionTable.toString())
    // Initialise the final list to be returned.
    var finalList : List[ProductInformation] = List()

    // If nothing was found, nothing to match.
    if ( nutritionTable.isEmpty ) return finalList

    // Split the table by the rows.
    val table = nutritionTable.get.split("<tr>")

    // List of regex to get each of the known fields from the table.
    val valuesRegex = """(?<=td>)(\d+)[^</td]*""".r
    for( line <- table ) {
      line match {
        case x if isEnergy(x)      => finalList = getEnergy(line)    ::: finalList
        case x if isFat(x)         => finalList = getFat(line)       ::: finalList
        case x if isSaturates(x)   => finalList = getSaturates(line) ::: finalList
        // TODO: Match the rest and uncomment ingredient fields for next matches e.g. price
//        case x if x.contains("Carbohydrate")           => finalList = Carbohydrate(valuesRegex.findFirstMatchIn(line).get.toString().toDouble,g) :: finalList
//        case x if x.contains("sugars")                 => finalList = Sugars(valuesRegex.findFirstMatchIn(line).get.toString().toDouble,g) :: finalList
//        case x if x.contains("Fibre (g)")              => finalList = Fibre(valuesRegex.findFirstMatchIn(line).get.toString().toDouble,g) :: finalList
//        case x if x.contains("Protein (g)")            => finalList = Protein(valuesRegex.findFirstMatchIn(line).get.toString().toDouble,g) :: finalList
//        case x if x.contains("Salt (g)")               => finalList = Salt(valuesRegex.findFirstMatchIn(line).get.toString().toDouble,g) :: finalList
//        case x if x.contains("Vitamin A")              => finalList = Vitamin(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, VitaminType.A) :: finalList
//        case x if x.contains("Vitamin D")              => finalList = Vitamin(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, VitaminType.D) :: finalList
//        case x if x.contains("Vitamin E")              => finalList = Vitamin(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, VitaminType.E) :: finalList
//        case x if x.contains("Vitamin K")              => finalList = Vitamin(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, VitaminType.K) :: finalList
//        case x if x.contains("Vitamin C")              => finalList = Vitamin(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, VitaminType.C) :: finalList
//        case x if x.contains("Thiamin (B1) (mg)")      => finalList = Thiamin(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, VitaminType.B1) :: finalList
//        case x if x.contains("Thiamin [B1] (mg)")      => finalList = Thiamin(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, VitaminType.B1) :: finalList
//        case x if x.contains("Niacin (mg)")            => finalList = Naicin(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, mg) :: finalList
//        case x if x.contains("Vitamin B6")             => finalList = Vitamin(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, VitaminType.B6) :: finalList
//        case x if x.contains("Folic Acid ")            => finalList = FolicAcid(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, ug) :: finalList
//        case x if x.contains("Biotin ")                => finalList = Biotin(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, ug) :: finalList
//        case x if x.contains("Pantothenic acid (mg)")  => finalList = PanthothenicAcid(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, mg) :: finalList
//        case x if x.contains("Calcium (mg)")           => finalList = Calcium(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, mg) :: finalList
//        case x if x.contains("Iron (mg)")              => finalList = Iron(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, mg) :: finalList
//        case x if x.contains("Zinc (mg)")              => finalList = Zinc(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, mg) :: finalList
//        case x if x.contains("Iodine ")                => finalList = Iodine(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, ug) :: finalList

        // Known cases to ignore
        case x if x.contains("table>")                 => Nil
        case x if x.contains(">Typical Values")        => Nil
        case x if x.contains(" water")                 => Nil
        case x if x.contains("Labelling Reference ")   => Nil
        case _ => Nil//throw new IllegalStateException("Do not know about this nutrition type: ["+line+"] in table:\n{ "+table.foreach { p => println(p) } +"}" )
      }
    }
    return finalList
  }
  
}