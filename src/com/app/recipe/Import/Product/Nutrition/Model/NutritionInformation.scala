package com.app.recipe.Import.Product.Nutrition.Model

import com.app.recipe.Import.Product.Nutrition.Model.VitaminType.VitType
import com.app.recipe.Import.Product.Units.Model.StandardUnits.Units

/**
 * The typical values that could be found on any product.
 */
trait NutritionInformation 
case class Energy          ( val energyPer100g       : Double, val units       : Units)   extends NutritionInformation { require(units != null       && energyPer100g       >= 0 )}
case class Fat             ( val fatPer100g          : Double, val units       : Units)   extends NutritionInformation { require(units != null       && fatPer100g          >= 0 )}
case class Saturates       ( val saturatePer100g     : Double, val units       : Units)   extends NutritionInformation { require(units != null       && saturatePer100g     >= 0 )}
case class Carbohydrate    ( val carbohydratePer100g : Double, val units       : Units)   extends NutritionInformation { require(units != null       && carbohydratePer100g >= 0 )}
case class Sugars          ( val sugarsPer100g       : Double, val units       : Units)   extends NutritionInformation { require(units != null       && sugarsPer100g       >= 0 )}
case class Salt            ( val saltPer100g         : Double, val units       : Units)   extends NutritionInformation { require(units != null       && saltPer100g         >= 0 )}
case class Fibre           ( val fibrePer100g        : Double, val units       : Units)   extends NutritionInformation { require(units != null       && fibrePer100g        >= 0 )}
case class Protein         ( val proteinPer100g      : Double, val units       : Units)   extends NutritionInformation { require(units != null       && proteinPer100g      >= 0 )}
case class Vitamin         ( val vitaminAmount       : Double, val vitaminType : VitType) extends NutritionInformation { require(vitaminType != null && vitaminAmount       >= 0 )}
case class Thiamin         ( val amountPer100g       : Double, val vitaminType : VitType) extends NutritionInformation { require(vitaminType != null && amountPer100g       >= 0 )}
case class Naicin          ( val amountPer100g       : Double, val units       : Units)   extends NutritionInformation { require(units != null       && amountPer100g       >= 0 )}
case class FolicAcid       ( val amountPer100g       : Double, val units       : Units)   extends NutritionInformation { require(units != null       && amountPer100g       >= 0 )}
case class Biotin          ( val amountPer100g       : Double, val units       : Units)   extends NutritionInformation { require(units != null       && amountPer100g       >= 0 )}
case class PanthothenicAcid( val amountPer100g       : Double, val units       : Units)   extends NutritionInformation { require(units != null       && amountPer100g       >= 0 )}
case class Calcium         ( val amountPer100g       : Double, val units       : Units)   extends NutritionInformation { require(units != null       && amountPer100g       >= 0 )}
case class Iron            ( val amountPer100g       : Double, val units       : Units)   extends NutritionInformation { require(units != null       && amountPer100g       >= 0 )}
case class Zinc            ( val amountPer100g       : Double, val units       : Units)   extends NutritionInformation { require(units != null       && amountPer100g       >= 0 )}
case class Iodine          ( val amountPer100g       : Double, val units       : Units)   extends NutritionInformation { require(units != null       && amountPer100g       >= 0 )}

// TODO: ContainsPeanuts
// TODO: ContainsWheat ...
// OR: Allergens(Peanuts(), Wheat() ... )



