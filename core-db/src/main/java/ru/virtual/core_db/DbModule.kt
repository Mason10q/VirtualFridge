package ru.virtual.core_db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideGroceryListDao(dataBase: FridgeDataBase) = dataBase.getGroceryListDao()

}