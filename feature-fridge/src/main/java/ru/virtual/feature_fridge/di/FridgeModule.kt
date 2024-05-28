package ru.virtual.feature_fridge.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.virtual.core_android.Mapper
import ru.virtual.core_android.di.ViewModelKey
import ru.virtual.core_db.tables.FridgeProductsTable
import ru.virtual.core_network.dto.FridgeProductDto
import ru.virtual.feature_fridge.domain.FridgeUseCase
import ru.virtual.feature_fridge.domain.FridgeUseCaseImpl
import ru.virtual.feature_fridge.domain.entities.Product
import ru.virtual.feature_fridge.domain.mappers.ProductDtoMapper
import ru.virtual.feature_fridge.domain.mappers.ProductTableMapper
import ru.virtual.feature_fridge.presentation.FridgeViewModel

@Module
interface FridgeModule {

    @Binds
    fun bindFridgeUseCase(useCase: FridgeUseCaseImpl): FridgeUseCase

    @Binds
    fun bindProductDtoMapper(mapper: ProductDtoMapper): Mapper<Product, FridgeProductDto>

    @Binds
    fun bindProductTableMapper(mapper: ProductTableMapper): Mapper<Product, FridgeProductsTable>

    @Binds
    @IntoMap
    @ViewModelKey(FridgeViewModel::class)
    fun bindFridgeViewModel(viewModel: FridgeViewModel): ViewModel

}