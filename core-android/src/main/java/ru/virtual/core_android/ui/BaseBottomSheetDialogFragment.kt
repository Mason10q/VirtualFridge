package ru.virtual.core_android.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.virtual.core_android.ui.utils.inflateBinding
import ru.virtual.core_res.R

private const val COLLAPSED_HEIGHT = 300

abstract class BaseBottomSheetDialogFragment<VB: ViewBinding>(private val bindingClass: Class<VB>): BottomSheetDialogFragment() {

    private var _binding: VB? = null
    /** Should be called between [onCreateView] and [onDestroyView] */
    protected val binding get() = _binding!!

    private var onDismissListener: (() -> Unit)? = null

    override fun getTheme(): Int = R.style.BottomDialog

    override fun onStart() {
        super.onStart()

        val density = context?.resources?.displayMetrics?.density ?: 0.0f

        dialog?.let {
            val bottomSheet = it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)

            behavior.peekHeight = (COLLAPSED_HEIGHT * density).toInt()
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

    }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = bindingClass.inflateBinding(inflater, container).also { _binding = it }?.root ?: throw IllegalStateException("Failed to instantiate binding")

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpObservers()
        getStartData()
        setUpViews(view)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.invoke()
    }

    /** Get data from navigation bundle or something like this */
    protected open fun getStartData() { }

    /** Here you can set up text, click listeners etc */
    protected open fun setUpViews(view: View) { }

    /** Here you can set up observers for [LiveData] or anything other observable */
    protected open fun setUpObservers() { }

    fun setOnDismissListener(listener: () -> Unit) {
        onDismissListener = listener
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}