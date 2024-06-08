package ru.virtual.feature_product_list.domain.usecases

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.paging.Pager
import androidx.paging.rxjava3.flowable
import androidx.paging.rxjava3.observable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.collectLatest
import ru.virtual.feature_product_list.data.GroceryListRepository
import ru.virtual.feature_product_list.data.paging.GroceryListPagingSource
import ru.virtual.feature_product_list.domain.entities.GroceryList
import javax.inject.Inject

class GroceryListUseCaseImpl @Inject constructor(
    private val repository: GroceryListRepository
) : GroceryListUseCase {

    override fun shareGroceryList(context: Context, listId: Int) =
        repository.getGroceriesFromList(listId, 1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ groceryList ->

                val list = groceryList.joinToString(separator = ""){ "${it.name}, ${it.amount} шт. \n" }

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


    override fun getGroceryLists() = Pager(GroceryListRepository.pagerConfig, initialKey = null,
        pagingSourceFactory = { GroceryListPagingSource(repository) }
    ).observable

    override fun addGroceryList(name: String): Completable =
        repository.addGroceryList(name)
            .observeOn(AndroidSchedulers.mainThread())

    override fun removeGroceryList(listId: Int): Completable = repository.removeGroceryList(listId)
        .observeOn(AndroidSchedulers.mainThread())

    override fun renameGroceryList(listId: Int, newName: String): Completable =
        repository.renameGroceryList(listId, newName)
            .observeOn(AndroidSchedulers.mainThread())
}