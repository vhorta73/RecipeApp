package com.app.recipe.Import.Vendor.HTTP

import com.app.recipe.Import.Vendor.URL.Model.VendorEnum
import com.app.recipe.Import.Vendor.HTTP.Model.USDAURLBuilder

/**
 * Http Builder factory to return the built Http response for a specific vendor.
 */
object HttpBuilderFactory {
  /**
   * Given a known vendor name, returns the respective builder class
   * or gives an exception.
   */
  def get(vendor : VendorEnum.VendorName) : HttpBuilder = vendor match {
    case v if v.equals(VendorEnum.USDA)      => USDAURLBuilder
    case _ => throw new IllegalStateException("Unknown Vendor")
  }
}
