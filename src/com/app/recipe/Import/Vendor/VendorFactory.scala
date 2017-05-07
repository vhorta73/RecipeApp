package com.app.recipe.Import.Vendor

import com.app.recipe.Import.Vendor.TESCO.Tesco
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum
import com.app.recipe.Import.Vendor.USDA.Usda

/**
 * The factory to return all the Vendor objects for query.
 */
object VendorFactory {
  
  def get(vendor : VendorEnum.VendorName) : VendorBase = vendor match {
    case VendorEnum.TESCO => Tesco
    case VendorEnum.USDA  => Usda
    case _ => throw new IllegalArgumentException("Vendor not known")
  }
  
}