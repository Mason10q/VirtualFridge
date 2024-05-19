package ru.virtual.feature_product_list.presentation

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.virtual.core_android.ui.BaseFragment
import ru.virtual.core_android.ui.SimpleTextWatcher
import ru.virtual.core_db.DbModule
import ru.virtual.core_navigation.R as navR
import ru.virtual.feature_product_list.databinding.FragmentAddGroceryListBinding
import ru.virtual.feature_product_list.di.DaggerGroceryListComponent
import ru.virtual.feature_product_list.di.GroceryListRepoModule
import ru.virtual.feature_product_list.domain.entities.GroceryList
import javax.inject.Inject

class AddGroceryListFragment: BaseFragment<FragmentAddGroceryListBinding>(FragmentAddGroceryListBinding::class.java) {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<GroceryListViewModel> { viewModelFactory }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject(context)
    }

    override fun setUpViews(view: View) {
        binding.addBtn.setOnClickListener{
            viewModel.addGroceryList(binding.nameEdit.text.toString())
            findNavController().navigate(navR.id.fragment_grocery_list)
        }

        binding.nameEdit.addTextChangedListener(object : SimpleTextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.addBtn.isEnabled = !s.isNullOrEmpty()
            }
        })
    }

    private fun inject(context: Context) = DaggerGroceryListComponent.builder()
        .groceryListRepoModule(GroceryListRepoModule(context))
        .dbModule(DbModule(context))
        .build()
        .inject(this)

}