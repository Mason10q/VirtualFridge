package ru.virtual.core_android.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import ru.virtual.core_android.ViewModelFactory

@Module
interface CoreModule {

    @Binds
    fun bindViewModelFactory(impl: ViewModelFactory): ViewModelProvider.Factory

}