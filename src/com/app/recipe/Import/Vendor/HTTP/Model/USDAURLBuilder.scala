package com.app.recipe.Import.Vendor.HTTP.Model

import com.app.recipe.Import.Vendor.HTTP.HttpBuilder
import com.app.recipe.Import.Vendor.HTTP.USDAHttpListRequestType
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestFormat
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestQueryType
import com.app.recipe.Import.Vendor.HTTP.USDAHttpRequestType
import com.app.recipe.Import.Vendor.HTTP.USDAHttpSortRequestType
import com.typesafe.config.ConfigFactory

import scalaj.http.Http
import scalaj.http.HttpRequest

/**
 * Building a Vendor URL for USDA, the United Stated Department of Agriculture.
 */
object USDAURLBuilder extends HttpBuilder {

  /**
   * The main application configuration file path.
   */
  private final val MAIN_CONFIG_FILE = "configuration/application.conf"
  
  /**
   * The configuration factory loader.
   */
  private final val config = ConfigFactory.load(MAIN_CONFIG_FILE)

  /**
   * Setting the various configuration values.
   */
  private final val USDA_KEY_API       : String = config.getString("usda_key_api")
  private final val USDA_BASE_URL      : String = config.getString("usda_base_url")
  private final val USDA_REPORT_URL    : String = config.getString("usda_report_url")
  private final val USDA_SEARCH_URL    : String = config.getString("usda_search_url")
  private final val USDA_NUTRIENTS_URL : String = config.getString("usda_nutrients_url")
  private final val USDA_LIST_URL      : String = config.getString("usda_list_url")

  /**
   * Give 30 seconds to connect and read as sometimes the api gets slow.
   */
  private final val READ_TIMEOUT : Int = 30000
  private final val CONN_TIMEOUT : Int = 30000

  /**
   * Single point of entry to collect the HttpRequest.
   * @param Vector[Any]
   * @return HttpRequest
   */
  override def get( value : Any ) : HttpRequest = value match {
    // Report
    // https://ndb.nal.usda.gov/ndb/doc/apilist/API-FOOD-REPORT.md
    case (format, USDAHttpRequestQueryType.REPORT, requestType, productId) => {
      Http(USDA_BASE_URL+getRequestType(USDAHttpRequestQueryType.REPORT))
        .header("Content-Type", "application/"+getFormat(format.asInstanceOf[USDAHttpRequestFormat.formatType])) 
        .header("Charset", "UTF-8")
        .timeout(CONN_TIMEOUT, READ_TIMEOUT)
        .param("api_key",USDA_KEY_API)
        .param("format",getFormat(format.asInstanceOf[USDAHttpRequestFormat.formatType]))
        .param("type",getType(requestType.asInstanceOf[USDAHttpRequestType.requestType]))
        .postData(productId.toString())
    }
    // Nutrients
    // https://ndb.nal.usda.gov/ndb/doc/apilist/API-NUTRIENT-REPORT.md
    case (format, USDAHttpRequestQueryType.NUTRIENTS, request) => {
      Http(USDA_BASE_URL+getRequestType(USDAHttpRequestQueryType.NUTRIENTS))
        .header("Content-Type", "application/"+getFormat(format.asInstanceOf[USDAHttpRequestFormat.formatType])) 
        .header("Charset", "UTF-8")
        .timeout(CONN_TIMEOUT, READ_TIMEOUT)
        .param("api_key",USDA_KEY_API)
        .param("format",getFormat(format.asInstanceOf[USDAHttpRequestFormat.formatType]))
        .postData(request.toString())
    }
    // List
    // https://ndb.nal.usda.gov/ndb/doc/apilist/API-LIST.md
    case(format, USDAHttpRequestQueryType.LIST, listType, maxNoItems, offset, sort) => {
      Http(USDA_BASE_URL+getRequestType(USDAHttpRequestQueryType.LIST))
        .header("Content-Type", "application/"+getFormat(format.asInstanceOf[USDAHttpRequestFormat.formatType])) 
        .header("Charset", "UTF-8")
        .timeout(CONN_TIMEOUT, READ_TIMEOUT)
        .param("api_key",USDA_KEY_API)
        .param("lt",getListType(listType.asInstanceOf[USDAHttpListRequestType.listRequestType]))
        .param("maxNoItems",maxNoItems.asInstanceOf[String])
        .param("offset",offset.asInstanceOf[String])
        .param("sort",getSortType(sort.asInstanceOf[USDAHttpSortRequestType.sortRequestType]))
    }
    // Search
    // https://ndb.nal.usda.gov/ndb/doc/apilist/API-SEARCH.md
    case(format, USDAHttpRequestQueryType.SEARCH, request) => {
      Http(USDA_BASE_URL+getRequestType(USDAHttpRequestQueryType.SEARCH))
        .header("Content-Type", "application/"+getFormat(format.asInstanceOf[USDAHttpRequestFormat.formatType])) 
        .header("Charset", "UTF-8")
        .timeout(CONN_TIMEOUT, READ_TIMEOUT)
        .param("api_key",USDA_KEY_API)
        .postData(request.toString())
    }
    case _ => throw new IllegalStateException(s"Do not know $value")
  }
  
  /**
   * The sort type of required to be returned.
   */
  private def getSortType( requestType : USDAHttpSortRequestType.sortRequestType ) : String = requestType match {
    case USDAHttpSortRequestType.NAME => "n"
    case USDAHttpSortRequestType.ID   => "id"
    case _ => throw new IllegalStateException(s"Cannot parse list sort type $requestType")
  }

  /**
   * The type of required to be returned.
   */
  private def getListType( requestType : USDAHttpListRequestType.listRequestType ) : String = requestType match {
    case USDAHttpListRequestType.ALL_NUTRIENTS                   => "n"
    case USDAHttpListRequestType.FOOD                            => "f"
    case USDAHttpListRequestType.FOOD_GROUP                      => "g"
    case USDAHttpListRequestType.SPECIALTY_NUTRIENTS             => "ns"
    case USDAHttpListRequestType.STANDARD_RELEASE_NUTRIENTS_ONLY => "ns"
    case _ => throw new IllegalStateException(s"Cannot parse list type $requestType")
  }

  /**
   * The type required to be returned.
   */
  private def getType( requestType : USDAHttpRequestType.requestType ) : String = requestType match {
    case USDAHttpRequestType.FULL  => "f"
    case USDAHttpRequestType.BASIC => "b"
    case USDAHttpRequestType.STATS => "s"
    case _ => throw new IllegalStateException(s"Cannot parse type $requestType")
  }

  /**
   * Request type converts enum into string url that is to be appended to the 
   * base url on the Http request.
   * 
   */
  private def getRequestType( requestType : USDAHttpRequestQueryType.requestQueryStyle ) : String = requestType match {
    case USDAHttpRequestQueryType.REPORT    => USDA_REPORT_URL
    case USDAHttpRequestQueryType.LIST      => USDA_LIST_URL
    case USDAHttpRequestQueryType.SEARCH    => USDA_SEARCH_URL
    case USDAHttpRequestQueryType.NUTRIENTS => USDA_NUTRIENTS_URL
    case _ => throw new IllegalStateException(s"Cannot parse request type $requestType")
  }
  
  /**
   * Converting the format to String which is then used by the Http Request.
   */
  private def getFormat( format : USDAHttpRequestFormat.formatType ) : String = format match {
    case USDAHttpRequestFormat.JSON => "json"
    case USDAHttpRequestFormat.XML  => "xml"
    case _ => throw new IllegalStateException(s"Cannot parse format $format")
  }
}