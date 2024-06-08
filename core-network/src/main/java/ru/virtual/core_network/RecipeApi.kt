package ru.virtual.core_network

import androidx.annotation.IntRange
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query
import ru.virtual.core_network.dto.RangeFilterDto
import ru.virtual.core_network.dto.RecipeDto
import ru.virtual.core_network.dto.RecipeProductDto
import ru.virtual.core_network.dto.TextFilterDto
import ru.virtual.core_network.retrofit.EndpointUrl

@EndpointUrl(BuildConfig.ENDPOINT_URL + "/recipes/")
interface RecipeApi {

    @GET("recipes/filters/text")
    fun getAllTextFilters(): Single<List<TextFilterDto>>

    @GET("recipes/filters/range")
    fun getAllRangeFilters(): Single<List<RangeFilterDto>>

    @GET("list")
    fun getRecipes(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 1, to = GroceryListApi.MAX_PAGE_SIZE.toLong()) pageSize: Int = GroceryListApi.DEFAULT_PAGE_SIZE
    ): Single<List<RecipeDto>>

    @GET("search")
    fun searchRecipes(
        @Query("query") query: String,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 1, to = GroceryListApi.MAX_PAGE_SIZE.toLong()) pageSize: Int = GroceryListApi.DEFAULT_PAGE_SIZE
    ): Single<List<RecipeDto>>


    @GET("recipe")
    fun getRecipe(@Query("recipe_id") recipeId: Int): Single<RecipeDto>

    @GET("recipe/products")
    fun getRecipeProducts(@Query("recipe_id") recipeId: Int, @Query("family_id") familyId: Int): Single<List<RecipeProductDto>>


    companion object {
        const val DEFAULT_PAGE_SIZE = 10
        const val MAX_PAGE_SIZE = 20
    }
}