package ru.virtual.feature_product_list.domain.mappers

import ru.virtual.core_android.Mapper
import ru.virtual.core_db.tables.GroceryProduct
import ru.virtual.feature_product_list.domain.entities.Grocery

class GroceryTableMapper: Mapper<Grocery, GroceryProduct> {

    override fun map(item: GroceryProduct): Grocery = Grocery(
        item.productId, item.name, item.amount, item.marked
    )
}