package ru.virtual.feature_product_list.presentation.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.virtual.core_android.ui.BaseFragment
import ru.virtual.core_android.ui.SimpleTextWatcher
import ru.virtual.core_android.ui.utils.addItemMargins
import ru.virtual.core_android.ui.utils.setTextAndSelection
import ru.virtual.core_db.DbModule
import ru.virtual.core_navigation.R as navR
import ru.virtual.feature_product_list.databinding.FragmentAddProductBinding
import ru.virtual.feature_product_list.di.DaggerGroceryListComponent
import ru.virtual.feature_product_list.di.GroceryListRepoModule
import ru.virtual.feature_product_list.presentation.adapters.ExampleNameAdapter
import ru.virtual.feature_product_list.presentation.vm.GroceryListViewModel
import ru.virtual.feature_product_list.presentation.vm.GroceryViewModel
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class AddProductFragment: BaseFragment<FragmentAddProductBinding>(FragmentAddProductBinding::class.java) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<GroceryViewModel> { viewModelFactory }

    private val exampleNameAdapter = ExampleNameAdapter()

    @SuppressLint("SimpleDateFormat")
    private val exampleNames = listOf("Молоко", "Хлеб", "Картошка", "Свекла", "Сахар", "Соль", "Мороженое", "Морковь", "Сыр", "Творог")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject(context)
    }

    override fun setUpViews(view: View) {
        setUpAdapter()

        with(binding) {
            addBtn.setOnClickListener {
                viewModel.addProduct(nameEdit.text.toString())
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

    override fun setUpObservers() {
        viewModel.addedProductId.observe(viewLifecycleOwner) { findNavController().navigateUp() }
    }

    private fun inject(context: Context) = DaggerGroceryListComponent.builder()
        .groceryListRepoModule(GroceryListRepoModule(context))
        .dbModule(DbModule(context))
        .build()
        .inject(this)


}