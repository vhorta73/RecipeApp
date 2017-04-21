package com.app.recipe.Database.SQL.VendorImport.Tesco

import com.app.recipe.Database.SQL.SQLDatabaseHandle
import com.app.recipe.Database.SQL.VendorImport.SQLRecipeDatabaseVendorImport
import com.app.recipe.Import.Product.Model.ProductImport
import com.app.recipe.Log.RecipeLogging

/**
 * This object implements the defined trait SQL methods and connects to the
 * SQL database to apply required operations.
 */
object SQLTescoImport extends SQLRecipeDatabaseVendorImport with RecipeLogging {
  
  /**
   * Prefix table name for Tesco.
   */
  private final val TABLE_PREFIX : String = "tesco_"
  
  override def getVendorImportProductTableBaseName() : String = TABLE_PREFIX + super.getVendorImportProductTableBaseName()

  /**
   * Given a product, if a product for same product_id and imported_date already
   * exists, details get updated, else the product gets inserted as new.
   */
  final def importProduct( product : ProductImport ) : Unit = {
    val product_id = product.id
    val importDate = product.lastUpdated

    if ( hasProduct(product) ) {
      info(s"UPDATE [$product_id $importDate]")
      updateProduct(product)
    }
    else {
      info(s"INSERT [$product_id $importDate]")
      insertProduct(product) 
    }
  }

  /**
   * Updating details on an exiting product.
   */
  final def updateProduct( product : ProductImport ) : Unit = {
    // Prepare the statement with placeholder to complete per loop.
    val statement = SQLDatabaseHandle.getSQLHandle().prepareStatement("UPDATE " + 
        getImportRecipeDatabaseName() + "." + getVendorImportProductTableBaseName() +
        " SET title = ?,          halal = ?,           vegetarian = ?,  offer = ?,   available = ?,   " +
        "    amount = ?,          amount_unit = ?,     price = ?,       ccy = ?,     base_price = ?,  " +
        "    base_ccy = ?,        base_value = ?,      base_unit = ?,   details = ?, product_url = ?, " + 
        "    image_small_url = ?, image_large_url = ?, last_updated = NOW()" +
        "WHERE product_id = ? AND imported_date = DATE(?) " 
    )

    statement.setString(1, product.title)
    statement.setString(2, if ( product.isHalal ) "Y" else "N" )
    statement.setString(3, if ( product.isSuitableForVegetarians ) "Y" else "N" )
    statement.setString(4, if ( product.isOnOffer ) "Y" else "N" )
    statement.setString(5, if ( product.isAvailable ) "Y" else "N" )
    statement.setDouble(6, product.amount)
    statement.setString(7, product.amountUnit.toString())
    statement.setDouble(8, product.price)
    statement.setString(9, product.ccy.toString())
    statement.setDouble(10, product.basePrice)
    statement.setString(11, product.baseCcy.toString())
    statement.setDouble(12, product.baseValue)
    statement.setString(13, product.baseUnit.toString())
    statement.setString(14, product.details.toList.toString())
    statement.setString(15, product.productUrl)
    statement.setString(16, product.smallImgUrl)
    statement.setString(17, product.largeImgUrl)
    statement.setString(18, product.id)
    statement.setString(19, product.importedDate.toString())
    statement.execute()
  }

  /**
   * Inserting a product to the database.
   */
  final def insertProduct( product : ProductImport ) : Unit = {
    // Prepare the statement with placeholder to complete per loop.
    val statement = SQLDatabaseHandle.getSQLHandle().prepareStatement("INSERT INTO " + 
        getImportRecipeDatabaseName() + "." + getVendorImportProductTableBaseName() + " ( " +
        "`product_id`,`title`,`halal`,`vegetarian`,`offer`,`available`,`amount`,`amount_unit`, " + 
        "`price`,`ccy`,`base_price`,`base_ccy`,`base_value`,`base_unit`, " + 
        "`details`,`product_url`,`image_small_url`,`image_large_url`,`imported_date` " + 
        " ) "+
      " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )"
    )

    statement.setString(1, product.id)
    statement.setString(2, product.title)
    statement.setString(3, if ( product.isHalal ) "Y" else "N" )
    statement.setString(4, if ( product.isSuitableForVegetarians ) "Y" else "N" )
    statement.setString(5, if ( product.isOnOffer ) "Y" else "N" )
    statement.setString(6, if ( product.isAvailable ) "Y" else "N" )
    statement.setDouble(7, product.amount)
    statement.setString(8, product.amountUnit.toString())
    statement.setDouble(9, product.price)
    statement.setString(10, product.ccy.toString())
    statement.setDouble(11, product.basePrice)
    statement.setString(12, product.baseCcy.toString())
    statement.setDouble(13, product.baseValue)
    statement.setString(14, product.baseUnit.toString())
    statement.setString(15, product.details.toList.toString())
    statement.setString(16, product.productUrl)
    statement.setString(17, product.smallImgUrl)
    statement.setString(18, product.largeImgUrl)
    statement.setString(19, product.importedDate.toString())
    statement.execute()
  }

  /**
   * Check if the database already has this product keyed on product_id and 
   * imported_date, and return true if yes, false otherwise.
   */
  private final def hasProduct(product : ProductImport) : Boolean = {
    val product_id = product.id
    val importDate = product.importedDate
    val getSavedProductStatement = SQLDatabaseHandle.getSQLHandle().createStatement()

    val resultSet = getSavedProductStatement.executeQuery(
        "SELECT product_id, imported_date " + 
        "FROM " + getImportRecipeDatabaseName() + "." + getVendorImportProductTableBaseName() + " " +
        "WHERE product_id = '" + product_id + "' AND imported_date = DATE('" + importDate + "') "
    )

    if ( resultSet.next() ) {
      val product_id    = resultSet.getString(1)
      val imported_date = resultSet.getDate(2)
      info(s"Found product id $product_id date $imported_date")
      true
    }
    else {
      false
    }
  }
}