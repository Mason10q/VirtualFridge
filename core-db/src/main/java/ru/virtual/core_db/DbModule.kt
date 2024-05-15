package ru.virtual.core_db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideFridgeDataBase() = Room.databaseBuilder(
        context.applicationContext,
        FridgeDataBase::class.java,
        "fridge_database"
    ).build()

    @Provides
    fun provideGroceryListDao(dataBase: FridgeDataBase) = dataBase.getGroceryListDao()

}