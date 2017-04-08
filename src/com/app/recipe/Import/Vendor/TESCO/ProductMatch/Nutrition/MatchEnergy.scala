package com.app.recipe.Import.Vendor.TESCO.ProductMatch.Nutrition

import scala.util.matching.Regex.Match

import com.app.recipe.Import.Product.Nutrition.Model.Energy
import com.app.recipe.Import.Product.Nutrition.Model.NutritionInformation
import com.app.recipe.Import.Product.Nutrition.Model.NutritionInformation
import com.app.recipe.Import.Product.Units.Model.StandardUnits.Kcal
import com.app.recipe.Import.Product.Units.Model.StandardUnits.Kj
import com.app.recipe.Import.Product.Units.Model.StandardUnits.Units
import com.app.recipe.Log.RecipeLogging
import com.typesafe.scalalogging.LazyLogging

/**
 * Class to find match the energy values from a given line to parse.
 * The line may contain more than one energy levels, which in case will
 * return more than one element.
 */
class MatchEnergy(productString : String) extends RecipeLogging {

  /**
   * Returns the Energy case class with the detailed values as displaying on the web page.
   */
  def getMatch() : List[NutritionInformation] = {

    // Adding all elements to this list which is to be returned last.
    var finalList : List[NutritionInformation] = Nil

    // Interested only in finding the first match.
    val valuesMatch = getFirstMatch(productString)

    // Find out which units to use.
    var energyUnits : Units = null
    if ( isKcal(productString) ) energyUnits = Kcal
    if ( isKj(productString) )   energyUnits = Kj

    // If we have two units, then this is a composite string.
    if ( isKjAndKcal(productString) ) {
      val finalValues = valuesMatch.get.toString()
      var energyValue = 0.0

      // If we still have some units in the value, it means more diligence to parse it.
      if ( isKcal(finalValues) || isKj(finalValues) ) {
        var splitValuesAndUnits = finalValues.split(" ")
        splitValuesAndUnits = (finalValues.replaceAll(" ", "")).split(" ")

        if ( splitValuesAndUnits.size == 1 ) {
          // This is pair composite value separated by / .e.g: 22Kj/22Kcal
          splitValuesAndUnits(0) match {
            case x if x.contains("/") => {
              var splitValues = splitValuesAndUnits(0).split("/")
              splitValues.foreach { value => { finalList = getCompositeElement(value)::finalList } }
            }
            case x if x.contains(",") => {
              var splitValues = splitValuesAndUnits(0).split(",")
              splitValues.foreach { value => { finalList = getCompositeElement(value)::finalList } }
            }
            case x if """\d+|\D+""".r.findAllIn(x).size == 4 => {
              // 1054kJ (253kcal)
              var newX = x.replaceAll("[()]", "")
              var list = """\d+|\D+""".r.findAllIn(newX).toList
              var first = (list(0) + list(1)) 
              finalList = getCompositeElement(first) :: finalList
              var second = list(2) + list(3)
              finalList = getCompositeElement(second) :: finalList
            }
            case _ => finalList = getCompositeElement(finalValues) :: finalList
          }
        }
        // More than two elements if bad omen
        else if ( splitValuesAndUnits.size > 2 ) {
          throw new IllegalStateException("Cannot process composite value with more than two elements: ["+productString+"]")
        }
        else {
          // If the value contains the K letter for either Kcal or Kj, then it is all composite
          if ( splitValuesAndUnits(0).contains("k") || splitValuesAndUnits(0).contains("K") ) {
            splitValuesAndUnits.foreach { string => finalList = getCompositeElement(string) :: finalList }
          }
          else {
            val value = splitValuesAndUnits(0).toString().toDouble
            val units = splitValuesAndUnits(1).toString()
            finalList.+:(Energy(value,getUnitsFromString(units)))
          }
        }
      }
    }
    else {
      if ( valuesMatch.isEmpty ) throw new IllegalStateException("No values found")
      if ( valuesMatch.get.toString().contains("k") ) {
        finalList = getCompositeElement(valuesMatch.get.toString()) :: finalList
      }
      else {
        finalList = Energy(valuesMatch.get.toString().toDouble,energyUnits) :: finalList
      }
    }

    return finalList
  }

  /**
   * Returns the parsed composite element as a NutritionInformation object.
   */
  private final def getCompositeElement(givenString : String) : NutritionInformation = {

    // Allowing string to be cleaned without changing original value.
    var string = givenString
    string = string.replaceAll(" ", "")
    
    // Special cases where a deeper cleanup is required.
    if ( string.contains("(") ) string = """(?<=\()[^\)]*""".r.findFirstIn(givenString).get.toString()

    // In the case that the value and the unit comes together, we do this extra process
    val splitValuesAndUnits = """\d+|\D+""".r.findAllIn(string).toArray[String]

    // If not two elements, we give up at this point.
    if ( splitValuesAndUnits.size != 2 ) 
      throw new IllegalStateException("Unable to split value from units: ["+string+"] from ["+productString+"]")

    var units : Units = null
    if ( isKcal(splitValuesAndUnits(1)) ) units = Kcal
    if ( isKj(splitValuesAndUnits(1)) )   units = Kj

    Energy(splitValuesAndUnits(0).toString().toDouble,units)
  }
  
  /**
   * Matching criteria for energy on a given line. It must match only line that
   * are certain of containing the energy values. By default returns false.
   */
  def isEnergy() : Boolean = {
    productString match {
      case x if x.contains("Energy")           => true
      case x if isKcal(x)                      => true
      case x if isKj(x)                        => true
      case _ => false
    }
  }
  
  /**
   * Getting the first match for processing from the first regex.
   */
  private final def getFirstMatch(string : String) : Option[Match] = {
    // <th scope="row">Energy kJ</th><td>1797</td><td>899</td><td>-</td><td>-</td></tr>
    // Matching: [1797]
    // <th scope="row">Energy</th><td>1633 kJ</td><td>116 kJ</td><td>278 kJ</td><td>8400 kJ</td><td>-</td></tr>
    // Matching: [1633 kJ]
    var valuesRegex = """(?<=td>)(\d+)[^</td]*""".r
    var valueMatched = valuesRegex.findFirstMatchIn(productString)
    if ( valueMatched.isDefined ) return valueMatched

    // <th scope="row">*Reference Intake of an average adult (8400kJ/2000kcal)</th><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td></tr>
    // Matching: [8400kJ/2000kcal]
    // Matching: [8400 kJ / 2000 kcal]
    valuesRegex = """(?<= \()([0-9a-zA-Z \/]+)[^\)</]*""".r
    valueMatched = valuesRegex.findFirstMatchIn(productString)
    if ( valueMatched.isDefined ) return valueMatched
    
    // <th scope="row">*Reference Intake of an average adult 8400kJ / 2000kcal</th><td>-</td><td>-</td><td>-</td></tr></tbody></table
    // Matching: [8400kJ / 2000kcal]
    valuesRegex = """(?<=adult )([0-9a-zA-Z \/]*)[^</th]*""".r
    valueMatched = valuesRegex.findFirstMatchIn(productString)
    if ( valueMatched.isDefined ) return valueMatched
    
    throw new IllegalStateException("Cannot parse value and unit from string: ["+string+"] in line ["+productString+"]")
  }


    
  /**
   * Check if the units to use is Kcal from the supplied line.
   */
  private final def isKcal(string : String) : Boolean = {
    string match {
      case x if x.contains("kcal") => true
      case x if x.contains("Kcal") => true
      case _ => false
    }
  }

  /**
   * Check if the units to use is Kj from the supplied line.
   */
  private final def isKj(string : String) : Boolean = {
    // <th scope="row">Energy</th><td>1633 kJ</td><td>116 kJ</td><td>278 kJ</td><td>8400 kJ</td><td>-</td></tr>
    string match {
      case x if x.contains("kj")   => true
      case x if x.contains("kJ")   => true
      case x if x.contains("KJ")   => true
      case x if x.contains("Kj")   => true
      case _ => false
    }
  }

  /**
   * Check if the units to use are Kj and Kcal together. 
   */
  private final def isKjAndKcal(string : String) : Boolean = {
    if ( isKcal(string) && isKj(string) ) true
    else false
  }
  
  /**
   * When Kj and Kcal come combined, 
   * this method aims to return the value of the Kj only.
   */
  private final def getKj(string : String) : String = {
    // <th scope="row">Energy</th><td>256kJ (61kcal)</td><td>256kJ (61kcal)</td></tr>
    val valueRegex = """(?<=td>)(\d+)[^kJ]*""".r
    var temp = valueRegex.findFirstMatchIn(string)
    if ( temp.isDefined ) return temp.get.toString()

    null
  }
  
  /**
   * When Kj and Kcal come combined, 
   * this method aims to return the value of the Kcal only.
   */
  private final def getKcal(string : String) : String = {
    // <th scope="row">Energy</th><td>256kJ (61kcal)</td><td>256kJ (61kcal)</td></tr>
    val valueRegex = """(?<=\()(\d+)[^kc]*""".r
    var temp = valueRegex.findFirstMatchIn(string)
    if ( temp.isDefined ) return temp.get.toString()

    null
  }

  /**
   * From a string that is meant to have one unit, return it converted to Units enum.
   */
  private final def getUnitsFromString(string : String) : Units = {
    // Find out which units to use.
    var energyUnits : Units = null
    if ( isKcal(productString) ) energyUnits = Kcal
    if ( isKj(productString) )   energyUnits = Kj
    energyUnits
  }
}