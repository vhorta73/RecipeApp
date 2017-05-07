package com.app.recipe.Import.Vendor.TESCO

import java.net.URL
import java.util.Currency

import scala.io.Codec.string2codec

import com.app.recipe.Database.DatabaseFactory
import com.app.recipe.Database.Model.DatabaseMode
import com.app.recipe.Database.RecipeDatabase
import com.app.recipe.Database.RecipeDatabaseVendorImport
import com.app.recipe.Import.Product.Model.ProductDetails
import com.app.recipe.Import.Product.Model.ProductImport
import com.app.recipe.Import.Product.Units.Model.StandardUnits.Units
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.MatchExtraLargeImage
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.MatchHalal
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.MatchIsAvailable
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.MatchIsOnOffer
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.MatchName
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.MatchPrice
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.MatchPricePerUnit
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.MatchProductDetails
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.MatchQuantity
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.MatchSmallImage
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.MatchSuitableForVegetarians
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum.TESCO
import com.app.recipe.Import.Vendor.URL.URLBuilderFactory
import com.app.recipe.Import.Vendor.VendorBase


object Tesco extends VendorBase {

  /**
   * The maximum products allowed to be found in one search.
   */
  private final val MAX_PRODUCT_PER_SEARCH = 10000;
  
  /**
   * The default search page increment.
   */
  private final val SEARCH_PRODUCT_INCREMENT = 20;
  
  /**
   * The search start value.
   */
  private final val START_SEARCH_AMOUNT = 19;

  /**
   * The string found before each product id.
   */
  private final val PRODUCT_ID_PREFIX_STRING = "product_"

  /**
   * The prefix string found before data product ids.
   */
  private final val DATA_PRODUCT_ID_PREFIX_STRING = "data-product-id"

  /**
   * The string that identifies in a search that no products were found.
   */
  private final val STRING_IDENTIFIER_FOR_NO_PRODUCTS_FOUND = "Sorry, we didn't find any products to match"
  
  /**
   * Text used on a product for which the title was not found.
   */
  private final val TITLE_NOT_FOUND_TEXT : String = "Title not found"

  /**
   * Instantiate the matching objects once.
   */
  private final val matchQuantity = new MatchQuantity()
  private final val matchName = new MatchName()
  private final val matchPrice = new MatchPrice()
  private final val matchProductDetails = new MatchProductDetails()
  private final val matchHalal = new MatchHalal()
  private final val matchSuitableForVegetarians = new MatchSuitableForVegetarians()
  private final val matchIsOnOffer = new MatchIsOnOffer()
  private final val matchIsAvailable = new MatchIsAvailable()
  private final val matchPricePerUnit = new MatchPricePerUnit()
  private final val matchSmallImage = new MatchSmallImage()
  private final val matchExtraLargeImage = new MatchExtraLargeImage()
  
  /**
   * List of private methods to help with matching the several product fields.
   */
  private final def getName(string : String)                   : String = matchName.getMatch(string)
  private final def getProductDetails(string : String)         : List[ProductDetails] = matchProductDetails.getMatch(string)
  private final def getHalal(string : String)                  : Boolean = matchHalal.getMatch(string)
  private final def getSuitableForVegetarians(string : String) : Boolean = matchSuitableForVegetarians.getMatch(string)
  private final def getIsOnOffer(string : String)              : Boolean = matchIsOnOffer.getMatch(string)
  private final def getIsAvailable(string : String)            : Boolean = matchIsAvailable.getMatch(string)
  private final def getPrice(string : String)                  :(Double, Currency) = matchPrice.getMatch(string)
  private final def getPricePerUnit(string : String)           :(Double, Currency, Double, Units) = matchPricePerUnit.getMatch(string)
  private final def getQuantity(string : String)               :(Double, Units) = matchQuantity.getMatch(string)
  private final def getSmallImage(string : String)             : String = matchSmallImage.getMatch(string)
  private final def getExtraLargeImage(string : String)        : String = matchExtraLargeImage.getMatch(string)

  /**
   * Given a product ID, gets the respective product details page, and parse it to 
   * return a completed product details object.
   */
  override def getProductImport(productId: String) : ProductImport = {
    val tescoUrlObj           = URLBuilderFactory.get(TESCO)
    val productUrl            = tescoUrlObj.getURLForProductID(productId)
    val webProductDetailsPage = scala.io.Source.fromURL(productUrl)("UTF-8")

    if ( webProductDetailsPage.isEmpty ) {
      warn("Product Details page for product ID: " + productId + " returned empty.")
      return ProductImport(id = productId, title = TITLE_NOT_FOUND_TEXT, vendor = VendorEnum.TESCO)
    }

    var productString = ""
    for( line <- webProductDetailsPage ) productString += line
    var productStr = productString.replaceAll("\n", "")

    // The price is a vector for (Double, Currency)
    val price                    = getPrice(productString)
    val pricePerUnit             = getPricePerUnit(productString)
    val quantity                 = getQuantity(productString)

    // Building up the product to be returned.
    ProductImport( 
       id                       = productId                                // The vendor product Id .  
      ,title                    = getName(productString)                   // The parsed vendor product name.
      ,vendor                   = VendorEnum.TESCO                         // This vendor name.
      ,isHalal                  = getHalal(productString)                  // Halal indicator.
      ,isSuitableForVegetarians = getSuitableForVegetarians(productString) // Suitable for vegetarians indicator.
      ,isOnOffer                = getIsOnOffer(productString)              // Check if the product is on offer.
      ,isAvailable              = getIsAvailable(productString)            // Check if the product is available.
      ,amount                   = quantity._1                              // Product amount.
      ,amountUnit               = quantity._2                              // Amount units.
      ,price                    = price._1                                 // Product price
      ,ccy                      = price._2                                 // Product price currency.
      ,basePrice                = pricePerUnit._1                          // Product base price.
      ,baseCcy                  = pricePerUnit._2                          // Product base price currency.
      ,baseValue                = pricePerUnit._3                          // Product base value amount for base price.
      ,baseUnit                 = pricePerUnit._4                          // Product base value units.
      ,details                  = getProductDetails(productString)         // Product details
      ,productUrl               = productUrl + ""                          // Product details url.
      ,smallImgUrl              = getSmallImage(productString)             // Product small image representation.
      ,largeImgUrl              = getExtraLargeImage(productString)        // Extra large image representation.
    )
  }

  /**
   * From a supplied string, query the vendor web site for all that match,
   * parse all the product id found in all pages, and return a list of all 
   * the parsed product details now converted into product details objects.
   */
  override def search( productName : String ) : List[ProductImport] = {
    val tescoUrlObj = URLBuilderFactory.get(TESCO)
    val searchUrl   = tescoUrlObj.getURLForSerach(productName)

    // The product id list found in this search.
    var productIds : List[String] = _getProductIds(searchUrl)

    // If no ids found, nothing to be returned.
    if ( productIds.size == 0 ) {
      List()
    }
    else {
      info("Found: "+productIds.size+" products")
      var finalProductList : List[ProductImport] = List()
val dbObj : RecipeDatabase = DatabaseFactory.getInstance(DatabaseMode.TESCO_IMPORT)
      
      // The parsing of the products, require to call the web product page for each.
      productIds.foreach { product_id => {
        info("Parsing ["+product_id+"]: "+finalProductList.size+" of "+productIds.size+" products")

        val productDetails = getProductImport(product_id)
        if ( productDetails != null ) {
          finalProductList = getProductImport(product_id) :: finalProductList 
dbObj.asInstanceOf[RecipeDatabaseVendorImport].importProducts(List(productDetails))
        }
        else {
          info("Product id: " + product_id + " returned no details")
        }
      } }
      
      info("Parsed a total of "+finalProductList.size+" products")
      finalProductList
    }
  }

  /**
   * Get all product ids from a search.
   */
  private def _getProductIds(searchUrl : URL) : List[String] = {

    // The product id list to return.
    var productIdList : List[String] = List()

    // Previous amount of productIds found to check against current and exit if no change.
    var previousAmount : Int = 0

    // Start with the original url page zero and increase page then.
    // The search for Tesco, converts any spaces to +
    var webPage = scala.io.Source.fromURL(searchUrl.toString().replaceAll(" ","+"))
      
    // Web pages from search only show first 20 products max. The rest are recommendations.
    // Nao starts with 19 and increase 20 each time until no more products are found.
    for ( nao <- START_SEARCH_AMOUNT.to(MAX_PRODUCT_PER_SEARCH).by(SEARCH_PRODUCT_INCREMENT) ) {

      var lines : String = ""
      // Filtering the lines we are interested in
      if ( ! webPage.getLines().isEmpty ) {
        val res = webPage.getLines().filter { line => 
          line.contains(PRODUCT_ID_PREFIX_STRING) && line.contains(DATA_PRODUCT_ID_PREFIX_STRING) && 
          !line.contains(STRING_IDENTIFIER_FOR_NO_PRODUCTS_FOUND)
        }
        try {
          lines = res.mkString
        } catch {
          case e : Throwable => lines = ""
        }
      }

      // Split lines by product such that the id would be at the start of each line,
      // ready to be captured
      val splitLinesByProductId = lines.split("product_")      
      val productIdRegex = """^(\d+)""".r
      var recommendations = false
      var count = 0
      // Go over each line to add the captured product id to the final list
      // and skip any product ids that are from other parts of the page
      // that are not related to the original search, like recommendations.
      splitLinesByProductId.foreach { line =>
        for( productId <- productIdRegex.findFirstIn(line)) { 
          if ( !recommendations ) {
            productIdList = productId :: productIdList; 
            count = count + 1
          }
        }
        
        // If a line contains track url click, it is part of a recommendation.
        if ( line.contains("data-clicktrackurl") ) recommendations = true
      }

      // If no changes, return, else, update the previous amount.
      if ( previousAmount == productIdList.size ) return productIdList
      previousAmount = productIdList.size

      info(s"Captured ${productIdList.size} products, for Tesco web page "+nao)

      // Get the next page before starting the look again.
      webPage = scala.io.Source.fromURL(searchUrl+"&newSort=true&N=0&Nao="+nao)
    }

    // This should never happen, and is here just in case.
    throw new IllegalStateException("Too many products to search")
  }  
}
