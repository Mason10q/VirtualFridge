package ru.virtual.feature_product_list.domain.mappers

import ru.virtual.core_android.Mapper
import ru.virtual.core_db.tables.GroceryProduct
import ru.virtual.feature_product_list.domain.entities.Grocery
import javax.inject.Inject

class GroceryTableMapper @Inject constructor(): Mapper<Grocery, GroceryProduct> {

    override fun map(item: GroceryProduct): Grocery = Grocery(
        item.productId, item.name, item.amount, item.marked
    )
}