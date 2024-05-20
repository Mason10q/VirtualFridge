package ru.virtual.feature_product_list.domain.usecases

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.paging.Pager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.virtual.feature_product_list.data.GroceryListRepo
import ru.virtual.feature_product_list.data.paging.GroceryListPagingSource
import ru.virtual.feature_product_list.domain.entities.GroceryList
import javax.inject.Inject

class GroceryListUseCaseImpl @Inject constructor(
    private val repository: GroceryListRepo
) : GroceryListUseCase {

    override fun shareGroceryList(context: Context, listId: Int) =
        repository.getGroceriesFromList(listId, 1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ groceryList ->

                val list = groceryList.joinToString{ "${it.name}, ${it.amount} шт. \n" }

                Log.d("asd", list)

                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, list)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(context, shareIntent, null)
            }, {})

    override fun getGroceryListById(listId: Int): Single<GroceryList> =
        repository.getGroceryListById(listId)
            .observeOn(AndroidSchedulers.mainThread())


    override fun getGroceryLists() = Pager(GroceryListRepo.pagerConfig, initialKey = 1,
        pagingSourceFactory = { GroceryListPagingSource(repository) }
    ).flow

    override fun addGroceryList(name: String): Completable =
        repository.addGroceryList(name)
            .observeOn(AndroidSchedulers.mainThread())

    override fun removeGroceryList(listId: Int): Completable = repository.removeGroceryList(listId)
        .observeOn(AndroidSchedulers.mainThread())

    override fun renameGroceryList(listId: Int, newName: String): Completable =
        repository.renameGroceryList(listId, newName)
            .observeOn(AndroidSchedulers.mainThread())
}