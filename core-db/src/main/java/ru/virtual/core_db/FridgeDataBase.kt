package ru.virtual.core_db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.virtual.core_db.tables.FridgeProductsTable
import ru.virtual.core_db.tables.GroceryListTable
import ru.virtual.core_db.tables.GroceryTable
import ru.virtual.core_db.tables.ProductTable

@Database(entities = [GroceryListTable::class, GroceryTable::class, ProductTable::class, FridgeProductsTable::class], version = 6, exportSchema = false)
abstract class FridgeDataBase: RoomDatabase() {
    abstract fun getGroceryListDao(): GroceryListDao

    abstract fun getFridgeDao(): FridgeDao

}