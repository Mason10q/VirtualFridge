package ru.virtual.feature_product_list.domain.mappers

import ru.virtual.core_android.Mapper
import ru.virtual.core_db.tables.GroceryListTable
import ru.virtual.feature_product_list.domain.entities.GroceryList

class GroceryListTableMapper: Mapper<GroceryList, GroceryListTable> {

    override fun map(item: GroceryListTable): GroceryList = GroceryList(
        item.id, item.name, item.groceryAmount, item.groceryMarked
    )

}