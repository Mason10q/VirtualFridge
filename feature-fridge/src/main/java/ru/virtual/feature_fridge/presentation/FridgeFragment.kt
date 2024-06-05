package ru.virtual.feature_fridge.presentation

import android.content.Context
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.virtual.core_android.states.StateMachine
import ru.virtual.core_android.ui.FooterLoadStateAdapter
import ru.virtual.core_android.ui.StateFragment
import ru.virtual.core_android.ui.utils.addItemMargins
import ru.virtual.core_db.DbModule
import ru.virtual.feature_fridge.R
import ru.virtual.feature_fridge.databinding.FragmentFridgeBinding
import ru.virtual.feature_fridge.di.DaggerFridgeComponent
import ru.virtual.feature_fridge.di.FridgeRepoModule
import javax.inject.Inject

class FridgeFragment: StateFragment<FragmentFridgeBinding, FridgeViewModel>(FragmentFridgeBinding::class.java) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val viewModel: FridgeViewModel by viewModels<FridgeViewModel> { viewModelFactory }

    override val stateMachine =  StateMachine.Builder()
        .addLoadingState()
        .addReadyState()
        .build()

    private val adapter = FridgeAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject(context)
        Log.d("asd", "onAttach")
    }

    override fun getStartData() {
        lifecycleScope.launch {
            viewModel.getProducts()
        }
    }

    override fun setUpViews(view: View) {
        setUpEmptyLayout()

        with(binding) {
            productsRecycler.also {
                it.adapter = adapter.withLoadStateFooter(FooterLoadStateAdapter())
                it.addItemMargins(15, 10)
            }

            adapter.setOnRemoveBtnClick { fridgeId, productId ->
                viewModel.removeProduct(fridgeId, productId)
            }

            adapter.addLoadStateListener { loadState ->
                if (loadState.prepend.endOfPaginationReached) {
                    binding.emptyLayout.root.isVisible = adapter.itemCount < 1
                }
            }
        }
    }

    private fun setUpEmptyLayout() {
        with(binding.emptyLayout) {
            notifyText.text = context?.getString(R.string.screen_fridge_empty_list_text)
            notifyDescription.text = context?.getString(R.string.screen_fridge_empty_list_description)
        }
    }

    private fun StateMachine.Builder.addLoadingState(): StateMachine.Builder = addState(
        FridgeViewModel.FridgeState.Loading::class,
        callback = { binding.loading.root.isVisible = true },
        onExit = { binding.loading.root.isVisible = false }
    )

    private fun StateMachine.Builder.addReadyState(): StateMachine.Builder = addState(
        FridgeViewModel.FridgeState.Ready::class
    ) {
        lifecycleScope.launch {
            adapter.submitData(it.products)
        }
    }

    private fun inject(context: Context) = DaggerFridgeComponent.builder()
        .fridgeRepoModule(FridgeRepoModule(context))
        .dbModule(DbModule(context))
        .build()
        .inject(this)

}