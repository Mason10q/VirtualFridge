package ru.virtual.feature_auth.di

import dagger.Component
import ru.virtual.core_android.di.CoreModule
import ru.virtual.core_network.di.NetworkModule
import ru.virtual.feature_auth.presentation.EnterCodeFragment
import ru.virtual.feature_auth.presentation.EnterEmailFragment
import ru.virtual.feature_auth.presentation.FamilyAccessFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AuthModule::class, NetworkModule::class, CoreModule::class, AuthRepositoryModule::class])
interface AuthComponent {

    fun inject(fragment: EnterCodeFragment)
    fun inject(fragment: EnterEmailFragment)
    fun inject(fragment: FamilyAccessFragment)

    @Component.Builder
    interface Builder {
        fun build(): AuthComponent
        fun authRepoModule(module: AuthRepositoryModule): Builder
    }


}