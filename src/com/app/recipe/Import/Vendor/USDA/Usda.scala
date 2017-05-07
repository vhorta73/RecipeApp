package com.app.recipe.Import.Vendor.USDA

import com.app.recipe.Import.Product.Model.ProductBase
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum
import com.app.recipe.Import.Vendor.URL.URLBuilderFactory
import com.app.recipe.Import.Vendor.VendorBase
import com.google.gson.Gson
import com.typesafe.config.ConfigFactory

import scalaj.http.Http

/**
 * United States Department of Agriculture vendor access object.
 */
object Usda extends VendorBase {

  /**
   * The static gson to convert objects to JSON and vice-versa.
   */
  final val gson = new Gson

  /**
   * Give 30 seconds to connect and read as sometimes the api gets slow.
   */
  final val readTimeOut : Int = 30000
  final val connTimeOut : Int = 30000
  
  /**
   * The main application config from where the USDA details are found.
   */
  private final val MAIN_CONFIG_FILE = "configuration/application.conf"
  private final val config = ConfigFactory.load(MAIN_CONFIG_FILE)
  private final val KEY_API = "usda_api"
  private final val BASE_URL = "usda_base_url"
  private final val USDA_BASE_URL : String = config.getString(BASE_URL)
  private final val USDA_KEY_API : String = config.getString(KEY_API)
  
  /**
   * Given an USDA ingredient ID, gets the respective ingredient details page,
   * parse it and returns a well defined USDA typical ingredient object.
   */
  override def getProductImport(productId: String) : ProductBase = {
    val usdaUrlObj = URLBuilderFactory.get(VendorEnum.USDA)
    val productUrl = usdaUrlObj.getURLForProductID(productId)
    val usdaJsonId = gson.toJson(USDAId(productId))

    val response   = Http(USDA_BASE_URL)
      .postData(usdaJsonId)
      .header("Content-Type", "application/json")
      .header("Charset", "UTF-8")
      .param("api_key",USDA_KEY_API)
      .timeout(connTimeOut, readTimeOut)
      .asString

    var usda = new USDAResponse
    if ( ! response.code.equals(200) ) {
      error(s"Could not download details for product $productId")
    }
    else {
      val string = response.body
      usda = gson.fromJson(string, usda.getClass)
    }
    usda
  }

  /**
   * From a supplied string, query the vendor web site for all that match,
   * parse all the product id found in all pages, and return a list of all 
   * the parsed product details now converted into product details objects.
   */
  override def search( productName : String ) : List[ProductBase] = {
    // TODO
    null
  }

  case class USDAId( var ndbno : String ) 

}


