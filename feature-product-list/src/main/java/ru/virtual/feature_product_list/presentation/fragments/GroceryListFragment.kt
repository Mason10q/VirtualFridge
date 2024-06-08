package ru.virtual.feature_product_list.presentation.fragments

import android.content.Context
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import ru.virtual.core_navigation.R as navR
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.virtual.core_android.states.StateMachine
import ru.virtual.core_android.ui.FooterLoadStateAdapter
import ru.virtual.core_android.ui.StateFragment
import ru.virtual.core_android.ui.utils.addItemMargins
import ru.virtual.core_db.DbModule
import ru.virtual.feature_product_list.R
import ru.virtual.feature_product_list.databinding.FragmentGroceryListBinding
import ru.virtual.feature_product_list.di.DaggerGroceryListComponent
import ru.virtual.feature_product_list.di.GroceryListRepoModule
import ru.virtual.feature_product_list.presentation.adapters.GroceryListAdapter
import ru.virtual.feature_product_list.presentation.vm.GroceryListViewModel
import javax.inject.Inject

class GroceryListFragment: StateFragment<FragmentGroceryListBinding, GroceryListViewModel>(FragmentGroceryListBinding::class.java) {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override val viewModel by viewModels<GroceryListViewModel> { viewModelFactory }

    override val stateMachine = StateMachine.Builder()
        .lifecycleOwner(this)
        .addLoadingState()
        .addReadyState()
        .build()


    private val adapter = GroceryListAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject(requireContext())
    }

    override fun setUpViews(view: View) {
        setUpEmptyLayout()
        setUpAdapter()

        with(binding) {
            groceryListRecycler.also {
                it.adapter = adapter.withLoadStateFooter(FooterLoadStateAdapter())
                it.addItemMargins(26, 26)
            }

            addBtn.setOnClickListener { findNavController().navigate(navR.id.fragment_add_grocery_list) }

            refresher.setOnRefreshListener { refreshData() }
        }
    }

    override fun refreshData() { viewModel.getGroceryLists() }

    private fun setUpAdapter() {
        with(adapter) {
            addLoadStateListener { loadState ->
                if (loadState.prepend.endOfPaginationReached) {
                    binding.emptyLayout.root.isVisible = itemCount < 1
                }
            }

            setOnRedactButtonClick { list ->
                RedactGroceryListDialog().apply {
                    arguments = bundleOf("listId" to list.id)
                    setOnDismissListener {
                        lifecycleScope.launch { viewModel.getGroceryLists() } //TODO:updates whole recycler, bad
                    }
                }.show(parentFragmentManager, "")
            }

            setOnItemClick { list ->
                findNavController().navigate(
                    navR.id.fragment_groceries,
                    bundleOf("listId" to list.id)
                )
            }
        }
    }

    override fun getStartData() {
        viewModel.getGroceryLists()
    }

    private fun setUpEmptyLayout() {
        with(binding.emptyLayout) {
            notifyText.text = resources.getString(R.string.screen_grocery_list_empty_list_text)
            notifyDescription.text = resources.getString(R.string.screen_grocery_list_empty_list_description)
        }
    }

    private fun StateMachine.Builder.addLoadingState(): StateMachine.Builder = addState(
        GroceryListViewModel.GroceryListState.Loading::class,
        callback = { binding.loading.root.isVisible = true },
        onExit = { binding.loading.root.isVisible = false }
    )

    private fun StateMachine.Builder.addReadyState(): StateMachine.Builder = addState(
        GroceryListViewModel.GroceryListState.Ready::class
    ) {
        lifecycleScope.launch {
            adapter.submitData(it.groceryLists)
            binding.refresher.isRefreshing = false
        }
    }

    private fun inject(context: Context) = DaggerGroceryListComponent.builder()
        .groceryListRepoModule(GroceryListRepoModule(context))
        .dbModule(DbModule(context))
        .build()
        .inject(this)


}