package ru.virtual.core_db

import androidx.annotation.IntRange
import androidx.room.Dao
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.virtual.core_db.tables.FridgeProductsTable

@Dao
interface FridgeDao {

    @Query("insert into FridgeProducts (productName) values ((select name from Products where id = :productId))")
    fun addProductToFridge(productId: Int): Completable

    @Query("select * from FridgeProducts limit :pageSize offset (:page - 1) * :pageSize")
    fun getFridgeProducts(
        @IntRange(from = 1) page: Int = 1,
        @IntRange(from = 1, to = GroceryListDao.MAX_PAGE_SIZE.toLong()) pageSize: Int = GroceryListDao.DEFAULT_PAGE_SIZE
    ): Single<List<FridgeProductsTable>>

    @Query("delete from FridgeProducts where id = :productId")
    fun removeProductFromFridge(productId: Int): Completable

}