package com.app.recipe.Import.Vendor.URL.Model

/**
 * Defining the vendor names to be used when calling for the logic
 * which is linked to them.
 */
object VendorEnum extends Enumeration {
  type VendorName = Value

  // List of all known Vendors
  val ASDA, 
      MORRISONS, 
      OCADO, 
      TESCO, 
      USDA,      // The Unites States Department of Agriculture.
      WAITROSE 
    = Value
}
 