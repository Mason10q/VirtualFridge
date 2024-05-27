package ru.virtual.core_network

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.POST
import retrofit2.http.Query
import ru.virtual.core_network.dto.VerifiedDto
import ru.virtual.core_network.retrofit.EndpointUrl

@EndpointUrl(BuildConfig.ENDPOINT_URL + "/auth/")
interface AuthApi {

    @POST("sendCode")
    fun sendCodeToEmail(@Query("email") email: String): Completable

    @POST("checkCode")
    fun checkCode(@Query("email") email: String, @Query("code") code: String): Single<VerifiedDto>

}