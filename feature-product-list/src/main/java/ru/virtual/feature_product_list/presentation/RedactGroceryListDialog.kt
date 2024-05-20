package ru.virtual.feature_product_list.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import ru.virtual.core_android.ui.BaseBottomSheetDialogFragment
import ru.virtual.core_db.DbModule
import ru.virtual.feature_product_list.databinding.DialogGroceryListRedactBinding
import ru.virtual.feature_product_list.databinding.DialogRenameGroceryListBinding
import ru.virtual.feature_product_list.di.DaggerGroceryListComponent
import ru.virtual.feature_product_list.di.GroceryListRepoModule
import javax.inject.Inject

class RedactGroceryListDialog :
    BaseBottomSheetDialogFragment<DialogGroceryListRedactBinding>(DialogGroceryListRedactBinding::class.java) {

    @Inject
    lateinit var viewmodelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<GroceryListViewModel> { viewmodelFactory }

    private var listId: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject(context)
    }

    override fun setUpViews(view: View) {

        binding.rename.setOnClickListener { _ ->
            RenameGroceryListDialog().apply {
                arguments = bundleOf("listId" to listId)
            }.show(parentFragmentManager, "")
        }

        binding.share.setOnClickListener { _ ->
            context?.let { context ->
                listId?.let { listId ->
                    viewModel.shareGroceryList(context, listId)
                }
            }
            dialog?.dismiss()
        }

        binding.remove.setOnClickListener { _ ->
            listId?.let { viewModel.removeGroceryList(it) }
            dialog?.dismiss()
        }
    }

    override fun getStartData() {
        listId = arguments?.getInt("listId")
    }

    fun inject(context: Context) = DaggerGroceryListComponent.builder()
        .groceryListRepoModule(GroceryListRepoModule(context))
        .dbModule(DbModule(context))
        .build()
        .inject(this)

}