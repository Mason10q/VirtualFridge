package ru.virtual.feature_product_list

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import ru.virtual.feature_product_list.data.GroceryListRepository
import ru.virtual.feature_product_list.domain.entities.Grocery
import ru.virtual.feature_product_list.domain.entities.GroceryList

class GroceryListAmountsRepoTest {

    private lateinit var groceryListRepository: GroceryListRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        groceryListRepository = Mockito.mock(GroceryListRepository::class.java)
    }

    @Test
    fun `getGroceryListById returns GroceryList`() {
        val listId = 1
        val expectedGroceryList = GroceryList(listId, "Test List", 1, 1)

        Mockito.`when`(groceryListRepository.getGroceryListById(listId))
            .thenReturn(Single.just(expectedGroceryList))

        groceryListRepository.getGroceryListById(listId)
            .test()
            .assertValue(expectedGroceryList)
    }

    @Test
    fun `addGroceryList completes successfully`() {
        val listName = "New List"

        Mockito.`when`(groceryListRepository.addGroceryList(listName))
            .thenReturn(Completable.complete())

        groceryListRepository.addGroceryList(listName)
            .test()
            .assertComplete()
    }

    @Test
    fun `getGroceriesFromList returns list of groceries`() {
        val listId = 1
        val pageNum = 1
        val expectedGroceries = listOf(Grocery(1, "Apple", 1, false), Grocery(2, "Banana", 1, true))

        Mockito.`when`(groceryListRepository.getGroceriesFromList(listId, pageNum))
            .thenReturn(Single.just(expectedGroceries))

        groceryListRepository.getGroceriesFromList(listId, pageNum)
            .test()
            .assertValue(expectedGroceries)
    }

    @Test
    fun `removeGroceryList completes successfully`() {
        val listId = 1

        Mockito.`when`(groceryListRepository.removeGroceryList(listId))
            .thenReturn(Completable.complete())

        groceryListRepository.removeGroceryList(listId)
            .test()
            .assertComplete()
    }

    @Test
    fun `searchProduct returns list of groceries`() {
        val query = "Apple"
        val pageNum = 1
        val listId = 1
        val expectedGroceries = listOf(Grocery(1, "Apple", 1, true))

        Mockito.`when`(groceryListRepository.searchProduct(query, listId, pageNum))
            .thenReturn(Single.just(expectedGroceries))

        groceryListRepository.searchProduct(query, listId, pageNum)
            .test()
            .assertValue(expectedGroceries)
    }
}
