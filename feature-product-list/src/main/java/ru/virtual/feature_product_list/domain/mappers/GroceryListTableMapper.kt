package ru.virtual.feature_product_list.domain.mappers

import ru.virtual.core_android.Mapper
import ru.virtual.core_db.tables.GroceryListTable
import ru.virtual.feature_product_list.domain.entities.GroceryList
import javax.inject.Inject

class GroceryListTableMapper @Inject constructor(): Mapper<GroceryList, GroceryListTable> {

    override fun map(item: GroceryListTable): GroceryList = GroceryList(
        item.id, item.name, item.groceryAmount, item.groceryMarked
    )

}