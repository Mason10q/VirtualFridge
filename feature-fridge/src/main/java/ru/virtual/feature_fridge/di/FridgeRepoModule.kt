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
import ru.virtual.feature_fridge.data.FridgeRepositoryImpl
import ru.virtual.feature_fridge.data.NetworkFridgeRepositoryImpl
import ru.virtual.feature_fridge.domain.entities.Product

@Module
class FridgeRepoModule(private val context: Context) {

    val sp = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

    @Provides
    fun provideNetworkGroceryListRepo(
        api: FridgeApi,
        dao: FridgeDao,
        productDtoMapper: Mapper<Product, FridgeProductDto>,
        productTableMapper: Mapper<Product, FridgeProductsTable>
    ): NetworkFridgeRepositoryImpl = NetworkFridgeRepositoryImpl(api, productDtoMapper, sp.getInt("familyId", -1))

    @Provides
    fun provideDbGroceryListRepo(
        dao: FridgeDao,
        productTableMapper: Mapper<Product, FridgeProductsTable>
    ): DbFridgeRepositoryImpl = DbFridgeRepositoryImpl(dao, productTableMapper)


    @Provides
    fun provideGroceryListRepo(
        networkRepository: NetworkFridgeRepositoryImpl,
        dbRepository: DbFridgeRepositoryImpl
    ): FridgeRepository = FridgeRepositoryImpl(dbRepository, networkRepository) {
        sp.getBoolean("online", false)
    }

}