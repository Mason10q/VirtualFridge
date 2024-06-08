package ru.virtual.feature_recipes.presentation

import android.content.Context
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.squareup.picasso.Picasso
import ru.virtual.core_android.ViewModelFactory
import ru.virtual.core_android.di.AndroidModule
import ru.virtual.core_android.states.StateMachine
import ru.virtual.core_android.ui.StateFragment
import ru.virtual.feature_recipes.databinding.FragmentRecipeBinding
import ru.virtual.feature_recipes.di.DaggerRecipeComponent
import ru.virtual.feature_recipes.di.RecipeRepositoryModule
import ru.virtual.feature_recipes.presentation.adapters.RecipeAdapter
import ru.virtual.feature_recipes.presentation.adapters.RecipeProductAdapter
import javax.inject.Inject
import ru.virtual.core_res.R as resR

class RecipeFragment: StateFragment<FragmentRecipeBinding, RecipesViewModel>(FragmentRecipeBinding::class.java) {

    @Inject lateinit var viewModelFactory: ViewModelFactory

    @Inject lateinit var picasso: Picasso

    override val viewModel: RecipesViewModel by viewModels<RecipesViewModel> { viewModelFactory }

    override val stateMachine = StateMachine.Builder()
        .build()

    private val adapter = RecipeProductAdapter()

    private var recipeId: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject(context)
    }

    override fun getStartData() {
        recipeId = arguments?.getInt("recipeId", -1)
        Log.d("asd", recipeId.toString())
        recipeId?.let {
            viewModel.getRecipe(it)
            viewModel.getRecipeProducts(it)
        }
    }

    override fun setUpViews(view: View) {
        with(binding) {
            ingredientsRecycler.adapter = adapter
        }
    }

    override fun setUpObservers() {
        super.setUpObservers()

        viewModel.recipe.observe(viewLifecycleOwner) {
            Log.d("asd", it.toString())
            with(binding) {
                recipeName.text = it.name
                recipeDescription.text = it.description
                time.text = it.cookingTime
                picasso.load(it.imageUrl)
                    .error(resR.drawable.ic_broken)
                    .placeholder(resR.drawable.ic_broken)
                    .fit()
                    .centerCrop()
                    .into(recipeImage)
            }
        }

        viewModel.recipeProducts.observe(viewLifecycleOwner) {
            Log.d("asd", it.toString())
            adapter.addItems(it)
        }
    }


    private fun inject(context: Context) = DaggerRecipeComponent.builder()
        .recipeRepoModule(RecipeRepositoryModule(context))
        .androidModule(AndroidModule(context))
        .build()
        .inject(this)

}