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
  val Units, Box, Packages, Tablets = Value

  /**
   * Return the respective unit from a given string.
   */
  def getUnit( unit : String ) : Units = unit.toLowerCase() match {
    case "kj"       => Kj
    case "j"        => j
    case "kcal"     => Kcal
    case "cal"      => cal
    case "l"        => l
    case "ltr"      => l
    case "litre"    => l
    case "litres"   => l
    case "dl"       => dl
    case "cl"       => cl
    case "ml"       => ml
    case "kg"       => Kg
    case "g"        => g
    case "grams"    => g
    case "mg"       => mg
    case "unit"     => Units
    case "pack"     => Packages
    case "pk"       => Packages
    case "tablets"  => Tablets
    case "s"        => Tablets
    case _          => throw new IllegalArgumentException(s"Unknown unit [$unit]")
  }
}