package ru.virtual.core_db

import androidx.annotation.IntRange
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.virtual.core_db.tables.GroceryListAmounts
import ru.virtual.core_db.tables.GroceryListTable
import ru.virtual.core_db.tables.GroceryProduct
import ru.virtual.core_db.tables.GroceryTable
import ru.virtual.core_db.tables.ProductTable

@Dao
interface GroceryListDao {

    @Query("select gl.id, gl.name, COUNT(g.groceryListId) as groceryAmount, SUM(CASE WHEN g.marked = 1 THEN 1 ELSE 0 END) AS groceryMarked from GroceryLists as gl left join Groceries as g on gl.id = g.groceryListId where id = :listId")
    fun getGroceryListById(listId: Int): Single<GroceryListAmounts>

    @Query("select gl.id, gl.name, COUNT(g.groceryListId) as groceryAmount, SUM(CASE WHEN g.marked = 1 THEN 1 ELSE 0 END) AS groceryMarked from GroceryLists as gl left join Groceries as g on gl.id = g.groceryListId group by gl.id, gl.name limit :pageSize offset (:page - 1) * :pageSize")
    fun getGroceryLists(
        @IntRange(from = 1) page: Int = 1,
        @IntRange(from = 1, to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE
    ): Single<List<GroceryListAmounts>>

    @Query("select G.productId, P.name, G.amount, G.marked from Groceries as G join Products as P on G.productId = P.id where groceryListId = :listId limit :pageSize offset (:page - 1) * :pageSize")
    fun getGroceriesFromList(
        listId: Int,
        @IntRange(from = 1) page: Int = 1,
        @IntRange(from = 1, to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE
    ): Single<List<GroceryProduct>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addGroceryList(groceryList: GroceryListTable): Completable

    @Query("delete from GroceryLists where id = :listId")
    fun removeGroceryList(listId: Int): Completable

    @Query("update GroceryLists set name = :newName where id = :listId")
    fun renameGroceryList(listId: Int, newName: String): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addGroceryToList(grocery: GroceryTable): Completable

    @Query("delete from Groceries where groceryListId = :listId and productId = :productId")
    fun removeGroceryFromList(listId: Int, productId: Int): Completable

    @Query("update Groceries set amount = amount + 1 where groceryListId = :listId and productId = :productId")
    fun incrementGroceryAmount(listId: Int, productId: Int): Completable

    @Query("update Groceries set amount = amount - 1 where groceryListId = :listId and productId = :productId")
    fun decrementGroceryAmount(listId: Int, productId: Int): Completable

    @Query("select P.id as productId, P.name, G.amount, G.marked from Products as P left join Groceries as G on P.id = G.productId and G.groceryListId = :listId where P.name like '%' || :query || '%' limit :pageSize offset (:page - 1) * :pageSize")
    fun searchProduct(
        query: String,
        listId: Int,
        @IntRange(from = 1) page: Int = 1,
        @IntRange(from = 1, to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE
    ): Single<List<GroceryProduct>>

    @Query("update Groceries set marked = 1 where groceryListId = :listId and productId = :productId")
    fun markGroceryInList(listId: Int, productId: Int): Completable

    @Query("update Groceries set marked = 0 where groceryListId = :listId and productId = :productId")
    fun unMarkGroceryInList(listId: Int, productId: Int): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProduct(product: ProductTable): Single<Long>

    companion object {
        const val DEFAULT_PAGE_SIZE = 10
        const val MAX_PAGE_SIZE = 20
    }
}