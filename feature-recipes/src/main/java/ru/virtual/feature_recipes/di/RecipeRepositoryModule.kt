package ru.virtual.feature_recipes.di

import android.content.Context
import android.content.SharedPreferences
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.virtual.core_android.Mapper
import ru.virtual.core_network.RecipeApi
import ru.virtual.core_network.dto.RecipeDto
import ru.virtual.core_network.dto.RecipeProductDto
import ru.virtual.feature_recipes.data.RecipeRepository
import ru.virtual.feature_recipes.data.RecipeRepositoryImpl
import ru.virtual.feature_recipes.domain.entities.Recipe
import ru.virtual.feature_recipes.domain.entities.RecipeProduct
import ru.virtual.feature_recipes.presentation.adapters.RecipeAdapter

@Module
class RecipeRepositoryModule(context: Context) {

    private val sp: SharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)


    @Provides
    fun provideRecipeRepository(
        api: RecipeApi,
        recipeMapper: Mapper<Recipe, RecipeDto>,
        recipeProductMapper: Mapper<RecipeProduct, RecipeProductDto>
    ):RecipeRepository = RecipeRepositoryImpl(api, recipeMapper, recipeProductMapper, sp.getInt("familyId", -1))


    @Provides
    fun provideRecipeAdapter(picasso: Picasso) = RecipeAdapter(picasso)
}