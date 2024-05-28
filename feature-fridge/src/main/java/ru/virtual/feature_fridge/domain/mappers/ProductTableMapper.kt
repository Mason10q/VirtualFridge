package ru.virtual.feature_fridge.domain.mappers

import ru.virtual.core_android.Mapper
import ru.virtual.core_db.tables.FridgeProductsTable
import ru.virtual.feature_fridge.domain.entities.Product
import javax.inject.Inject

class ProductTableMapper @Inject constructor(): Mapper<Product, FridgeProductsTable> {

    override fun map(item: FridgeProductsTable): Product = Product(-1, item.productName, item.id)
}