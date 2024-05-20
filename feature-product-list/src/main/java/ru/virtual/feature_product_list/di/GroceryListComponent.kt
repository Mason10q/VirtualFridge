package ru.virtual.feature_product_list.di

import dagger.Component
import ru.virtual.core_android.di.CoreModule
import ru.virtual.core_db.DbModule
import ru.virtual.core_network.di.NetworkModule
import ru.virtual.feature_product_list.presentation.AddGroceryListFragment
import ru.virtual.feature_product_list.presentation.GroceryListFragment
import ru.virtual.feature_product_list.presentation.RedactGroceryListDialog
import ru.virtual.feature_product_list.presentation.RenameGroceryListDialog
import javax.inject.Singleton

@Singleton
@Component(modules = [GroceryListModule::class, GroceryListRepoModule::class, NetworkModule::class, CoreModule::class, DbModule::class])
interface GroceryListComponent {

    fun inject(fragment: GroceryListFragment)

    fun inject(fragment: AddGroceryListFragment)

    fun inject(dialog: RedactGroceryListDialog)

    fun inject(dialog: RenameGroceryListDialog)

    @Component.Builder
    interface Builder {
        fun build(): GroceryListComponent
        fun dbModule(module: DbModule): Builder

        fun groceryListRepoModule(module: GroceryListRepoModule): Builder
    }

}