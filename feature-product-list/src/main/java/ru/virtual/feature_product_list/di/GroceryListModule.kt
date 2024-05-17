package ru.virtual.feature_product_list.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.virtual.core_android.Mapper
import ru.virtual.core_android.di.ViewModelKey
import ru.virtual.core_db.tables.GroceryListTable
import ru.virtual.core_db.tables.GroceryProduct
import ru.virtual.core_network.dto.GroceryDto
import ru.virtual.core_network.dto.GroceryListDto
import ru.virtual.feature_product_list.domain.entities.Grocery
import ru.virtual.feature_product_list.domain.entities.GroceryList
import ru.virtual.feature_product_list.domain.mappers.GroceryDtoMapper
import ru.virtual.feature_product_list.domain.mappers.GroceryListDtoMapper
import ru.virtual.feature_product_list.domain.mappers.GroceryListTableMapper
import ru.virtual.feature_product_list.domain.mappers.GroceryTableMapper
import ru.virtual.feature_product_list.domain.usecases.GroceryListUseCase
import ru.virtual.feature_product_list.domain.usecases.GroceryListUseCaseImpl
import ru.virtual.feature_product_list.presentation.GroceryListViewModel

@Module
interface GroceryListModule {

    @Binds
    fun bindsGroceryDtoMapper(mapper: GroceryDtoMapper): Mapper<Grocery, GroceryDto>

    @Binds
    fun bindsGroceryTableMapper(mapper: GroceryTableMapper): Mapper<Grocery, GroceryProduct>

    @Binds
    fun bindGroceryListDtoMapper(mapper: GroceryListDtoMapper): Mapper<GroceryList, GroceryListDto>

    @Binds
    fun bindGroceryListTableMapper(mapper: GroceryListTableMapper): Mapper<GroceryList, GroceryListTable>


    @Binds
    fun bindGroceryDataUseCase(useCase: GroceryListUseCaseImpl): GroceryListUseCase

    @Binds
    @IntoMap
    @ViewModelKey(GroceryListViewModel::class)
    fun bindGroceryListViewModel(viewModel: GroceryListViewModel): ViewModel
}