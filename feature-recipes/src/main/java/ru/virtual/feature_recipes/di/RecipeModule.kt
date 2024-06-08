package ru.virtual.feature_recipes.di

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.virtual.core_android.Mapper
import ru.virtual.core_android.di.ViewModelKey
import ru.virtual.core_network.dto.GroceryDto
import ru.virtual.core_network.dto.RecipeDto
import ru.virtual.core_network.dto.RecipeProductDto
import ru.virtual.feature_recipes.domain.FilterUseCase
import ru.virtual.feature_recipes.domain.FilterUseCaseImpl
import ru.virtual.feature_recipes.domain.RecipeUseCase
import ru.virtual.feature_recipes.domain.RecipeUseCaseImpl
import ru.virtual.feature_recipes.domain.entities.Recipe
import ru.virtual.feature_recipes.domain.entities.RecipeProduct
import ru.virtual.feature_recipes.domain.mappers.RecipeMapper
import ru.virtual.feature_recipes.domain.mappers.RecipeProductMapper
import ru.virtual.feature_recipes.presentation.RecipesViewModel
import ru.virtual.feature_recipes.presentation.adapters.RecipeAdapter

@Module
interface RecipeModule {

    @Binds
    fun bindsRecipeDtoMapper(mapper: RecipeMapper): Mapper<Recipe, RecipeDto>

    @Binds
    fun bindsRecipeProductMapper(mapper: RecipeProductMapper): Mapper<RecipeProduct, RecipeProductDto>

    @Binds
    fun bindRecipeUseCase(useCase: RecipeUseCaseImpl): RecipeUseCase

    @Binds
    fun bindFilterUseCase(useCase: FilterUseCaseImpl): FilterUseCase

    @Binds
    @IntoMap
    @ViewModelKey(RecipesViewModel::class)
    fun bindGroceryListViewModel(viewModel: RecipesViewModel): ViewModel

}