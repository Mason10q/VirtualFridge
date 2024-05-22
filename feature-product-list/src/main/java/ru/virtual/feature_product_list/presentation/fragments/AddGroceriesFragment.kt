package ru.virtual.feature_product_list.presentation.fragments

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import ru.virtual.core_navigation.R as navR
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.virtual.core_android.states.StateMachine
import ru.virtual.core_android.ui.FooterLoadStateAdapter
import ru.virtual.core_android.ui.StateFragment
import ru.virtual.core_android.ui.utils.addItemMargins
import ru.virtual.core_db.DbModule
import ru.virtual.feature_product_list.R
import ru.virtual.feature_product_list.databinding.FragmentAddGroceryBinding
import ru.virtual.feature_product_list.databinding.FragmentGroceriesBinding
import ru.virtual.feature_product_list.di.DaggerGroceryListComponent
import ru.virtual.feature_product_list.di.GroceryListRepoModule
import ru.virtual.feature_product_list.presentation.adapters.AddGroceriesAdapter
import ru.virtual.feature_product_list.presentation.vm.GroceryViewModel
import javax.inject.Inject

class AddGroceriesFragment: StateFragment<FragmentAddGroceryBinding, GroceryViewModel>(FragmentAddGroceryBinding::class.java) {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override val viewModel by viewModels<GroceryViewModel> { viewModelFactory }

    private val handler = Handler(Looper.getMainLooper())

    override val stateMachine = StateMachine.Builder()
        .lifecycleOwner(this)
        .addLoadingState()
        .addReadyState()
        .build()

    private val adapter = AddGroceriesAdapter()

    private var listId: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject(context)
    }

    override fun getStartData() {
        listId = arguments?.getInt("listId")

        lifecycleScope.launch {
            listId?.let { viewModel.getGroceriesIfNeeded(it) }
        }
    }

    override fun setUpViews(view: View) {
        setUpEmptyLayout()

        binding.backBtn.setOnClickListener{ findNavController().navigateUp() }

        binding.groceryRecycler.also {
            it.adapter = adapter.withLoadStateFooter(FooterLoadStateAdapter())
            it.addItemMargins(26, 26)
        }

        binding.searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacksAndMessages(null)

                handler.postDelayed({
                    lifecycleScope.launch {
                        listId?.let {
                            viewModel.searchGroceries(binding.searchBar.query.toString().trim(), it)
                        }
                    }
                }, 1000)

                return true
            }
        })

        adapter.addLoadStateListener { loadState ->
            if(loadState.prepend.endOfPaginationReached) {
                if(adapter.itemCount < 1) {
                    binding.emptyLayout.root.visibility = View.VISIBLE
                } else {
                    binding.emptyLayout.root.visibility = View.GONE
                }
            }
        }


        adapter.setOnAddGroceryListener { productId, amount ->
            listId?.let {
                if (amount == 0) viewModel.addGrocery(it, productId)
                else viewModel.incrementGroceryAmount(it, productId)
            }
        }
        adapter.setOnGroceryReduceListener { productId, amount ->
            listId?.let {
                if(amount == 1) viewModel.removeGrocery(it, productId)
                else viewModel.decrementGroceryAmount(it, productId)
            }
        }

        binding.addBtn.setOnClickListener{ findNavController().navigate(navR.id.fragment_add_product, bundleOf("listId" to listId)) }
    }

    private fun setUpEmptyLayout() {
        with(binding.emptyLayout) {
            notifyText.text = resources.getString(R.string.screen_add_grocery_empty_list_text)
            notifyDescription.text = resources.getString(R.string.screen_add_grocery_empty_list_description)
        }
    }

    private fun inject(context: Context) = DaggerGroceryListComponent.builder()
        .groceryListRepoModule(GroceryListRepoModule(context))
        .dbModule(DbModule(context))
        .build()
        .inject(this)

    private fun StateMachine.Builder.addLoadingState(): StateMachine.Builder = addState(
        GroceryViewModel.GroceryState.Loading::class,
        callback = { binding.loading.root.isVisible = true },
        onExit = { binding.loading.root.isVisible = false }
    )

    private fun StateMachine.Builder.addReadyState(): StateMachine.Builder = addState(
        GroceryViewModel.GroceryState.Ready::class
    ) {
        lifecycleScope.launch {
            adapter.submitData(it.groceries)
        }
    }
}