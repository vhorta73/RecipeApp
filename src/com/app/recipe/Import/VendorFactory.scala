package com.app.recipe.Import

import com.app.recipe.Import.Vendor.Model.VendorBase
import com.app.recipe.Import.Vendor.TESCO.Tesco
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum._

/**
 * The factory to return all the Vendor objects for query.
 */
object VendorFactory {
  
  def get(vendor : VendorEnum.VendorName) : VendorBase = vendor match {
    case TESCO => Tesco
    case _ => throw new IllegalArgumentException("Vendor not known")
  }
  
}