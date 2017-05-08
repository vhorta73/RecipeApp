package com.app.recipe.Import.Vendor.URL

import com.app.recipe.Import.Vendor.URL.Model.ASDAURLBuilder
import com.app.recipe.Import.Vendor.URL.Model.MORRISONSURLBuilder
import com.app.recipe.Import.Vendor.URL.Model.OCADOURLBuilder
import com.app.recipe.Import.Vendor.URL.Model.TESCOURLBuilder
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum._
import com.app.recipe.Import.Vendor.URL.Model.WAITROSEURLBuilder

/**
 * URLBuilder factory to return the built URL for the specific vendor.
 */
object URLBuilderFactory {
  /**
   * Given a known vendor name, returns the respective builder class
   * or gives an exception.
   */
  def get(vendor : VendorEnum.VendorName) : URLBuilder = vendor match {
    case v if v.equals(VendorEnum.ASDA)      => ASDAURLBuilder
    case v if v.equals(VendorEnum.MORRISONS) => MORRISONSURLBuilder
    case v if v.equals(VendorEnum.OCADO)     => OCADOURLBuilder
    case v if v.equals(VendorEnum.TESCO)     => TESCOURLBuilder
    case v if v.equals(VendorEnum.WAITROSE)  => WAITROSEURLBuilder
    case _ => throw new IllegalStateException("Unknown Vendor")
  }
}
