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
import ru.virtual.core_db.DbModule
import ru.virtual.core_navigation.R
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

    private var listId: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject(context)
    }

    override fun getStartData() {
        listId = arguments?.getInt("listId")
    }

    override fun setUpViews(view: View) {
        binding.addBtn.setOnClickListener{
            viewModel.addProduct(binding.nameEdit.text.toString())
        }

        binding.nameEdit.addTextChangedListener(object : SimpleTextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.addBtn.isEnabled = !s.isNullOrEmpty()
            }
        })

        binding.backBtn.setOnClickListener{ findNavController().navigateUp() }

        binding.nameRecycler.also {
            it.adapter = exampleNameAdapter
            it.addItemMargins(0, 16)
        }

        exampleNameAdapter.addItems(exampleNames)

        exampleNameAdapter.setOnItemClick { name ->
            binding.nameEdit.setText(name)
        }
    }

    override fun setUpObservers() {
        viewModel.addedProductId.observe(viewLifecycleOwner) { productId ->
            listId?.let { viewModel.addGrocery(it, productId.toInt()) }
            findNavController().navigate(R.id.fragment_groceries, bundleOf("listId" to listId))
        }
    }

    private fun inject(context: Context) = DaggerGroceryListComponent.builder()
        .groceryListRepoModule(GroceryListRepoModule(context))
        .dbModule(DbModule(context))
        .build()
        .inject(this)


}