package ru.virtual.feature_product_list.di

import dagger.Component
import ru.virtual.core_android.di.CoreModule
import ru.virtual.core_network.di.NetworkModule
import ru.virtual.feature_product_list.presentation.GroceryListFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [GroceryListModule::class, GroceryListRepoModule::class, NetworkModule::class, CoreModule::class])
interface GroceryListComponent {

    fun inject(fragment: GroceryListFragment)
    @Component.Builder
    interface Builder {
        fun build(): GroceryListComponent
        fun groceryListRepoModule(module: GroceryListRepoModule): Builder
    }

}