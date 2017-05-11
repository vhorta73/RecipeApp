package com.app.recipe.Import.Vendor.USDA.NutrientsReport

import scala.collection.immutable.HashMap

/**
 * The Nutrient details based on https://www.ars.usda.gov nutrient food definitions.
 */
case class FoodGroupDefinition(
    val food_group_id          : String // Unique food group identifier
  , val food_group_description : String // Name of the food group
)

/**
 * The factory to return the respective id for USDA query.
 */
object FoodGroupFactory {

  /**
   * All food group definitions imported from the USDA definitions table.
   */
  private final val HASH : HashMap[String,FoodGroupDefinition] = HashMap(
      "0100" -> FoodGroupDefinition("0100","Dairy and Egg Products")
    , "0200" -> FoodGroupDefinition("0200","Spices and Herbs")
    , "0300" -> FoodGroupDefinition("0300","Baby Foods")
    , "0400" -> FoodGroupDefinition("0400","Fats and Oils")
    , "0500" -> FoodGroupDefinition("0500","Poultry Products")
    , "0600" -> FoodGroupDefinition("0600","Soups, Sauces, and Gravies")
    , "0700" -> FoodGroupDefinition("0700","Sausages and Luncheon Meats")
    , "0800" -> FoodGroupDefinition("0800","Breakfast Cereals")
    , "0900" -> FoodGroupDefinition("0900","Fruits and Fruit Juices")
    , "1000" -> FoodGroupDefinition("1000","Pork Products")
    , "1100" -> FoodGroupDefinition("1100","Vegetables and Vegetable Products")
    , "1200" -> FoodGroupDefinition("1200","Nut and Seed Products")
    , "1300" -> FoodGroupDefinition("1300","Beef Products")
    , "1400" -> FoodGroupDefinition("1400","Beverages")
    , "1500" -> FoodGroupDefinition("1500","Finfish and Shellfish Products")
    , "1600" -> FoodGroupDefinition("1600","Legumes and Legume Products")
    , "1700" -> FoodGroupDefinition("1700","Lamb, Veal, and Game Products")
    , "1800" -> FoodGroupDefinition("1800","Baked Products")
    , "1900" -> FoodGroupDefinition("1900","Sweets")
    , "2000" -> FoodGroupDefinition("2000","Cereal Grains and Pasta")
    , "2100" -> FoodGroupDefinition("2100","Fast Foods")
    , "2200" -> FoodGroupDefinition("2200","Meals, Entrees, and Side Dishes")
    , "2500" -> FoodGroupDefinition("2500","Snacks")
    , "3500" -> FoodGroupDefinition("3500","American Indian/Alaska Native Foods")
    , "3600" -> FoodGroupDefinition("3600","Restaurant Foods")
  )

  /**
   * Returning the Food Group Definition by matching supplied food group Id.
   * 
   * @return FoodGroupDefinition
   */
  def get(foodGroupId : String) : FoodGroupDefinition = {
    val foodGroup = HASH.get(foodGroupId)
    if ( foodGroup.isEmpty ) throw new IllegalStateException(s"No food group found with value [$foodGroupId]")
    foodGroup.get
  }

  /**
   * Receiving an enum of FoodGroupNames, returns the class with all respective
   * food group definitions.
   * 
   * @return FoodGroupDefinition.
   */
  def get(foodGroupName : FoodGroupNames.foodGroupNames ) : FoodGroupDefinition = foodGroupName match {
    case FoodGroupNames.DIARY_AND_EGG_PRODUCTS             => get("0100")
    case FoodGroupNames.SPICES_AND_HERBS                   => get("0200")
    case FoodGroupNames.BABY_FOODS                         => get("0300")
    case FoodGroupNames.FATS_AND_OILS                      => get("0400")
    case FoodGroupNames.POULTRY_PRODUCTS                   => get("0500")
    case FoodGroupNames.SOUPS_SAUCES_AND_GRAVIES           => get("0600")
    case FoodGroupNames.SAUSAGES_AND_LUNCHEON_MEATS        => get("0700")
    case FoodGroupNames.BREAKFAST_CEREALS                  => get("0800")
    case FoodGroupNames.FRUITS_AND_FRUIT_JUICES            => get("0900")
    case FoodGroupNames.PORK_PRODUCTS                      => get("1000")
    case FoodGroupNames.VEGETABLES_AND_VEGETABLES_PRODUCTS => get("1100")
    case FoodGroupNames.NUT_AND_SEED_PRODUCTS              => get("1200")
    case FoodGroupNames.BEEF_PRODUCTS                      => get("1300")
    case FoodGroupNames.BEVERAGES                          => get("1400")
    case FoodGroupNames.FINFISH_AND_SHELLFISH_PRODUCTS     => get("1500")
    case FoodGroupNames.LEGUMES_AND_LEGUMES_PRODUCTS       => get("1600")
    case FoodGroupNames.LAMB_VEAL_AND_GAME_PRODUCTS        => get("1700")
    case FoodGroupNames.SWEETS                             => get("1900")
    case FoodGroupNames.CEREAL_GRAINS_AND_PASTA            => get("2000")
    case FoodGroupNames.FAST_FOODS                         => get("2100")
    case FoodGroupNames.MEAL_ENTREES_AND_SIDE_DISHES       => get("2200")
    case FoodGroupNames.SNACKS                             => get("2500")
    case FoodGroupNames.AMERICAN_IDIAN_ALASKA_NATIVE_FOODS => get("3500")
    case FoodGroupNames.RESTAURANT_FOODS                   => get("3600")
    case _ => throw new IllegalArgumentException("Nutrient not defined")
  }

  /**
   * The full list of food group ids.
   */
  def getList() : List[String] = HASH.keySet.toList
}
