package ru.virtual.core_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.virtual.core_db.tables.GroceryListTable
import ru.virtual.core_db.tables.GroceryProduct
import ru.virtual.core_db.tables.GroceryTable
import ru.virtual.core_db.tables.ProductTable

@Dao
interface GroceryListDao {

    @Query("select * from GroceryLists")
    fun getGroceryLists(): Single<List<GroceryListTable>>

    @Query("select G.productId, P.name, G.amount, G.marked from Groceries as G join Products as P on G.productId = P.id where groceryListId = :listId")
    fun getGroceriesFromList(listId: Int): Single<List<GroceryProduct>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addGroceryList(groceryList: GroceryListTable): Completable

    @Query("delete from GroceryLists where id = :listId")
    fun removeGroceryList(listId: Int): Completable

    @Query("update GroceryLists set name = :newName where id = :listId")
    fun renameGroceryList(listId: Int, newName: String): Completable

    @Query("insert into Groceries (groceryListId, productId) values (:listId, :productId)")
    fun addGroceryToList(listId: Int, productId: Int): Completable

    @Query("update Groceries set amount = amount + 1 where groceryListId = :listId and productId = :productId")
    fun incrementGroceryAmount(listId: Int, productId: Int): Completable

    @Query("update Groceries set amount = amount - 1 where groceryListId = :listId and productId = :productId")
    fun decrementGroceryAmount(listId: Int, productId: Int): Completable

    @Query("select G.productId, P.name, G.amount, G.marked from Groceries as G join Products as P on G.productId = P.id where P.name like :query")
    fun searchProduct(query: String): Single<List<GroceryProduct>>

    @Query("update Groceries set marked = 1 where groceryListId = :listId and productId = :productId")
    fun markGroceryInList(listId: Int, productId: Int): Completable

    @Query("update Groceries set marked = 0 where groceryListId = :listId and productId = :productId")
    fun unMarkGroceryInList(listId: Int, productId: Int): Completable
}