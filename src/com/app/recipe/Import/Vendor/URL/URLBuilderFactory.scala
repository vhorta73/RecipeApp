package com.app.recipe.Import.Vendor.URL

import com.app.recipe.Import.Vendor.URL.Model.OCADOURLBuilder
import com.app.recipe.Import.Vendor.URL.Model.MORRISONSURLBuilder
import com.app.recipe.Import.Vendor.URL.Model.WAITROSEURLBuilder
import com.app.recipe.Import.Vendor.URL.Model.ASDAURLBuilder
import com.app.recipe.Import.Vendor.URL.Model.TESCOURLBuilder
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum
import com.app.recipe.Import.Vendor.URL.Model.URLBuilder
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum._

/**
 * URLBuilder factory to return the built URL for the specific vendor.
 */
object URLBuilderFactory {
  /**
   * Given a known vendor name, returns the respective builder class
   * or gives an exception.
   */
  def get(vendor : VendorEnum.VendorName) : URLBuilder = vendor match {
    case ASDA      => ASDAURLBuilder
    case MORRISONS => MORRISONSURLBuilder
    case OCADO     => OCADOURLBuilder
    case TESCO     => TESCOURLBuilder
    case WAITROSE  => WAITROSEURLBuilder
    case _ => throw new IllegalStateException("Unknown Vendor")
  }
}
