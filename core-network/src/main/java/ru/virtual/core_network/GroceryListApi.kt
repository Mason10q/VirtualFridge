package ru.virtual.core_network

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.virtual.core_network.dto.GroceryDto
import ru.virtual.core_network.dto.GroceryListDto
import ru.virtual.core_network.retrofit.EndpointUrl
import androidx.annotation.IntRange

@EndpointUrl(BuildConfig.ENDPOINT_URL + "/groceryList/")
interface GroceryListApi {

    @GET("list")
    fun getGroceryListById(@Query("listId") listId: Int): Single<GroceryListDto>

    @GET("lists")
    fun getGroceryLists(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 1, to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE
    ): Single<List<GroceryListDto>>

    @GET("groceries")
    fun getGroceriesFromList(
        @Query("listId") listId: Int,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 1, to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE
    ): Single<List<GroceryDto>>

    @GET("add")
    fun addGroceryList(@Query("name") name: String): Completable

    @POST("remove")
    fun removeGroceryList(@Query("listId") listId: Int): Completable

    @POST("rename")
    fun renameGroceryList(@Query("listId") listId: Int, @Query("newName") newName: String): Completable

    @POST("grocery/add")
    fun addGroceryToList(@Query("listId") listId: Int, @Query("productId") productId: Int): Completable

    @POST("grocery/remove")
    fun removeGroceryFromList(@Query("listId") listId: Int, @Query("productId") productId: Int): Completable

    @POST("grocery/amount/increment")
    fun incrementGroceryAmount(@Query("listId") listId: Int, @Query("productId") productId: Int): Completable

    @POST("grocery/amount/decrement")
    fun decrementGroceryAmount(@Query("listId") listId: Int, @Query("productId") productId: Int): Completable

    @GET("grocery/search")
    fun searchProduct(
        @Query("query") query: String,
        @Query("list_id") listId: Int,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 1, to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE
    ): Single<List<GroceryDto>>

    @POST("grocery/mark")
    fun setMarkState(@Query("listId") listId: Int, @Query("productId") productId: Int, @Query("state") state: Boolean): Completable

    companion object {
        const val DEFAULT_PAGE_SIZE = 10
        const val MAX_PAGE_SIZE = 20
    }

}