package ru.virtual.feature_recipes

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import ru.virtual.core_android.states.StateMachine
import ru.virtual.core_android.ui.StateFragment
import ru.virtual.feature_recipes.databinding.FragmentRecipesBinding
import javax.inject.Inject

class RecipesFragment: StateFragment<FragmentRecipesBinding, RecipesViewModel>(FragmentRecipesBinding::class.java) {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override val viewModel: RecipesViewModel by viewModels<RecipesViewModel> { viewModelFactory }

    override val stateMachine =  StateMachine.Builder().build()

    override fun setUpViews(view: View) {
        with(binding) {
            searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                }
            })

            this.filterBtn.setOnClickListener{  }


        }
    }

}