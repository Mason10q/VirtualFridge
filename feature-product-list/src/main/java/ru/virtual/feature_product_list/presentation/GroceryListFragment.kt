package ru.virtual.feature_product_list.presentation

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import ru.virtual.core_android.states.StateMachine
import ru.virtual.core_android.ui.StateFragment
import ru.virtual.core_android.ui.utils.addItemMargins
import ru.virtual.feature_product_list.databinding.FragmentGroceryListBinding
import javax.inject.Inject

class GroceryListFragment: StateFragment<FragmentGroceryListBinding, GroceryListViewModel>(FragmentGroceryListBinding::class.java) {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override val viewModel by viewModels<GroceryListViewModel> { viewModelFactory }

    override val stateMachine = StateMachine.Builder()
        .build()


    private val adapter = GroceryListAdapter()

    override fun setUpViews(view: View) {
        binding.groceryListRecycler.also {
            it.adapter = adapter
            it.addItemMargins(26, 26)
        }

    }


}