package ru.virtual.feature_auth.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.virtual.core_android.di.ViewModelKey
import ru.virtual.feature_auth.data.AuthRepository
import ru.virtual.feature_auth.data.AuthRepositoryImpl
import ru.virtual.feature_auth.domain.AuthUseCase
import ru.virtual.feature_auth.domain.AuthUseCaseImpl
import ru.virtual.feature_auth.presentation.AuthViewModel

@Module
interface AuthModule {

    @Binds
    fun bindAuthRepository(repository: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindAuthUseCase(useCase: AuthUseCaseImpl): AuthUseCase

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel

}