package ru.virtual.feature_recipes.domain.mappers

import ru.virtual.core_android.Mapper
import ru.virtual.core_network.dto.RecipeProductDto
import ru.virtual.feature_recipes.domain.entities.RecipeProduct
import javax.inject.Inject

class RecipeProductMapper @Inject constructor(): Mapper<RecipeProduct, RecipeProductDto> {

    override fun map(item: RecipeProductDto): RecipeProduct = RecipeProduct(item.productName ?: "", item.amount ?: "", item.isInFridge)

}