package com.app.recipe.Database

import com.app.recipe.Database.Model.DatabaseMode
import com.app.recipe.Database.SQL.Core.SQLCore
import com.app.recipe.Database.SQL.VendorImport.Tesco.SQLTescoImport
import com.typesafe.config.ConfigFactory

/**
 * The Database Factory will return the handler for the respective required 
 * database. This is decided by the caller based on the Databases types 
 * available.
 */
object DatabaseFactory {

  /**
   * The main application config required to setup the various details, required
   * to get the application working.
   */
  private final val config = ConfigFactory.load(MAIN_CONFIG_FILE)
  private final val MAIN_CONFIG_FILE = "configuration/application.conf"
  private final val STORAGE = "storage"

  def getInstance(mode : DatabaseMode.Mode) : RecipeDatabase = config.getString(STORAGE) match {
    case "SQL" => {
      mode match {
        // Admin database
        case DatabaseMode.ADMIN => throw new IllegalStateException("Not yet implemented")

        // Core database
        case DatabaseMode.CORE => SQLCore

        // Vendor databases
        case DatabaseMode.TESCO_IMPORT => SQLTescoImport
        
        // For the unknown database modes.
        case _ => throw new IllegalStateException("Unknown Mode")
      }
    }
    case _ => throw new IllegalStateException("Unknown Database")
  }
}