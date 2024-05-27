package ru.virtual.feature_product_list.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.virtual.core_android.Mapper
import ru.virtual.core_db.GroceryListDao
import ru.virtual.core_db.tables.GroceryListAmounts
import ru.virtual.core_db.tables.GroceryListTable
import ru.virtual.core_db.tables.GroceryProduct
import ru.virtual.core_network.GroceryListApi
import ru.virtual.core_network.NetworkUtil
import ru.virtual.core_network.dto.GroceryDto
import ru.virtual.core_network.dto.GroceryListDto
import ru.virtual.feature_product_list.data.DbGroceryListRepo
import ru.virtual.feature_product_list.data.GroceryListRepo
import ru.virtual.feature_product_list.data.NetworkGroceryListRepo
import ru.virtual.feature_product_list.domain.entities.Grocery
import ru.virtual.feature_product_list.domain.entities.GroceryList
import javax.inject.Singleton

@Module
class GroceryListRepoModule(private val context: Context) {

    @Provides
    fun provideGroceryListRepo(
        api: GroceryListApi,
        dao: GroceryListDao,
        groceryDtoMapper: Mapper<Grocery, GroceryDto>,
        groceryTableMapper: Mapper<Grocery, GroceryProduct>,
        groceryListDtoMapper: Mapper<GroceryList, GroceryListDto>,
        groceryListTableMapper: Mapper<GroceryList, GroceryListAmounts>
    ): GroceryListRepo {
        val sp = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        return if (sp.getBoolean("online", false)) {
            NetworkGroceryListRepo(api, groceryListDtoMapper, groceryDtoMapper, sp.getInt("familyId", -1))
        } else {
            DbGroceryListRepo(dao, groceryListTableMapper, groceryTableMapper)
        }
    }

}