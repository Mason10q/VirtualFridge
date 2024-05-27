package ru.virtual.feature_auth.presentation

import android.content.Context
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.virtual.core_android.isValidEmail
import ru.virtual.core_android.ui.BaseFragment
import javax.inject.Inject
import ru.virtual.core_navigation.R as navR
import ru.virtual.core_android.addTextChangedListener
import ru.virtual.feature_auth.di.DaggerAuthComponent
import ru.virtual.mylibrary.R
import ru.virtual.mylibrary.databinding.FragmentEnterEmailBinding

class EnterEmailFragment :
    BaseFragment<FragmentEnterEmailBinding>(FragmentEnterEmailBinding::class.java) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<AuthViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun setUpViews(view: View) {
        with(binding) {
            emailEdit.addTextChangedListener { s: CharSequence?, _: Int, _: Int, _: Int ->
                continueBtn.isEnabled = s?.isValidEmail() ?: false
            }

            header.title.text = context?.getString(R.string.screen_enter_email_title)
            header.backBtn.setOnClickListener{ findNavController().navigateUp() }

            continueBtn.setOnClickListener {
                viewModel.sendMessageToEmail(emailEdit.text.toString())

                findNavController().navigate(
                    navR.id.fragment_enter_code,
                    bundleOf("user_email" to emailEdit.text.toString())
                )
            }
        }
    }

    private fun inject() = DaggerAuthComponent.builder()
        .build()
        .inject(this)

}