package ru.virtual.feature_auth.di

import dagger.Component
import ru.virtual.core_android.di.CoreModule
import ru.virtual.core_network.di.NetworkModule
import ru.virtual.feature_auth.presentation.EnterCodeFragment
import ru.virtual.feature_auth.presentation.EnterEmailFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AuthModule::class, NetworkModule::class, CoreModule::class])
interface AuthComponent {

    fun inject(fragment: EnterCodeFragment)
    fun inject(fragment: EnterEmailFragment)

    @Component.Builder
    interface Builder {
        fun build(): AuthComponent
    }


}