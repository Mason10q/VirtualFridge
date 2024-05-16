package ru.virtual.feature_product_list.domain.mappers

import ru.virtual.core_android.Mapper
import ru.virtual.core_network.dto.GroceryListDto
import ru.virtual.feature_product_list.domain.entities.Grocery
import ru.virtual.feature_product_list.domain.entities.GroceryList

class GroceryListDtoMapper: Mapper<GroceryList, GroceryListDto> {

    override fun map(item: GroceryListDto): GroceryList = GroceryList(
        item.id ?: 0, item.name ?: "", item.productsAmount ?: 0, item.productsMarked ?: 0
    )
}