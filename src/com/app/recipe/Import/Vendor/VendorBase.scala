package com.app.recipe.Import.Vendor

import com.app.recipe.Import.Product.Model.ProductBase
import com.app.recipe.Log.RecipeLogging

/**
 * This is the top interface with the methods that all child classes must
 * implement. A new vendor is to extend this interface and add logic to 
 * the required methods.
 */
abstract class VendorBase extends RecipeLogging {}