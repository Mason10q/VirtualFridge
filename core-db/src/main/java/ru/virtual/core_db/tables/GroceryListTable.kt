package ru.virtual.core_db.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("GroceryLists")
data class GroceryListTable(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "groceryAmount", defaultValue = "0")
    val groceryAmount: Int = 0,
    @ColumnInfo(name = "productsMarked", defaultValue = "0")
    val groceryMarked: Int = 0
)