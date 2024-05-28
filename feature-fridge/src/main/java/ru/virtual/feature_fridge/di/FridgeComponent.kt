package ru.virtual.feature_fridge.di

import dagger.Component
import ru.virtual.core_android.di.CoreModule
import ru.virtual.core_db.DbModule
import ru.virtual.core_network.di.NetworkModule
import ru.virtual.feature_fridge.presentation.FridgeFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [FridgeModule::class, FridgeRepoModule::class, NetworkModule::class, DbModule::class, CoreModule::class])
interface FridgeComponent {

    fun inject(fragment: FridgeFragment)

    @Component.Builder
    interface Builder {
        fun build(): FridgeComponent

        fun fridgeRepoModule(module: FridgeRepoModule): Builder

        fun dbModule(module: DbModule): Builder
    }

}