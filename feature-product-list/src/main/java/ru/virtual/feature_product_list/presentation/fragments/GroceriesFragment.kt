package ru.virtual.feature_product_list.presentation.fragments

import android.content.Context
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.map
import kotlinx.coroutines.launch
import ru.virtual.core_android.states.StateMachine
import ru.virtual.core_android.ui.FooterLoadStateAdapter
import ru.virtual.core_android.ui.StateFragment
import ru.virtual.core_android.ui.utils.addItemMargins
import ru.virtual.core_db.DbModule
import ru.virtual.feature_product_list.R
import ru.virtual.feature_product_list.databinding.FragmentGroceriesBinding
import ru.virtual.feature_product_list.di.DaggerGroceryListComponent
import ru.virtual.feature_product_list.di.GroceryListRepoModule
import ru.virtual.feature_product_list.domain.PercentUtil
import ru.virtual.feature_product_list.presentation.adapters.GroceriesAdapter
import ru.virtual.feature_product_list.presentation.vm.GroceryViewModel
import javax.inject.Inject
import ru.virtual.core_navigation.R as resR

class GroceriesFragment :
    StateFragment<FragmentGroceriesBinding, GroceryViewModel>(FragmentGroceriesBinding::class.java) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val viewModel by viewModels<GroceryViewModel> { viewModelFactory }

    override val stateMachine = StateMachine.Builder()
        .lifecycleOwner(this)
        .addLoadingState()
        .addReadyState()
        .build()

    private val adapter = GroceriesAdapter()

    private var listId: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject(context)
    }

    override fun getStartData() {
        listId = arguments?.getInt("listId")

        lifecycleScope.launch {
            listId?.let { viewModel.getListGroceriesIfNeeded(it) }
        }

        listId?.let { viewModel.getGroceryList(it) }
    }

    override fun setUpViews(view: View) {
        setUpEmptyLayout()

        binding.groceryRecycler.also {
            it.adapter = adapter.withLoadStateFooter(FooterLoadStateAdapter())
            it.addItemMargins(26, 26)
        }

        binding.backBtn.setOnClickListener { findNavController().navigateUp() }

        adapter.addLoadStateListener { loadState ->
            if (loadState.prepend.endOfPaginationReached) {
                if (adapter.itemCount < 1) {
                    binding.emptyLayout.root.visibility = View.VISIBLE
                } else {
                    binding.emptyLayout.root.visibility = View.GONE
                }
            }
        }

        adapter.setOnCheckBoxClick { checked, productId ->
            listId?.let {
                if (checked) {
                    viewModel.markGrocery(it, productId)
                } else {
                    viewModel.unMarkGrocery(it, productId)
                }
            }

            viewModel.groceriesAmount?.let {
                binding.groceryProgressBar.setProgressPercentage(
                    if (checked)
                        binding.groceryProgressBar.getProgressPercentage() + PercentUtil.getPercents(1, it)
                    else
                        binding.groceryProgressBar.getProgressPercentage() - PercentUtil.getPercents(1, it)
                )
            }
        }

        binding.addBtn.setOnClickListener {
            findNavController().navigate(
                resR.id.fragment_add_groceries,
                bundleOf("listId" to listId)
            )
        }
    }

    private fun setUpEmptyLayout() {
        with(binding.emptyLayout) {
            notifyText.text = resources.getString(R.string.screen_groceries_empty_list_text)
            notifyDescription.text =
                resources.getString(R.string.screen_groceries_empty_list_description)
        }
    }

    override fun setUpObservers() {
        super.setUpObservers()

        viewModel.groceryList.observe(viewLifecycleOwner) { groceryList ->
            binding.title.text = groceryList.name

            binding.groceryProgressBar.setProgressPercentage(
                PercentUtil.getPercents(
                    groceryList.productsMarked,
                    groceryList.productsAmount
                )
            )
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