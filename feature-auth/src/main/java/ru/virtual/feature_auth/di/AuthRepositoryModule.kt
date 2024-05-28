package ru.virtual.feature_auth.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.virtual.core_network.AuthApi
import ru.virtual.feature_auth.data.AuthRepository
import ru.virtual.feature_auth.data.AuthRepositoryImpl

@Module
class AuthRepositoryModule(private val context: Context) {

    @Provides
    fun provideAuthRepository(api: AuthApi): AuthRepository {
        val sp = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        return AuthRepositoryImpl(api, sp.getInt("familyId", -1))
    }

}