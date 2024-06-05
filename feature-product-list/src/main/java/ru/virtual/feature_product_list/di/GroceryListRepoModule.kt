package ru.virtual.feature_product_list.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import ru.virtual.core_android.Mapper
import ru.virtual.core_db.FridgeDao
import ru.virtual.core_db.GroceryListDao
import ru.virtual.core_db.tables.GroceryListAmounts
import ru.virtual.core_db.tables.GroceryProduct
import ru.virtual.core_network.FridgeApi
import ru.virtual.core_network.GroceryListApi
import ru.virtual.core_network.dto.GroceryDto
import ru.virtual.core_network.dto.GroceryListDto
import ru.virtual.feature_product_list.data.db.DbGroceryListRepositoryImpl
import ru.virtual.feature_product_list.data.FridgeRepository
import ru.virtual.feature_product_list.data.FridgeRepositoryImpl
import ru.virtual.feature_product_list.data.db.DbFridgeRepositoryImpl
import ru.virtual.feature_product_list.data.GroceryListRepository
import ru.virtual.feature_product_list.data.GroceryListRepositoryImpl
import ru.virtual.feature_product_list.data.network.NetworkFridgeRepositoryImpl
import ru.virtual.feature_product_list.data.network.NetworkGroceryListRepositoryImpl
import ru.virtual.feature_product_list.domain.entities.Grocery
import ru.virtual.feature_product_list.domain.entities.GroceryList

@Module
class GroceryListRepoModule(private val context: Context) {

    private val sp: SharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

    @Provides
    fun provideNetworkGroceryListRepository(
        api: GroceryListApi,
        groceryDtoMapper: Mapper<Grocery, GroceryDto>,
        groceryListDtoMapper: Mapper<GroceryList, GroceryListDto>,
    ): NetworkGroceryListRepositoryImpl =
        NetworkGroceryListRepositoryImpl(api, groceryListDtoMapper, groceryDtoMapper, sp.getInt("familyId", -1))

    @Provides
    fun provideDbGroceryListRepository(
        dao: GroceryListDao,
        groceryTableMapper: Mapper<Grocery, GroceryProduct>,
        groceryListTableMapper: Mapper<GroceryList, GroceryListAmounts>
    ): DbGroceryListRepositoryImpl =
        DbGroceryListRepositoryImpl(dao, groceryListTableMapper, groceryTableMapper)


    @Provides
    fun provideDbFridgeRepository(dao: FridgeDao): DbFridgeRepositoryImpl = DbFridgeRepositoryImpl(dao)

    @Provides
    fun provideNetworkFridgeRepository(api: FridgeApi): NetworkFridgeRepositoryImpl =
        NetworkFridgeRepositoryImpl(api, sp.getInt("familyId", -1))


    @Provides
    fun provideGroceryListRepository(
        networkRepository: NetworkGroceryListRepositoryImpl,
        dbRepository: DbGroceryListRepositoryImpl
    ): GroceryListRepository =
        GroceryListRepositoryImpl(networkRepository, dbRepository) {
            sp.getBoolean("online", false)
        }

    @Provides
    fun provideFridgeRepository(
        networkRepository: NetworkFridgeRepositoryImpl,
        dbRepository: DbFridgeRepositoryImpl
    ): FridgeRepository =
        FridgeRepositoryImpl(networkRepository, dbRepository) {
            sp.getBoolean("online", false)
        }

}