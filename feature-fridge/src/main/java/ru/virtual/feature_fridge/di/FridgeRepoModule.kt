package ru.virtual.feature_fridge.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.virtual.core_android.Mapper
import ru.virtual.core_db.FridgeDao
import ru.virtual.core_db.tables.FridgeProductsTable
import ru.virtual.core_network.FridgeApi
import ru.virtual.core_network.dto.FridgeProductDto
import ru.virtual.feature_fridge.data.DbFridgeRepositoryImpl
import ru.virtual.feature_fridge.data.FridgeRepository
import ru.virtual.feature_fridge.data.NetworkFridgeRepositoryImpl
import ru.virtual.feature_fridge.domain.entities.Product

@Module
class FridgeRepoModule(private val context: Context) {

    @Provides
    fun provideGroceryListRepo(
        api: FridgeApi,
        dao: FridgeDao,
        productDtoMapper: Mapper<Product, FridgeProductDto>,
        productTableMapper: Mapper<Product, FridgeProductsTable>
    ): FridgeRepository {
        val sp = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        return if (sp.getBoolean("online", false)) {
            NetworkFridgeRepositoryImpl(api, productDtoMapper, sp.getInt("familyId", -1))
        } else {
            DbFridgeRepositoryImpl(dao, productTableMapper)
        }
    }

}