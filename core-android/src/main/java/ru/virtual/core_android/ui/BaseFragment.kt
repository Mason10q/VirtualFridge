package ru.virtual.core_android.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ru.virtual.core_android.ui.utils.inflateBinding

abstract class BaseFragment<VB: ViewBinding> (private val bindingClass: Class<VB>) : Fragment() {

    private var _binding: VB? = null
    /** Should be called between [onCreateView] and [onDestroyView] */
    protected val binding get() = _binding!!

    private var receiver: BroadcastReceiver? = null



    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = bindingClass.inflateBinding(inflater, container).also { _binding = it }?.root ?: throw IllegalStateException("Failed to instantiate binding")

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getStartData()
        setUpViews(view)
        setUpObservers()
        receiver = makeBroadcastReceiver()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @CallSuper
    override fun onResume() {
        super.onResume()
        context?.registerReceiver(receiver, IntentFilter("ru.virtual.action.INTERNET_MODE_CHANGED"), Context.RECEIVER_EXPORTED)
    }

    /** Get data from navigation bundle or something like this */
    protected open fun getStartData() { }

    /** Here you can set up text, click listeners etc */
    protected open fun setUpViews(view: View) { }

    /** Here you can set up observers for [LiveData] or anything other observable */
    protected open fun setUpObservers() { }

    /** Refresh data from server or db */
    protected open fun refreshData() {}


    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        context?.unregisterReceiver(receiver)
    }

    private fun makeBroadcastReceiver() = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Toast.makeText(context, intent?.getStringExtra("ru.virtual.broadcast.MESSAGE"), Toast.LENGTH_LONG).show()
            refreshData()
        }
    }
}