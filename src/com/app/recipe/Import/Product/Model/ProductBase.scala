package com.app.recipe.Import.Product.Model

import java.time.LocalDateTime
import java.io.InputStream

/**
 * The trait interface for any Product
 */
trait ProductBase {
  /**
   * The unique product id.
   */
  val id : String
  
  /**
   * The time-stamp for the object creation which can be overridden if required
   *  or it will be set with the object creation value.
   */
  val lastUpdated : LocalDateTime = LocalDateTime.now()
  
}