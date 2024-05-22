package ru.virtual.feature_product_list.domain.mappers

import ru.virtual.core_android.Mapper
import ru.virtual.core_db.tables.GroceryListAmounts
import ru.virtual.core_db.tables.GroceryListTable
import ru.virtual.feature_product_list.domain.entities.GroceryList
import javax.inject.Inject

class GroceryListTableMapper @Inject constructor(): Mapper<GroceryList, GroceryListAmounts> {

    override fun map(item: GroceryListAmounts): GroceryList = GroceryList(
        item.id, item.name, item.groceryAmount, item.groceryMarked
    )

}