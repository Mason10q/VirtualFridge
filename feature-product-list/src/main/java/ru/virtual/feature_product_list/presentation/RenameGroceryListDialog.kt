package ru.virtual.feature_product_list.presentation

import android.content.Context
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import ru.virtual.core_android.ui.BaseBottomSheetDialogFragment
import ru.virtual.core_android.ui.SimpleTextWatcher
import ru.virtual.core_db.DbModule
import ru.virtual.feature_product_list.databinding.DialogRenameGroceryListBinding
import ru.virtual.feature_product_list.di.DaggerGroceryListComponent
import ru.virtual.feature_product_list.di.GroceryListRepoModule
import javax.inject.Inject

class RenameGroceryListDialog :
    BaseBottomSheetDialogFragment<DialogRenameGroceryListBinding>(DialogRenameGroceryListBinding::class.java) {

    @Inject
    lateinit var viewmodelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<GroceryListViewModel> { viewmodelFactory }

    private var listId: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject(context)
    }

    override fun setUpViews(view: View) {

        binding.backBtn.setOnClickListener{ dialog?.dismiss() }

        binding.saveBtn.setOnClickListener{ _ ->
            listId?.let { viewModel.renameGroceryList(it, binding.newNameEdit.text.toString()) }
            dialog?.dismiss()
        }

        binding.newNameEdit.addTextChangedListener(object : SimpleTextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.saveBtn.isEnabled = !s.isNullOrEmpty()
            }
        })
    }

    override fun getStartData() {
        listId = arguments?.getInt("listId")
        listId?.let { viewModel.getGroceryList(it) }
    }

    override fun setUpObservers() {
        viewModel.groceryList.observe(viewLifecycleOwner) { groceryList ->
            binding.newNameEdit.setText(groceryList.name)
        }
    }

    fun inject(context: Context) = DaggerGroceryListComponent.builder()
        .groceryListRepoModule(GroceryListRepoModule(context))
        .dbModule(DbModule(context))
        .build()
        .inject(this)

}