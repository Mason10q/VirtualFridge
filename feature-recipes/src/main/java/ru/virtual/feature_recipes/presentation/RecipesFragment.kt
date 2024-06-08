package ru.virtual.feature_recipes.presentation

import android.content.Context
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.launch
import ru.virtual.core_android.di.AndroidModule
import ru.virtual.core_android.states.StateMachine
import ru.virtual.core_android.ui.FooterLoadStateAdapter
import ru.virtual.core_android.ui.StateFragment
import ru.virtual.core_android.ui.utils.addItemMargins
import ru.virtual.feature_recipes.databinding.FragmentRecipesBinding
import ru.virtual.feature_recipes.di.DaggerRecipeComponent
import ru.virtual.feature_recipes.di.RecipeRepositoryModule
import ru.virtual.feature_recipes.presentation.adapters.RecipeAdapter
import javax.inject.Inject
import ru.virtual.core_navigation.R as navR

class RecipesFragment: StateFragment<FragmentRecipesBinding, RecipesViewModel>(FragmentRecipesBinding::class.java) {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override val viewModel: RecipesViewModel by viewModels<RecipesViewModel> { viewModelFactory }

    override val stateMachine =  StateMachine.Builder()
        .addLoadingState()
        .addReadyState()
        .build()

    private val filterDialog = FilterDialog()

    @Inject lateinit var adapter: RecipeAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject(context)
    }

    override fun getStartData() {
        viewModel.getRecipes()
    }

    override fun setUpViews(view: View) {
        with(binding) {
            searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = false

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })

            filterDialog.setOnCancelListener {
                binding.filterBtn.isEnabled = true
                viewModel.getRecipes()
            }

            filterBtn.setOnClickListener{
                filterDialog.show(parentFragmentManager, "")
                binding.filterBtn.isEnabled = false
            }

            searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = false

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { viewModel.searchRecipes(it) }
                    return true
                }
            })

            recipeRecycler.also {
                it.adapter = adapter.withLoadStateFooter(FooterLoadStateAdapter())
                it.layoutManager = GridLayoutManager(context, 2)
                it.addItemMargins(15, 15)
            }

            adapter.addEmptyLayout(emptyLayout.root)

            adapter.setOnItemClick {
                findNavController().navigate(navR.id.fragment_recipe, bundleOf("recipeId" to it))
            }
        }
    }

    private fun StateMachine.Builder.addLoadingState(): StateMachine.Builder = addState(
        RecipesViewModel.RecipesState.Loading::class,
        callback = { binding.loading.root.isVisible = true },
        onExit = { binding.loading.root.isVisible = false }
    )

    private fun StateMachine.Builder.addReadyState(): StateMachine.Builder = addState(
        RecipesViewModel.RecipesState.Ready::class
    ) {
        lifecycleScope.launch {
            adapter.submitData(it.recipes)
        }
    }

    private fun inject(context: Context) = DaggerRecipeComponent.builder()
        .recipeRepoModule(RecipeRepositoryModule(context))
        .androidModule(AndroidModule(context))
        .build()
        .inject(this)

}