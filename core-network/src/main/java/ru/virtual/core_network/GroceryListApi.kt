package ru.virtual.core_network

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.virtual.core_network.dto.GroceryDto
import ru.virtual.core_network.dto.GroceryListDto
import ru.virtual.core_network.retrofit.EndpointUrl

@EndpointUrl("")
interface GroceryListApi {

    @GET("")
    fun getGroceryLists(): Single<List<GroceryListDto>>

    @GET("")
    fun getGroceriesFromList(@Query("listId") listId: Int): Single<List<GroceryDto>>

    @GET("")
    fun addGroceryList(@Query("name") name: String): Completable

    @POST
    fun removeGroceryList(@Query("listId") listId: Int): Completable

    @POST
    fun renameGroceryList(@Query("listId") listId: Int, @Query("newName") newName: String): Completable

    @POST
    fun addGroceryToList(@Query("listId") listId: Int, @Query("productId") productId: Int): Completable

    @POST
    fun incrementGroceryAmount(@Query("listId") listId: Int, @Query("productId") productId: Int): Completable

    @POST
    fun decrementGroceryAmount(@Query("listId") listId: Int, @Query("productId") productId: Int): Completable

    @GET("")
    fun searchProduct(@Query("query") query: String): Single<List<GroceryDto>>

    @POST
    fun markGroceryInList(@Query("listId") listId: Int, @Query("productId") productId: Int): Completable

    @POST
    fun unMarkGroceryInList(@Query("listId") listId: Int, @Query("productId") productId: Int): Completable

}