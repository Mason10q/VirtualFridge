package ru.virtual.feature_fridge.domain.mappers

import ru.virtual.core_android.Mapper
import ru.virtual.core_network.dto.FridgeProductDto
import ru.virtual.feature_fridge.domain.entities.Product
import javax.inject.Inject

class ProductDtoMapper @Inject constructor(): Mapper<Product, FridgeProductDto> {

    override fun map(item: FridgeProductDto): Product = Product(item.id ?: -1, item.name ?: "", item.productId ?: -1)
}