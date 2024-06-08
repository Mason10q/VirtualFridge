package ru.virtual.feature_recipes.di

import dagger.Component
import dagger.Component.Builder
import ru.virtual.core_android.di.AndroidModule
import ru.virtual.core_android.di.CoreModule
import ru.virtual.core_network.di.NetworkModule
import ru.virtual.feature_recipes.presentation.RecipeFragment
import ru.virtual.feature_recipes.presentation.RecipesFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [CoreModule::class, NetworkModule::class, RecipeModule::class, RecipeRepositoryModule::class, AndroidModule::class])
interface RecipeComponent {

    fun inject(fragment: RecipesFragment)
    fun inject(fragment: RecipeFragment)


    @Component.Builder
    interface Builder {
        fun build(): RecipeComponent

        fun recipeRepoModule(module: RecipeRepositoryModule): Builder

        fun androidModule(module: AndroidModule): Builder
    }


}