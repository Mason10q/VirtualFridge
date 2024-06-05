package ru.virtual.core_network

import androidx.annotation.IntRange
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.virtual.core_network.dto.FridgeProductDto
import ru.virtual.core_network.retrofit.EndpointUrl

@EndpointUrl(BuildConfig.ENDPOINT_URL + "/fridge/")
interface FridgeApi {

    @GET("products")
    fun getFridgeProducts(
        @Query("family_id") familyId: Int,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 1, to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE
    ): Single<List<FridgeProductDto>>

    @POST("product/remove")
    fun removeProductFromFridge(@Query("family_id") fridgeId: Int, @Query("product_id") productId: Int): Completable


    @POST("product/add")
    fun addProductToFridge(@Query("family_id") fridgeId: Int, @Query("product_id") productId: Int): Completable

    companion object {
        const val DEFAULT_PAGE_SIZE = 10
        const val MAX_PAGE_SIZE = 20
    }
}