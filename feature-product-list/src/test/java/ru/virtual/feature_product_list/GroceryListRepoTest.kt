package ru.virtual.feature_product_list

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import ru.virtual.feature_product_list.data.GroceryListRepo
import ru.virtual.feature_product_list.domain.entities.Grocery
import ru.virtual.feature_product_list.domain.entities.GroceryList

class GroceryListRepoTest {

    private lateinit var groceryListRepo: GroceryListRepo

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        groceryListRepo = Mockito.mock(GroceryListRepo::class.java)
    }

    @Test
    fun `getGroceryListById returns GroceryList`() {
        val listId = 1
        val expectedGroceryList = GroceryList(listId, "Test List", 1, 1)

        Mockito.`when`(groceryListRepo.getGroceryListById(listId))
            .thenReturn(Single.just(expectedGroceryList))

        groceryListRepo.getGroceryListById(listId)
            .test()
            .assertValue(expectedGroceryList)
    }

    @Test
    fun `addGroceryList completes successfully`() {
        val listName = "New List"

        Mockito.`when`(groceryListRepo.addGroceryList(listName))
            .thenReturn(Completable.complete())

        groceryListRepo.addGroceryList(listName)
            .test()
            .assertComplete()
    }

    @Test
    fun `getGroceriesFromList returns list of groceries`() {
        val listId = 1
        val pageNum = 1
        val expectedGroceries = listOf(Grocery(1, "Apple", 1, false), Grocery(2, "Banana", 1, true))

        Mockito.`when`(groceryListRepo.getGroceriesFromList(listId, pageNum))
            .thenReturn(Single.just(expectedGroceries))

        groceryListRepo.getGroceriesFromList(listId, pageNum)
            .test()
            .assertValue(expectedGroceries)
    }

    @Test
    fun `removeGroceryList completes successfully`() {
        val listId = 1

        Mockito.`when`(groceryListRepo.removeGroceryList(listId))
            .thenReturn(Completable.complete())

        groceryListRepo.removeGroceryList(listId)
            .test()
            .assertComplete()
    }

    @Test
    fun `searchProduct returns list of groceries`() {
        val query = "Apple"
        val pageNum = 1
        val expectedGroceries = listOf(Grocery(1, "Apple", 1, true))

        Mockito.`when`(groceryListRepo.searchProduct(query, pageNum))
            .thenReturn(Single.just(expectedGroceries))

        groceryListRepo.searchProduct(query, pageNum)
            .test()
            .assertValue(expectedGroceries)
    }
}
