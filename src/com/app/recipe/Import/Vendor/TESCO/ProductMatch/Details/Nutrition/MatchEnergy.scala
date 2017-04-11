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
  private final val INTAKE_STRING  : String = "ence intake "
  private final val ENERGY_STRING  : String = "energy"
  private final val KCAL_STRING    : String = "kal"
  private final val KJ_STRING      : String = "kj"
  private final val PLANT_STEROLDS : String = "plant sterols"

  /**
   * The regex constants to extract the energy values from supplied string.
   */
  // Matching: <th scope="row">Energy kJ</th><td>1667.4</td>
  private final val KJ_01_REGEX = """(?i)<th scope="row">Energy kJ</th><td>([0-9.]+)</td>""".r.unanchored
  // Matchig: <th scope="row">Energy (kJ)</th><td>1365</td>
  private final val KJ_02_REGEX = """(?i)<th scope="row">Energy \(kJ\)</th><td>([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>2143kJ</td>
  private final val KJ_03_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+)kJ</td>""".r.unanchored
  // Matching: <th scope="row">Energy:</th><td>57kJ/</td>
  private final val KJ_04_REGEX = """(?i)<th scope="row">Energy:</th><td>([0-9.]+)kJ/</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>201 kJ</td>
  private final val KJ_05_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+) kJ</td>""".r.unanchored
  // Matching: <th scope="row">Energy (kJ/kcal)</th><td>1531kJ/</td>
  private final val KJ_06_REGEX = """(?i)<th scope="row">Energy (kJ/kcal)</th><td>([0-9.]+)kJ/</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>201 kJ/</td><td>1006 kJ/</td></tr>
  private final val KJ_07_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+) kJ/</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>96kJoules</td>
  private final val KJ_08_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+)kJoules</td>""".r.unanchored
  // Matching: <th scope="row">Energy kJ</th><td>1,387</td>
  private final val KJ_09_COMMA_REGEX = """(?i)<th scope="row">Energy kJ</th><td>([0-9.,]+)</td>""".r.unanchored
  // Matching: <th scope="row">Energy (kJ/kcal)</th><td>1531kJ/</td>
  private final val KJ_10_REGEX = """(?i)<th scope="row">Energy \(kJ/kcal\)</th><td>([0-9.]+)kJ/</td>""".r.unanchored
  // Matching: <th scope="row">Energy:</th><td>77kJ</td>
  private final val KJ_11_REGEX = """(?i)<th scope="row">Energy:</th><td>([0-9.]+)kJ</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>641 KJ</td>
  private final val KJ_12_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+) KJ</td>""".r.unanchored
  // Matching: <th scope="row">Energy kJ</th><td>247kJ</td>
  private final val KJ_13_REGEX = """(?i)<th scope="row">Energy kJ</th><td>([0-9.]+)kJ</td>""".r.unanchored
  // Matching: <th scope="row">-</th><td>970 kJ</td>
  private final val KJ_14_REGEX = """(?i)<th scope="row">-</th><td>([0-9.]+) kJ</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>105kJ/</td>
  private final val KJ_15_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+)kJ/</td>""".r.unanchored
  // Matching: <th scope="row">-</th><td>1588kJ</td>
  private final val KJ_16_REGEX = """(?i)<th scope="row">-</th><td>([0-9.]+)kJ</td>""".r.unanchored
  // Matching: <th scope="row">Energy Value kJ</th><td>Trace</td>
  private final val KJ_17_TRACE_REGEX = """(?i)<th scope="row">Energy Value kJ</th><td>(Trace)</td>""".r.unanchored
  // Matching: <th scope="row">Energy -kJ</th><td>1226kJ</td>
  private final val KJ_18_REGEX = """(?i)<th scope="row">Energy -kJ</th><td>([0-9.]+)kJ</td>""".r.unanchored

  // Matching: <th scope="row">Energy kCal</th><td>400.0</td>
  private final val KCAL_01_REGEX = """(?i)<th scope="row">Energy kCal</th><td>([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">Energy (kcal)</th><td>323</td>
  private final val KCAL_02_REGEX = """(?i)<th scope="row">Energy \(kcal\)</th><td>([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>364kcal</td>
  private final val KCAL_03_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+)kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy (kcals)</th><td>366</td>
  private final val KCAL_04_REGEX = """(?i)<th scope="row">Energy \(kcals\)</th><td>([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">Energy kcal</th><td>357</td>
  private final val KCAL_05_REGEX = """(?i)<th scope="row">Energy kcal</th><td>([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">Energy (Kcal)</th><td>483</td>
  private final val KCAL_06_REGEX = """(?i)<th scope="row">Energy \(Kcal\)</th><td>([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">Energy kcal</th><td>58kcal</td>
  private final val KCAL_07_REGEX = """(?i)<th scope="row">Energy kcal</th><td>([0-9.]+)kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>233 kcal</td>
  private final val KCAL_08_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+) kcal</td>""".r.unanchored

  // Matching: <th scope="row">Energy</th><td>3kJ/1kcal</td>
  private final val KJ_KCAL_01_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+)kJ/([0-9.]+)kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy (kJ/kcal)</th><td>1523/364</td>
  private final val KJ_KCAL_02_REGEX = """(?i)<th scope="row">Energy \(kJ/kcal\)</th><td>([0-9.]+)/([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>1510kJ (359kcal)</td></tr>
  private final val KJ_KCAL_03_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+)kJ \(([0-9.]+)kcal\)</td>""".r.unanchored
  // Matching: <th scope="row">Energy:</th><td>137 kJ/33 Kcal</td></tr>
  private final val KJ_KCAL_04_REGEX = """(?i)<th scope="row">Energy:</th><td>([0-9.]+) kJ/([0-9.]+) Kcal</td></tr>""".r.unanchored
  // Matching : <th scope="row">Energy, kJ/kcal</th><td>117/28</td>
  private final val KJ_KCAL_05_REGEX = """(?i)<th scope="row">Energy, kJ/kcal</th><td>([0-9.]+)/([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>1471 kJ (351 kcal)</td>
  private final val KJ_KCAL_06_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+) kJ \(([0-9.]+) kcal\)</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>93 kJ / 22 kcal</td></tr>
  private final val KJ_KCAL_07_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+) kJ / ([0-9.]+) kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>119kJ / 28kcal</td><td>595kJ / 140kcal</td>
  private final val KJ_KCAL_08_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+)kJ / ([0-9.]+)kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>150kj & 36 kcal</td>
  private final val KJ_KCAL_09_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+)kj & ([0-9.]+) kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>891 kJ/214 kcal</td>
  private final val KJ_KCAL_10_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+) kJ/([0-9.]+) kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>97 kJ 23 kcal</td>
  private final val KJ_KCAL_11_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+) kJ ([0-9.]+) kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>1738kj/415kcal</td>
  private final val KJ_KCAL_12_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+)kj/([0-9.]+)kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy:</th><td>1 621 kJ/381 kcal</td>
  private final val KJ_KCAL_13_SPACE_REGEX = """(?i)<th scope="row">Energy:</th><td>([0-9. ]+)kJ/([0-9.]+) kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>2016kJ 481kcal</td>
  private final val KJ_KCAL_14_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+)kJ ([0-9.]+)kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy Value (kJ/kcal)</th><td>1523/364</td>
  private final val KJ_KCAL_15_REGEX = """(?i)<th scope="row">Energy Value \(kJ/kcal\)</th><td>([0-9.]+)/([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>3kJ (<1kcal)</td>
  private final val KJ_KCAL_16_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+)kJ \(<([0-9.]+)kcal\)</td>""".r.unanchored
  // Matching: <th scope="row">Energy:</th><td>1046kJ/251kcal</td>
  private final val KJ_KCAL_17_REGEX = """(?i)<th scope="row">Energy:</th><td>([0-9.]+)kJ/([0-9.]+)kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>105 kJ/ 25 kcal</td>
  private final val KJ_KCAL_18_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+) kJ/ ([0-9.]+) kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>60/14</td>
  private final val KJ_KCAL_19_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+)/([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">Energy kJ/kcal</th><td>349/82</td>
  private final val KJ_KCAL_20_REGEX = """(?i)<th scope="row">Energy kJ/kcal</th><td>([0-9.]+)/([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>239KJ/57Kcal</td>
  private final val KJ_KCAL_21_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+)KJ/([0-9.]+)Kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy kJ/kcal</th><td>93kJ/22kcal</td>
  private final val KJ_KCAL_22_REGEX = """(?i)<th scope="row">Energy kJ/kcal</th><td>([0-9.]+)kJ/([0-9.]+)kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy:</th><td>325 kcal - 1360 kJ</td>
  private final val KJ_KCAL_23_REGEX = """(?i)<th scope="row">Energy:</th><td>([0-9.]+) kcal - ([0-9.]+) kJ</td>""".r.unanchored
  // Matching: <th scope="row">Energy, kJ/kcal</th><td>423 / 100</td>
  private final val KJ_KCAL_24_REGEX = """(?i)<th scope="row">Energy, kJ/kcal</th><td>([0-9.]+) / ([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">Energy (kJ/kcal)</th><td>234/ 55</td>
  private final val KJ_KCAL_25_REGEX = """(?i)<th scope="row">Energy \(kJ/kcal\)</th><td>([0-9.]+)/ ([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>160KJ40Kcal</td>
  private final val KJ_KCAL_26_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+)KJ([0-9.]+)Kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy (kJ/kcal)</th><td>121/28kcal</td>
  private final val KJ_KCAL_27_REGEX = """(?i)<th scope="row">Energy \(kJ/kcal\)</th><td>([0-9.]+)/([0-9.]+)kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>239KJ/57kcal</td>
  private final val KJ_KCAL_28_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+)KJ/([0-9.]+)kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy (kJ/kcal)</th><td>232/ 55</td>
  private final val KJ_KCAL_29_REGEX = """(?i)<th scope="row">Energy \(kJ/kcal\)</th><td>([0-9.]+)/ ([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">Energ</th><td>217kJ (51kcal)</td>
  private final val KJ_KCAL_30_REGEX = """(?i)<th scope="row">Energ</th><td>([0-9.]+)kJ \(([0-9.]+)kcal\)</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>160KJ/40kcal</td>
  private final val KJ_KCAL_31_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+)KJ/([0-9.]+)kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>0 kJ/kcal</td>
  private final val KJ_KCAL_32_SAME_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+) kJ/kcal</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>9.6kcal/40kJ</td>
  private final val KJ_KCAL_33_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+)kcal/([0-9.]+)kJ</td>""".r.unanchored
  // Matching: <th scope="row">Energy value kJ/(kcal)</th><td>0/(0)</td>
  private final val KJ_KCAL_34_REGEX = """(?i)<th scope="row">Energy value kJ/\(kcal\)</th><td>([0-9.]+)/\(([0-9.]+)\)</td>""".r.unanchored
  // Matching: <th scope="row">Energy</th><td>8.4 kJ/ 2.0kcal</td>
  private final val KJ_KCAL_35_REGEX = """(?i)<th scope="row">Energy</th><td>([0-9.]+) [a-zA-Z]+/ ([0-9.]+)[a-zA-Z]+</td>""".r.unanchored

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
      case KJ_13_REGEX(kjDouble)                 => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj))
      case KJ_14_REGEX(kjDouble)                 => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj))
      case KJ_15_REGEX(kjDouble)                 => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj))
      case KJ_16_REGEX(kjDouble)                 => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj))
      case KJ_17_TRACE_REGEX(trace)              => finalList = finalList ::: List(Energy(0,Kj),Energy(0,Kcal))

      // Matching on Kcal
      case KCAL_01_REGEX(kcalDouble)             => finalList = finalList ::: List(Energy(kcalDouble.toDouble,Kcal))
      case KCAL_02_REGEX(kcalDouble)             => finalList = finalList ::: List(Energy(kcalDouble.toDouble,Kcal))
      case KCAL_03_REGEX(kcalDouble)             => finalList = finalList ::: List(Energy(kcalDouble.toDouble,Kcal))
      case KCAL_04_REGEX(kcalDouble)             => finalList = finalList ::: List(Energy(kcalDouble.toDouble,Kcal))
      case KCAL_05_REGEX(kcalDouble)             => finalList = finalList ::: List(Energy(kcalDouble.toDouble,Kcal))
      case KCAL_06_REGEX(kcalDouble)             => finalList = finalList ::: List(Energy(kcalDouble.toDouble,Kcal))
      case KCAL_07_REGEX(kcalDouble)             => finalList = finalList ::: List(Energy(kcalDouble.toDouble,Kcal))
      case KCAL_08_REGEX(kcalDouble)             => finalList = finalList ::: List(Energy(kcalDouble.toDouble,Kcal))

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
      case KJ_KCAL_19_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_20_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_21_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_22_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_23_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_24_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_25_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_26_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_27_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_28_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_29_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_30_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_31_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_32_SAME_REGEX(sameDouble) => finalList = finalList ::: List(Energy(sameDouble.toDouble,Kj),Energy(sameDouble.toDouble,Kcal))
      case KJ_KCAL_33_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_34_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
      case KJ_KCAL_35_REGEX(kjDouble,kcalDouble) => finalList = finalList ::: List(Energy(kjDouble.toDouble,Kj),Energy(kcalDouble.toDouble,Kcal))
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
      // These may contain energy references but are not the line of interest.
      case line if line.toLowerCase().contains(INTAKE_STRING)  => false
      case line if line.toLowerCase().contains(PLANT_STEROLDS) => false
      
      // Expected energy lines.
      case line if line.toLowerCase().contains(ENERGY_STRING)  => true
      case line if line.toLowerCase().contains(KCAL_STRING)    => true
      case line if line.toLowerCase().contains(KJ_STRING)      => true
      
      // All the rest.
      case _ => false
    }
  }
}