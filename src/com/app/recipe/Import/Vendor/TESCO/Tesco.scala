package com.app.recipe.Import.Vendor.TESCO

import java.net.URL
import java.util.Currency
import java.util.Locale

import com.app.recipe.Database.DatabaseFactory
import com.app.recipe.Database.SQL.VendorImport.RecipeDatabaseVendorImport
import com.app.recipe.Database.Model.DatabaseMode
import com.app.recipe.Database.RecipeDatabase
import com.app.recipe.Import.Vendor.URL.URLBuilderFactory
import com.app.recipe.Import.Product.Model.ProductImport
import com.app.recipe.Import.Product.Model.ProductImport
import com.app.recipe.Import.Product.Nutrition.Model.NutritionInformation
import com.app.recipe.Import.Product.Nutrition.Model.NutritionInformation
import com.app.recipe.Import.Product.Nutrition.Model.NutritionInformation
import com.app.recipe.Import.Product.Units.Model.StandardUnits._
import com.app.recipe.Import.Product.Units.Model.StandardUnits
import com.app.recipe.Import.Vendor.VendorBase
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.MatchHalal
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.MatchHalal
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.MatchHalal
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.MatchName
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.MatchNutritionInformation
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum._
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.MatchSuitableForVegetarians
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.MatchSmallImage
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.MatchExtraLargeImage
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.MatchSmallImage
import com.app.recipe.Import.Vendor.TESCO.ProductMatch.MatchPrice


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
  private final val TITLE_NOT_FOUND_TEXT = "Title not found"

  /**
   * List of private methods to help with matching the several product fields.
   */
  private final def getName(string : String) : String = new MatchName(string).getMatch()
  private final def getNutritionInformation(string : String) : List[NutritionInformation] = new MatchNutritionInformation(string).getMatch()
  private final def getHalal(string : String) : Boolean = new MatchHalal(string).getMatch()
  private final def getSuitableForVegetarians(string : String) : Boolean = new MatchSuitableForVegetarians(string).getMatch()
  private final def getPrice(string : String) : (Double, Currency) = new MatchPrice(string).getMatch()
  private final def getSmallImage(string : String) : String = new MatchSmallImage(string).getMatch()
  private final def getExtraLargeImage(string : String) : String = new MatchExtraLargeImage(string).getMatch()

  /**
   * Given a product ID, gets the respective product details page, and parse it to 
   * return a completed product details object.
   */
  override def getProductDetails(productId: String) : ProductImport = {
    val tescoUrlObj           = URLBuilderFactory.get(TESCO)
    val productUrl            = tescoUrlObj.getURLForProductID(productId)
    val webProductDetailsPage = scala.io.Source.fromURL(productUrl)("UTF-8")

    if ( webProductDetailsPage.isEmpty ) {
      info("Product Details page for product ID: " + productId + " returned empty.")
      return null
    }

    var productString = ""
    for( line <- webProductDetailsPage ) productString += line
    var productStr = productString.replaceAll("\n", "")

    // The price is a vector for (Double, Currency)
    val price                    = getPrice(productString)
//    val pricePerUnit             = _getPricePerUnit(productString)
//    val description              = _getDescription(productString)

    // Building up the product to be returned.
    ProductImport( 
       id                       = productId                                // The vendor product Id .  
      ,title                    = getName(productString)                   // The parsed vendor product name.
      ,vendor                   = VendorEnum.TESCO                         // This vendor name.
      ,isHalal                  = getHalal(productString)                  // Halal indicator.
      ,isSuitableForVegetarians = getSuitableForVegetarians(productString) // Suitable for vegetarians indicator.
//      ,amount = 0.0                              // Product amount.
//      ,amountUnit = StandardUnits.cal                // Amount units.
      ,price = price._1                                                    // Product price
      ,ccy = price._2                                                      // Product price currency.
//      ,basePrice = 0.0                              // Product base price.
//      ,baseCcy = Currency.getInstance(Locale.UK)          // Product base price currency.
//      ,baseValue = 0.0                              // Product base value amount for base price.
//      ,baseUnit = StandardUnits.Kg                 // Product base value units.
//      ,nutrition = getNutritionInformation(productString)                  // Nutrition details
      ,productUrl = productUrl + ""                                        // Product details url.
      ,smallImgUrl = getSmallImage(productString)                          // Product small image representation.
      ,largeImgUrl = getExtraLargeImage(productString)                     // Extra large image representation.
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

        val productDetails = getProductDetails(product_id)
        if ( productDetails != null ) {
          finalProductList = getProductDetails(product_id) :: finalProductList 
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
  
  private final def _getPricePerUnit(productString : String) : (Double, Currency, Double, Units) = {
    // regex for retrieving the ingredient price
    // <span class="linePriceAbbr">(Â£0.70/100g)</span>
    val priceRegex = """(?<=<span class="linePriceAbbr">\(Â)[^\)<]*""".r
    val unitValueRegex = """([0-9.]+)[^a-z-A-Z]*""".r
    val unitNameRegex = """[a-z-A-Z]+""".r

    val price = priceRegex.findFirstMatchIn(productString).getOrElse("").toString()
    val priceList = price.split("/")
    val price_1 = priceList(0)
    val price_2 = priceList(1)
    var priceValue : Double = price_1.substring(1,price_1.length()-1).toDouble
    var priceCcy   : Currency = Currency.getInstance(Locale.UK)
    var unitValue  : Double = unitValueRegex.findFirstMatchIn(price_2).get.toString.toDouble
    var unitName   : Units = StandardUnits.getUnit(unitNameRegex.findFirstMatchIn(price_2).get.toString())
    ( priceValue, priceCcy, unitValue, unitName )
  }
}
