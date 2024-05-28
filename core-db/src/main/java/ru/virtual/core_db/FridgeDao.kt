package ru.virtual.core_db

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.virtual.core_db.tables.FridgeProductsTable

@Dao
interface FridgeDao {

    @Query("select * from FridgeProducts")
    fun getFridgeProducts(): Single<List<FridgeProductsTable>>

    @Query("delete from FridgeProducts where id = :productId")
    fun removeProductFromFridge(productId: Int): Completable

}