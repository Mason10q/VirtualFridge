package ru.virtual.feature_product_list.domain.mappers

import ru.virtual.core_android.Mapper
import ru.virtual.core_network.dto.GroceryDto
import ru.virtual.feature_product_list.domain.entities.Grocery
import javax.inject.Inject

class GroceryDtoMapper @Inject constructor(): Mapper<Grocery, GroceryDto> {

    override fun map(item: GroceryDto): Grocery = Grocery(
        item.productId ?: 0, item.name ?: "", item.amount ?: 0, (item.marked == 1)
    )
}