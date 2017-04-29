package com.app.recipe.Database.SQL.Core

/**
 * Listing the methods that all classes must implement to access any table.
 */
abstract trait SQLGlobalMethods[T] {
  /**
   * Method to get one row by id that must be implemented by child classes.
   * 
   * @param id : Int
   * @return Option[TableRow]
   */
  def getRowId( id : Int ) : Option[TableRow]
  
  /**
   * Creates a new record with information supplied. If the record already 
   * exists for the unique key supplied, it will apply an update instead.
   * Returns true if created, false if update and None if no action applied.
   * 
   * In the case an id is not supplied or set to zero, it will and then key
   * on the recipe name and version, updating if existing or creating a new
   * record across the board otherwise.
   * 
   * @param objectA
   * @return Option[List[TableRow]] 
   */
  def saveRecord( objectA : T ) : Option[List[TableRow]]

}