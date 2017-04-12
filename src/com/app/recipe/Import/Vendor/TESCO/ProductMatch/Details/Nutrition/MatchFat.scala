package com.app.recipe.Import.Vendor.TESCO.ProductMatch.Details.Nutrition

import com.app.recipe.Import.Product.Model.ProductNutrition
import com.app.recipe.Import.Product.Units.Model.StandardUnits
import com.app.recipe.Import.Product.Units.Model.StandardUnits.Units
import com.app.recipe.String.Utils._
import com.app.recipe.Import.Product.Model.Fat

/**
 * Class to find match the fat values from a given line to parse.
 * The line may contain more than one fat levels, which in case will
 * return more than one element.
 */
class MatchFat() {
  
  /**
   * Defining what states a string line contains fat information.
   */
  private final val FAT_STRING : String = "fat"

  /**
   * All regex that will retrieve the fat value from a given string.
   */
  // Matching: <th scope="row">Fat (g)</th><td>10.0</td><td>5.0</td><td>-</td><td>-</td></tr>
  private final val FAT_G_01_REGEX = """(?i)<th scope="row">Fat \(g\)</th><td>([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">Fat</th><td>11.0g</td>
  private final val FAT_G_02_REGEX = """(?i)<th scope="row">Fat</th><td>([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">Fats (g)</th><td>4.8</td>
  private final val FAT_G_03_REGEX = """(?i)<th scope="row">Fats \(g\)</th><td>([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">Fat:</th><td>0g</td>
  private final val FAT_G_04_REGEX = """(?i)<th scope="row">Fat:</th><td>([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">Fat:</th><td>< 0.5 g</td>
  private final val FAT_G_05_REGEX = """(?i)<th scope="row">Fat:</th><td>< ([0-9.]+) g</td>""".r.unanchored
  // Matching: <th scope="row">Fat</th><td>Negligible amount</td>
  private final val FAT_G_06_NEGLIGIBLE_REGEX = """(?i)<th scope="row">Fat</th><td>[a-zA-Z ]+(egligible amount)[a-zA-Z ]+</td>""".r.unanchored
  // Matching: <th scope="row">Fat</th><td>1.5 g</td>
  private final val FAT_G_07_REGEX = """(?i)<th scope="row">Fat</th><td>([0-9.]+) g</td>""".r.unanchored
  // Matching: <th scope="row">Contains negligible amounts of - Fat, Saturates and Protein. Electrolytes per 100ml: Sodium 50mg</th><td>-</td>
  private final val FAT_G_08_NEGLIGIBLE_REGEX = """(?i)<th scope="row">[a-zA-z ]+(negligible)[^0-9.]+.*</th>""".r.unanchored
  // Matching: <th scope="row">Fat:</th><td><0.5 g</td></tr>
  private final val FAT_G_09_REGEX = """(?i)<th scope="row">Fat:</th><td><([0-9.]+) g</td>""".r.unanchored
  // Matching: <th scope="row">Fat</th><td>Negligible amount</td><td>Negligible amount</td></tr>
  private final val FAT_G_10_NEGLIGIBLE_REGEX = """(?i)<th scope="row">Fat</th><td>(Negligible amount)</td>""".r.unanchored
  // Matching: <th scope="row">Fat</th><td>< 0.1g</td></tr>
  private final val FAT_G_11_REGEX = """(?i)<th scope="row">Fat</th><td>< ([0-9.]+)g</td></tr>""".r.unanchored
  // Matching: <th scope="row">Fat, saturates, protein - negligible amount</th><td>-</td>
  private final val FAT_G_12_NEGLIGIBLE_REGEX = """(?i)<th scope="row">Fat, saturates, protein - (negligible) amount</th>""".r.unanchored
  // Matching: <th scope="row">Fat</th><td>Negligible Amount</td><td>Negligible Amount</td></tr>
  private final val FAT_G_13_NEGLIGIBLE_REGEX = """(?i)<th scope="row">Fat</th><td>(Negligible) Amount</td>""".r.unanchored
  // Matching: <th scope="row">Fat</th><td><0.1g</td>
  private final val FAT_G_14_REGEX = """(?i)<th scope="row">Fat</th><td><([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">Trans Fatty Acids</th><td>0.1g</td>
  private final val FAT_G_15_REGEX = """(?i)<th scope="row">Trans Fatty Acids</th><td>([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">Fat</th><td>Neglibible amount</td><td>Neglibible amount</td>
  private final val FAT_G_16_NEGLIGIBLE_REGEX = """(?i)<th scope="row">Fat</th><td>(Neglibible) amount</td><td>Neglibible amount</td>""".r.unanchored
  // Matching: <th scope="row">Fat 1</th><td>1.5 g</td><td>2%</td>
  private final val FAT_G_17_REGEX = """(?i)<th scope="row">Fat 1</th><td>([0-9.]+) g</td>""".r.unanchored
  // Matching: <th scope="row">Fat, g</th><td>0</td><td>0</td>
  private final val FAT_G_18_REGEX = """(?i)<th scope="row">Fat, g</th><td>([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">Fat g</th><td>7</td><td>23</td>
  private final val FAT_G_19_REGEX = """(?i)<th scope="row">Fat g</th><td>([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">Fat</th><td><0,5 g</td>
  private final val FAT_G_20_COMMA_REGEX = """(?i)<th scope="row">Fat</th><td><([0-9.,]+) g</td>""".r.unanchored
  // Matching: <th scope="row">Fat</th><td>0,2 g</td>
  private final val FAT_G_21_COMMA_REGEX = """(?i)<th scope="row">Fat</th><td>([0-9.,]+) g</td>""".r.unanchored
  // Matching: <th scope="row">Fat</th><td>negligible amount</td>
  private final val FAT_G_22_NEGLIGIBLE_REGEX = """(?i)<th scope="row">Fat</th><td>(negligible) amount</td>""".r.unanchored
  // Matching: <th scope="row">Total Fat</th><td>23.2g</td>
  private final val FAT_G_23_REGEX = """(?i)<th scope="row">Total Fat</th><td>([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">Fat</th><td>trace</td><td>trace</td>
  private final val FAT_G_24_TRACE_REGEX = """(?i)<th scope="row">Fat</th><td>(trace)</td>""".r.unanchored
  // Matching: <th scope="row">Fat</th><td>9.9g(14%*)</td><td>30.5g</td></tr>
  private final val FAT_G_25_REGEX = """(?i)<th scope="row">Fat</th><td>([0-9.]+)g\([0-9%*]+\)</td>""".r.unanchored
  // Matching: <th scope="row">Fat - total</th><td>0 g</td>
  private final val FAT_G_26_REGEX = """(?i)<th scope="row">Fat - total</th><td>([0-9.]+) g</td>""".r.unanchored
  // Matching: negligible
  private final val FAT_G_27_NEGLIGIBLE_REGEX = """(?i)(negligible)""".r.unanchored

  // Matching: <th scope="row">†Long Chain Polyunsaturated fatty acids</th><td>-</td><td>-</td>
  private final val IGNORE_LINE = """(?i)Long Chain (Polyunsaturated fatty acids)""".r.unanchored

  /**
   * Returns the Fat case class with the detailed values as displaying on the web page.
   */
  def getMatch(productString : String) : List[ProductNutrition] = {

    // Adding all elements to this list which is to be returned last.
    var finalList : List[ProductNutrition] = Nil
    
    productString match {
      case FAT_G_01_REGEX(fat)                   => finalList = finalList ::: List(Fat(fat.toDouble,StandardUnits.g))
      case FAT_G_02_REGEX(fat)                   => finalList = finalList ::: List(Fat(fat.toDouble,StandardUnits.g))
      case FAT_G_03_REGEX(fat)                   => finalList = finalList ::: List(Fat(fat.toDouble,StandardUnits.g))
      case FAT_G_04_REGEX(fat)                   => finalList = finalList ::: List(Fat(fat.toDouble,StandardUnits.g))
      case FAT_G_05_REGEX(fat)                   => finalList = finalList ::: List(Fat(fat.toDouble,StandardUnits.g))
      case FAT_G_06_NEGLIGIBLE_REGEX(negligible) => finalList = finalList ::: List(Fat(0,StandardUnits.g))
      case FAT_G_07_REGEX(fat)                   => finalList = finalList ::: List(Fat(fat.toDouble,StandardUnits.g))
      case FAT_G_08_NEGLIGIBLE_REGEX(negligible) => finalList = finalList ::: List(Fat(0,StandardUnits.g))
      case FAT_G_09_REGEX(fat)                   => finalList = finalList ::: List(Fat(fat.toDouble,StandardUnits.g))
      case FAT_G_10_NEGLIGIBLE_REGEX(negligible) => finalList = finalList ::: List(Fat(0,StandardUnits.g))
      case FAT_G_11_REGEX(fat)                   => finalList = finalList ::: List(Fat(fat.toDouble,StandardUnits.g))
      case FAT_G_12_NEGLIGIBLE_REGEX(negligible) => finalList = finalList ::: List(Fat(0,StandardUnits.g))
      case FAT_G_13_NEGLIGIBLE_REGEX(negligible) => finalList = finalList ::: List(Fat(0,StandardUnits.g))
      case FAT_G_14_REGEX(fat)                   => finalList = finalList ::: List(Fat(fat.toDouble,StandardUnits.g))
      case FAT_G_15_REGEX(fat)                   => finalList = finalList ::: List(Fat(fat.toDouble,StandardUnits.g))
      case FAT_G_16_NEGLIGIBLE_REGEX(negligible) => finalList = finalList ::: List(Fat(0,StandardUnits.g))
      case FAT_G_17_REGEX(fat)                   => finalList = finalList ::: List(Fat(fat.toDouble,StandardUnits.g))
      case FAT_G_18_REGEX(fat)                   => finalList = finalList ::: List(Fat(fat.toDouble,StandardUnits.g))
      case FAT_G_19_REGEX(fat)                   => finalList = finalList ::: List(Fat(fat.toDouble,StandardUnits.g))
      case FAT_G_20_COMMA_REGEX(fat)             => finalList = finalList ::: List(Fat(fat.replaceAll(",", "").toDouble,StandardUnits.g))
      case FAT_G_21_COMMA_REGEX(fat)             => finalList = finalList ::: List(Fat(fat.replaceAll(",", "").toDouble,StandardUnits.g))
      case FAT_G_22_NEGLIGIBLE_REGEX(negligible) => finalList = finalList ::: List(Fat(0,StandardUnits.g))
      case FAT_G_23_REGEX(fat)                   => finalList = finalList ::: List(Fat(fat.toDouble,StandardUnits.g))
      case FAT_G_24_TRACE_REGEX(trace)           => finalList = finalList ::: List(Fat(0,StandardUnits.g))
      case FAT_G_25_REGEX(fat)                   => finalList = finalList ::: List(Fat(fat.toDouble,StandardUnits.g))
      case FAT_G_26_REGEX(fat)                   => finalList = finalList ::: List(Fat(fat.toDouble,StandardUnits.g))
      case FAT_G_27_NEGLIGIBLE_REGEX(negligible) => finalList = finalList ::: List(Fat(0,StandardUnits.g))

      case IGNORE_LINE(str)                      => Nil
      case _ => warn(s"No Fat Matched $productString")
    }
    return finalList
  }

  /**
   * Matching criteria for fat on a given line. It must match only line that
   * are certain of containing the fat values. By default returns false.
   */
  def isFat(productString : String) : Boolean = {
    productString match {
      case x if x.toLowerCase().contains(FAT_STRING) => true
      case _ => false
    }
  }
}