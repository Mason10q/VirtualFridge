package ru.virtual.core_db.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "Groceries",
    primaryKeys = ["groceryListId", "productId"],
    foreignKeys = [
        ForeignKey(
            entity = GroceryListTable::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("groceryListId"),
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProductTable::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("productId"),
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class GroceryTable(
    @ColumnInfo(name = "groceryListId")
    val groceryListId: Int,
    @ColumnInfo(name = "productId")
    val productId: Int,
    @ColumnInfo(name = "amount", defaultValue = "1")
    val amount: Int = 1,
    @ColumnInfo(name = "marked", defaultValue = "false")
    val marked: Boolean = false
)