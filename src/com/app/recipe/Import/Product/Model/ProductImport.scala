package com.app.recipe.Import.Product.Model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Currency
import java.util.Locale

import com.app.recipe.Import.Product.Units.Model.StandardUnits
import com.app.recipe.Import.Vendor.URL.Model.VendorEnum

/**
 * The trait interface for products of type import. These products are aimed 
 * to be stored as imports from a specific vendor. The information stored is
 * raw and not ready for the application usual processes. 
 * 
 * Fields in ProductImport case classes are subject to change from vendor to 
 * vendor, depending on the data available. This data is aimed to capture as
 * much information as possible that could be later used for many functions.
 * 
 * @author Vasco
 */
case class ProductImport ( 
    override val id               : String
    ,val title                    : String
    ,val vendor                   : VendorEnum.VendorName
    ,val isHalal                  : Boolean = false
    ,val isSuitableForVegetarians : Boolean = false
    ,val amount                   : Double  = 0.0
    ,val amountUnit               : StandardUnits.Units = StandardUnits.UNIT
    ,val price                    : Double = 0.0
    ,val ccy                      : Currency = Currency.getInstance(Locale.UK)
    ,val basePrice                : Double = 0.0
    ,val baseCcy                  : Currency = Currency.getInstance(Locale.UK)
    ,val baseValue                : Double = 0.0
    ,val baseUnit                 : StandardUnits.Units = StandardUnits.UNIT
    ,val details                  : List[ProductDetails] = Nil
    ,val productUrl               : String = ""
    ,val smallImgUrl              : String = ""
    ,val largeImgUrl              : String = ""
    ,val importedDate             : LocalDateTime = LocalDateTime.now()
    ) extends ProductBase {

  /**
   * Printing the Ingredient object for debugging purposes.
   */
  override def toString() : String = 
   s"Product ID                 \t: $id" +
   s"\nTitle                    \t: $title" + 
   s"\nVendor                   \t: $vendor" + 
   s"\nHalal                    \t: $isHalal" + 
   s"\nSuitable for Vegeratians \t: $isSuitableForVegetarians" + 
   s"\nAmount                   \t: $amount" + 
   s"\nAmount Units             \t: $amountUnit" + 
   f"\nPrice                    \t: $price%.2f" + 
   s"\nCcy                      \t: $ccy" + 
   f"\nBase Price               \t: $basePrice%.2f" + 
   s"\nBase Ccy                 \t: $baseCcy" + 
   s"\nBase Value               \t: $baseValue" + 
   s"\nBase Units               \t: $baseUnit" + 
    "\nDetails                  \t: " + details.map { details => ("\n\t"+ details) } + 
   s"\nProduct Url              \t: $productUrl" + 
   s"\nSmall Image Url          \t: $smallImgUrl" + 
   s"\nLarge Image Url          \t: $largeImgUrl" + 
    "\nLast Updated             \t: " + lastUpdated.format(DateTimeFormatter.BASIC_ISO_DATE)
                                     + lastUpdated.getHour + lastUpdated.getMinute + lastUpdated.getSecond
}