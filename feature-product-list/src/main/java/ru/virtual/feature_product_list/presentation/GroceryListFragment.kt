package ru.virtual.feature_product_list.presentation

import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import ru.virtual.core_android.states.StateMachine
import ru.virtual.core_android.ui.StateFragment
import ru.virtual.core_android.ui.utils.addItemMargins
import ru.virtual.feature_product_list.databinding.FragmentGroceryListBinding
import ru.virtual.feature_product_list.di.DaggerGroceryListComponent
import ru.virtual.feature_product_list.di.GroceryListRepoModule
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
        inject(context)
    }

    override fun setUpViews(view: View) {
        binding.groceryListRecycler.also {
            it.adapter = adapter
            it.addItemMargins(26, 26)
        }
    }

    override fun getStartData() = viewModel.getGroceryListsIfNeeded()

    private fun StateMachine.Builder.addLoadingState(): StateMachine.Builder = addState(
        GroceryListViewModel.GroceryListState.Loading::class,
        callback = { binding.loading.root.isVisible = true },
        onExit = { binding.loading.root.isVisible = false }
    )

    private fun StateMachine.Builder.addReadyState(): StateMachine.Builder = addState(
        GroceryListViewModel.GroceryListState.Ready::class
    ) {
        adapter.addItems(it.groceryLists)
    }

    private fun StateMachine.Builder.addEmptyListState(): StateMachine.Builder = addState(
        GroceryListViewModel.GroceryListState.DataIsEmpty::class
    ) {

    }

    private fun inject(context: Context) = DaggerGroceryListComponent.builder()
        .groceryListRepoModule(GroceryListRepoModule(context))
        .build()


}