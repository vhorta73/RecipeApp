package com.app.recipe.Import.Vendor.HTTP

import scalaj.http.HttpResponse
import scalaj.http.HttpRequest

/**
 * The HTTP builder interface defining all methods and variables
 * that must be set on all HTTP vendor defined builders.
 */
trait HttpBuilder {

  /**
   * Accepts any value that most matches vendor requirements, and returns
   * a built http request that can then be executed.
   */
  def get(value : Any) : HttpRequest
}