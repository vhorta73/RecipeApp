package com.app.recipe.Import.Product.Units.Model

/**
 * Standard units to be used across the system.
 */
object StandardUnits extends Enumeration {
  // Defining the type Units.
  type Units = Value

  // Joules
  val Kj, j = Value
  // Calories
  val Kcal, cal = Value
  // Liquids
  val l, dl, cl, ml = Value
  // Weight
  val Kg, g, mg, ug = Value
  // Other
  val UNIT = Value

  def getUnit( unit : String ) : Units = unit match {
    case "Kj"   => Kj
    case "j"    => j
    case "Kcal" => Kcal
    case "cal"  => cal
    case "l"    => l
    case "dl"   => dl
    case "cl"   => cl
    case "ml"   => ml
    case "Kg"   => Kg
    case "g"    => g
    case "mg"   => mg
    case "unit" => UNIT
    case _      => throw new IllegalArgumentException("Unknown unit")
  }
}