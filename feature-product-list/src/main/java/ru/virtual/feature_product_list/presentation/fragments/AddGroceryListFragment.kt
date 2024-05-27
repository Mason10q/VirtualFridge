package ru.virtual.feature_product_list.presentation.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.virtual.core_android.ui.BaseFragment
import ru.virtual.core_android.ui.SimpleTextWatcher
import ru.virtual.core_android.ui.utils.addItemMargins
import ru.virtual.core_android.ui.utils.setTextAndSelection
import ru.virtual.core_db.DbModule
import ru.virtual.core_navigation.R as navR
import ru.virtual.feature_product_list.databinding.FragmentAddGroceryListBinding
import ru.virtual.feature_product_list.di.DaggerGroceryListComponent
import ru.virtual.feature_product_list.di.GroceryListRepoModule
import ru.virtual.feature_product_list.presentation.adapters.ExampleNameAdapter
import ru.virtual.feature_product_list.presentation.vm.GroceryListViewModel
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class AddGroceryListFragment: BaseFragment<FragmentAddGroceryListBinding>(FragmentAddGroceryListBinding::class.java) {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<GroceryListViewModel> { viewModelFactory }

    private val exampleNameAdapter = ExampleNameAdapter()

    @SuppressLint("SimpleDateFormat")
    private val exampleNames = listOf("Завтрак", SimpleDateFormat("dd.MM.yyyy").format(Date()), "Выходные", "Обед", "Шашлыки", "На праздник", "Ужин", "День рождения")


    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject(context)
    }

    override fun setUpViews(view: View) {
        setUpAdapter()

        with(binding) {
            addBtn.setOnClickListener {
                viewModel.addGroceryList(nameEdit.text.toString())
                findNavController().navigate(navR.id.fragment_grocery_list)
            }

            nameEdit.addTextChangedListener(object : SimpleTextWatcher {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    addBtn.isEnabled = !s.isNullOrEmpty()
                }
            })

            backBtn.setOnClickListener { findNavController().navigateUp() }

            nameRecycler.also {
                it.adapter = exampleNameAdapter
                it.addItemMargins(0, 16)
            }
        }
    }

    private fun setUpAdapter() {
        with(exampleNameAdapter) {
            addItems(exampleNames)

            setOnItemClick { name ->
                binding.nameEdit.setTextAndSelection(name)
            }
        }
    }

    private fun inject(context: Context) = DaggerGroceryListComponent.builder()
        .groceryListRepoModule(GroceryListRepoModule(context))
        .dbModule(DbModule(context))
        .build()
        .inject(this)

}