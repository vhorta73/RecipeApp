package com.app.recipe.Import.Vendor.TESCO.ProductMatch.Details.Nutrition

import scala.util.matching.Regex.Match
import com.app.recipe.Import.Product.Model.Energy
import com.app.recipe.Import.Product.Model.ProductNutrition
import com.app.recipe.Import.Product.Units.Model.StandardUnits.Kcal
import com.app.recipe.Import.Product.Units.Model.StandardUnits.Kj
import com.app.recipe.Import.Product.Units.Model.StandardUnits.Units
import com.app.recipe.Log.RecipeLogging
import com.app.recipe.Import.Product.Units.Model.StandardUnits

/**
 * Class to find match the energy values from a given line to parse.
 * The line may contain more than one energy levels, which in case will
 * return more than one element.
 */
class MatchEnergy() extends RecipeLogging {

  /**
   * The strings to be used on defining if supplied string contains energy 
   * information or not.
   */
  private final val INTAKE_STRING : String = "ence intake "
  private final val ENERGY_STRING : String = "energy"
  private final val KCAL_STRING   : String = "kal"
  private final val KJ_STRING     : String = "kj"

  /**
   * The regex constants to extract the energy values from supplied string.
   */
  // Matching: <th scope="row">Energy kJ</th><td>1667.4</td>
  private final val KJ_01_REGEX = """<th scope="row">Energy kJ</th><td>([0-9.]+)</td>""".r.unanchored
  // Matchig: <th scope="row">Energy (kJ)</th><td>1365</td>
  private final val KJ_02_REGEX = """<th scope="row">Energy \(kJ\)</th><td>([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>2143kJ</td>
  private final val KJ_03_REGEX = """<th scope="row">Energy</th><td>([0-9.]+)kJ</td>""".r.unanchored
  // Matching: <th scope="row">Energy:</th><td>57kJ/</td>
  private final val KJ_04_REGEX = """<th scope="row">Energy:</th><td>([0-9.]+)kJ/</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>201 kJ</td>
  private final val KJ_05_REGEX = """<th scope="row">Energy</th><td>([0-9.]+) kJ</td>""".r.unanchored
  // Matching: <th scope="row">Energy (kJ/kcal)</th><td>1531kJ/</td>
  private final val KJ_06_REGEX = """<th scope="row">Energy (kJ/kcal)</th><td>([0-9.]+)kJ/</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>201 kJ/</td><td>1006 kJ/</td></tr>
  private final val KJ_07_REGEX = """<th scope="row">Energy</th><td>([0-9.]+) kJ/</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>96kJoules</td>
  private final val KJ_08_REGEX = """<th scope="row">Energy</th><td>([0-9.]+)kJoules</td>""".r.unanchored
  // Matching: <th scope="row">Energy kJ</th><td>1,387</td>
  private final val KJ_09_COMMA_REGEX = """<th scope="row">Energy kJ</th><td>([0-9.,]+)</td>""".r.unanchored
  // Matching: <th scope="row">Energy (kJ/kcal)</th><td>1531kJ/</td>
  private final val KJ_10_REGEX = """<th scope="row">Energy \(kJ/kcal\)</th><td>([0-9.]+)kJ/</td>""".r.unanchored
  // Matching: <th scope="row">Energy:</th><td>77kJ</td>
  private final val KJ_11_REGEX = """<th scope="row">Energy:</th><td>([0-9.]+)kJ</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>641 KJ</td>
  private final val KJ_12_REGEX = """<th scope="row">Energy</th><td>([0-9.]+) KJ</td>""".r.unanchored

  // Matching: <th scope="row">Energy kCal</th><td>400.0</td>
  private final val KCAL_01_REGEX = """<th scope="row">Energy kCal</th><td>([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">Energy (kcal)</th><td>323</td>
  private final val KCAL_02_REGEX = """<th scope="row">Energy \(kcal\)</th><td>([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>364kcal</td>
  private final val KCAL_03_REGEX = """<th scope="row">Energy</th><td>([0-9.]+)kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy (kcals)</th><td>366</td>
  private final val KCAL_04_REGEX = """<th scope="row">Energy \(kcals\)</th><td>([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">Energy kcal</th><td>357</td>
  private final val KCAL_05_REGEX = """<th scope="row">Energy kcal</th><td>([0-9.]+)</td>""".r.unanchored
  
  // Matching: <th scope="row">Energy</th><td>3kJ/1kcal</td>
  private final val KJ_KCAL_01_REGEX = """<th scope="row">Energy</th><td>([0-9.]+)kJ/([0-9.]+)kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy (kJ/kcal)</th><td>1523/364</td>
  private final val KJ_KCAL_02_REGEX = """<th scope="row">Energy \(kJ/kcal\)</th><td>([0-9.]+)/([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>1510kJ (359kcal)</td></tr>
  private final val KJ_KCAL_03_REGEX = """<th scope="row">Energy</th><td>([0-9.]+)kJ \(([0-9.]+)kcal\)</td>""".r.unanchored
  // Matching: <th scope="row">Energy:</th><td>137 kJ/33 Kcal</td></tr>
  private final val KJ_KCAL_04_REGEX = """<th scope="row">Energy:</th><td>([0-9.]+) kJ/([0-9.]+) Kcal</td></tr>""".r.unanchored
  // Matching : <th scope="row">Energy, kJ/kcal</th><td>117/28</td>
  private final val KJ_KCAL_05_REGEX = """<th scope="row">Energy, kJ/kcal</th><td>([0-9.]+)/([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>1471 kJ (351 kcal)</td>
  private final val KJ_KCAL_06_REGEX = """<th scope="row">Energy</th><td>([0-9.]+) kJ \(([0-9.]+) kcal\)</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>93 kJ / 22 kcal</td></tr>
  private final val KJ_KCAL_07_REGEX = """<th scope="row">Energy</th><td>([0-9.]+) kJ / ([0-9.]+) kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>119kJ / 28kcal</td><td>595kJ / 140kcal</td>
  private final val KJ_KCAL_08_REGEX = """<th scope="row">Energy</th><td>([0-9.]+)kJ / ([0-9.]+)kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>150kj & 36 kcal</td>
  private final val KJ_KCAL_09_REGEX = """<th scope="row">Energy</th><td>([0-9.]+)kj & ([0-9.]+) kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>891 kJ/214 kcal</td>
  private final val KJ_KCAL_10_REGEX = """<th scope="row">Energy</th><td>([0-9.]+) kJ/([0-9.]+) kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>97 kJ 23 kcal</td>
  private final val KJ_KCAL_11_REGEX = """<th scope="row">Energy</th><td>([0-9.]+) kJ ([0-9.]+) kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>1738kj/415kcal</td>
  private final val KJ_KCAL_12_REGEX = """<th scope="row">Energy</th><td>([0-9.]+)kj/([0-9.]+)kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy:</th><td>1 621 kJ/381 kcal</td>
  private final val KJ_KCAL_13_SPACE_REGEX = """<th scope="row">Energy:</th><td>([0-9. ]+)kJ/([0-9.]+) kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>2016kJ 481kcal</td>
  private final val KJ_KCAL_14_REGEX = """<th scope="row">Energy</th><td>([0-9.]+)kJ ([0-9.]+)kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy Value (kJ/kcal)</th><td>1523/364</td>
  private final val KJ_KCAL_15_REGEX = """<th scope="row">Energy Value \(kJ/kcal\)</th><td>([0-9.]+)/([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>3kJ (<1kcal)</td>
  private final val KJ_KCAL_16_REGEX = """<th scope="row">Energy</th><td>([0-9.]+)kJ \(<([0-9.]+)kcal\)</td>""".r.unanchored
  // Matching: <th scope="row">Energy:</th><td>1046kJ/251kcal</td>
  private final val KJ_KCAL_17_REGEX = """<th scope="row">Energy:</th><td>([0-9.]+)kJ/([0-9.]+)kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>105 kJ/ 25 kcal</td>
  private final val KJ_KCAL_18_REGEX = """<th scope="row">Energy</th><td>([0-9.]+) kJ/ ([0-9.]+) kcal</td>""".r.unanchored

  /**
   * Returns the Energy case class with the detailed values as displaying on the web page.
   */
  def getMatch(productString : String) : List[ProductNutrition] = {

    // Adding all elements to this list which is to be returned last.
    var finalList : List[ProductNutrition] = Nil
    
    productString match {
      // Matching on Kj 
      case KJ_01_REGEX(kjDouble)                 => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj))
      case KJ_02_REGEX(kjDouble)                 => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj))
      case KJ_03_REGEX(kjDouble)                 => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj))
      case KJ_04_REGEX(kjDouble)                 => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj))
      case KJ_05_REGEX(kjDouble)                 => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj))
      case KJ_06_REGEX(kjDouble)                 => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj))
      case KJ_07_REGEX(kjDouble)                 => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj))
      case KJ_08_REGEX(kjDouble)                 => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj))
      case KJ_09_COMMA_REGEX(kjDouble)           => finalList = finalList ::: List(Energy(kjDouble.replace(",", "").toDouble,Kj))
      case KJ_10_REGEX(kjDouble)                 => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj))
      case KJ_11_REGEX(kjDouble)                 => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj))
      case KJ_12_REGEX(kjDouble)                 => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj))

      // Matching on Kcal
      case KCAL_01_REGEX(kcalDouble)             => finalList = finalList ::: List(Energy(kcalDouble.toDouble,Kcal))
      case KCAL_02_REGEX(kcalDouble)             => finalList = finalList ::: List(Energy(kcalDouble.toDouble,Kcal))
      case KCAL_03_REGEX(kcalDouble)             => finalList = finalList ::: List(Energy(kcalDouble.toDouble,Kcal))
      case KCAL_04_REGEX(kcalDouble)             => finalList = finalList ::: List(Energy(kcalDouble.toDouble,Kcal))
      case KCAL_05_REGEX(kcalDouble)             => finalList = finalList ::: List(Energy(kcalDouble.toDouble,Kcal))

      // Matching on Kj Kcal
      case KJ_KCAL_01_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_02_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_03_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_04_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_05_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_06_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_07_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_08_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_09_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_10_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_11_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_12_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_13_SPACE_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.replaceAll(" ", "").toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_14_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_15_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_16_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_17_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_18_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case _ => warn(s"No Energy Matched $productString")
    }

    return finalList
  }

  /**
   * Matching criteria for energy on a given line. It must match only line that
   * are certain of containing the energy values. By default returns false.
   */
  def isEnergy(productString : String) : Boolean = {
    productString match {
      case line if line.toLowerCase().contains(INTAKE_STRING) => false
      case line if line.toLowerCase().contains(ENERGY_STRING) => true
      case line if line.toLowerCase().contains(KCAL_STRING)   => true
      case line if line.toLowerCase().contains(KJ_STRING)     => true
      case _ => false
    }
  }
}