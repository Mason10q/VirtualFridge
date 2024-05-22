package ru.virtual.feature_product_list.di

import dagger.Component
import ru.virtual.core_android.di.CoreModule
import ru.virtual.core_db.DbModule
import ru.virtual.core_network.di.NetworkModule
import ru.virtual.feature_product_list.presentation.fragments.AddGroceryListFragment
import ru.virtual.feature_product_list.presentation.fragments.AddGroceriesFragment
import ru.virtual.feature_product_list.presentation.fragments.AddProductFragment
import ru.virtual.feature_product_list.presentation.fragments.GroceriesFragment
import ru.virtual.feature_product_list.presentation.fragments.GroceryListFragment
import ru.virtual.feature_product_list.presentation.fragments.RedactGroceryListDialog
import ru.virtual.feature_product_list.presentation.fragments.RenameGroceryListDialog
import javax.inject.Singleton

@Singleton
@Component(modules = [GroceryListModule::class, GroceryListRepoModule::class, NetworkModule::class, CoreModule::class, DbModule::class])
interface GroceryListComponent {

    fun inject(fragment: GroceryListFragment)

    fun inject(fragment: AddGroceryListFragment)

    fun inject(dialog: RedactGroceryListDialog)

    fun inject(dialog: RenameGroceryListDialog)

    fun inject(fragment: AddGroceriesFragment)

    fun inject(fragment: GroceriesFragment)

    fun inject(fragment: AddProductFragment)

    @Component.Builder
    interface Builder {
        fun build(): GroceryListComponent
        fun dbModule(module: DbModule): Builder

        fun groceryListRepoModule(module: GroceryListRepoModule): Builder
    }

}