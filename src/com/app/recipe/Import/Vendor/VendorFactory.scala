package com.app.recipe.Import.Vendor

import com.app.recipe.Import.Vendor.URL.Model.VendorEnum
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum._
import com.app.recipe.Import.Vendor.TESCO.Tesco

/**
 * The factory to return all the Vendor objects for query.
 */
object VendorFactory {
  
  def get(vendor : VendorEnum.VendorName) : VendorBase = vendor match {
    case VendorEnum.TESCO => Tesco
    case _ => throw new IllegalArgumentException("Vendor not known")
  }
  
}