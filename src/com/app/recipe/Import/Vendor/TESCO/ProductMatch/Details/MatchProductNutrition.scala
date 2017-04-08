package com.app.recipe.Import.Vendor.TESCO.ProductMatch.Details

import com.app.recipe.Import.Product.Model.ProductNutrition
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.Details.Nutrition.MatchEnergy
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.Details.Nutrition.MatchEnergy
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.Details.Nutrition.MatchEnergy
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.Details.Nutrition.MatchFat
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.Details.Nutrition.MatchSaturates
import com.app.recipe.Log.RecipeLogging

/**
 * Class to find match the saturation values from a given line to parse.
 * The line may contain more than one saturation levels, which in case will
 * return more than one element.
 */
class MatchProductNutrition(productString : String) extends RecipeLogging {

  /**
   * All matchers get statically instantiated once.
   */
  private final val matchEnergy    = new MatchEnergy()
  private final val matchFat       = new MatchFat()
  private final val matchSaturates = new MatchSaturates()
  
  /**
   * List of all methods to check which type a string belongs to.
   */
  private final def isEnergy(string : String)     : Boolean = matchEnergy.isEnergy(string)
  private final def isFat(string : String)        : Boolean = matchFat.isFat(string)
  private final def isSaturates(string : String)  : Boolean = matchSaturates.isSaturates(string)

  /**
   * List of all methods to capture nutrition values from a string line.
   */
  private final def getEnergy(string : String)    : List[ProductNutrition] = matchEnergy.getMatch(string)
  private final def getFat(string : String)       : List[ProductNutrition] = matchFat.getMatch(string)
  private final def getSaturates(string : String) : List[ProductNutrition] = matchSaturates.getMatch(string)

  /**
   * Returns the Fat case class with the detailed values as displaying on the web page.
   */
  def getMatch() : List[ProductNutrition] = {

    // Initialise the final list to be returned.
    var finalList : List[ProductNutrition] = List()

    // Reduce the string length to the Nutrition table.
    val nutritionTableRegex = """table>(.*)</table""".r
    val nutritionTable = nutritionTableRegex.findFirstIn(productString)

    if ( ! nutritionTable.isEmpty ) {
      // Split the table by the rows.
      val table = nutritionTable.get.split("<tr>")

      // List of regex to get each of the known fields from the table.
      val valuesRegex = """(?<=td>)(\d+)[^</td]*""".r
      for( line <- table ) {
        line match {
          case x if isEnergy(x)      => finalList = getEnergy(line)    ::: finalList
//          case x if isFat(x)         => finalList = getFat(line)       ::: finalList
//          case x if isSaturates(x)   => finalList = getSaturates(line) ::: finalList
          // TODO: Match the rest and uncomment ingredient fields for next matches e.g. price
//          case x if x.contains("Carbohydrate")           => finalList = Carbohydrate(valuesRegex.findFirstMatchIn(line).get.toString().toDouble,g) :: finalList
//          case x if x.contains("sugars")                 => finalList = Sugars(valuesRegex.findFirstMatchIn(line).get.toString().toDouble,g) :: finalList
//          case x if x.contains("Fibre (g)")              => finalList = Fibre(valuesRegex.findFirstMatchIn(line).get.toString().toDouble,g) :: finalList
//          case x if x.contains("Protein (g)")            => finalList = Protein(valuesRegex.findFirstMatchIn(line).get.toString().toDouble,g) :: finalList
//          case x if x.contains("Salt (g)")               => finalList = Salt(valuesRegex.findFirstMatchIn(line).get.toString().toDouble,g) :: finalList
//          case x if x.contains("Vitamin A")              => finalList = Vitamin(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, VitaminType.A) :: finalList
//          case x if x.contains("Vitamin D")              => finalList = Vitamin(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, VitaminType.D) :: finalList
//          case x if x.contains("Vitamin E")              => finalList = Vitamin(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, VitaminType.E) :: finalList
//          case x if x.contains("Vitamin K")              => finalList = Vitamin(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, VitaminType.K) :: finalList
//          case x if x.contains("Vitamin C")              => finalList = Vitamin(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, VitaminType.C) :: finalList
//          case x if x.contains("Thiamin (B1) (mg)")      => finalList = Thiamin(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, VitaminType.B1) :: finalList
//          case x if x.contains("Thiamin [B1] (mg)")      => finalList = Thiamin(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, VitaminType.B1) :: finalList
//          case x if x.contains("Niacin (mg)")            => finalList = Naicin(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, mg) :: finalList
//          case x if x.contains("Vitamin B6")             => finalList = Vitamin(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, VitaminType.B6) :: finalList
//          case x if x.contains("Folic Acid ")            => finalList = FolicAcid(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, ug) :: finalList
//          case x if x.contains("Biotin ")                => finalList = Biotin(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, ug) :: finalList
//          case x if x.contains("Pantothenic acid (mg)")  => finalList = PanthothenicAcid(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, mg) :: finalList
//          case x if x.contains("Calcium (mg)")           => finalList = Calcium(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, mg) :: finalList
//          case x if x.contains("Iron (mg)")              => finalList = Iron(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, mg) :: finalList
//          case x if x.contains("Zinc (mg)")              => finalList = Zinc(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, mg) :: finalList
//          case x if x.contains("Iodine ")                => finalList = Iodine(valuesRegex.findFirstMatchIn(line).get.toString().toDouble, ug) :: finalList

          // Known cases to ignore
          case x if x.contains("table>")                 => Nil
          case x if x.contains(">Typical Values")        => Nil
          case x if x.contains(" water")                 => Nil
          case x if x.contains("Labelling Reference ")   => Nil
          case _ => Nil//throw new IllegalStateException("Do not know about this nutrition type: ["+line+"] in table:\n{ "+table.foreach { p => println(p) } +"}" )
        }
      }
    }
    else {
//      warn(s"Product details page seems to not have any Nutrition details... [$productString]")
    }
    
    finalList
  }
}