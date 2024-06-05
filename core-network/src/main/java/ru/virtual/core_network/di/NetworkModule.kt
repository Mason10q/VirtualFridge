package ru.virtual.core_network.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit.Builder
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.virtual.core_network.AlwaysRetryInterceptor
import ru.virtual.core_network.AuthApi
import ru.virtual.core_network.BuildConfig
import ru.virtual.core_network.FridgeApi
import ru.virtual.core_network.GroceryListApi
import ru.virtual.core_network.retrofit.buildApi
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    fun provideClient() = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addNetworkInterceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .header("accept", "application/json")
                    .build()
            )
        }
        .addInterceptor(AlwaysRetryInterceptor(5))
        .retryOnConnectionFailure(true)
        .build()

    @Singleton
    @Provides
    fun provideApi(client: OkHttpClient, gson: Gson) = Builder()
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())


    @Singleton
    @Provides
    fun provideFridgeApi(builder: Builder) = builder.buildApi<FridgeApi>()


    @Singleton
    @Provides
    fun provideGroceryListApi(builder: Builder) = builder.buildApi<GroceryListApi>()

    @Singleton
    @Provides
    fun provideAuthApi(builder: Builder)= builder.buildApi<AuthApi>()
}