package ru.virtual.feature_auth.presentation

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.virtual.core_android.toTimeFormat
import ru.virtual.core_android.ui.BaseFragment
import ru.virtual.feature_auth.di.DaggerAuthComponent
import ru.virtual.mylibrary.R
import ru.virtual.mylibrary.databinding.FragmentEnterAuthCodeBinding
import javax.inject.Inject
import ru.virtual.core_res.R as resR
import ru.virtual.core_navigation.R as navR

class EnterCodeFragment :
    BaseFragment<FragmentEnterAuthCodeBinding>(FragmentEnterAuthCodeBinding::class.java) {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<AuthViewModel> { viewModelFactory }

    private var email: String? = null

    private var sp: SharedPreferences? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
        sp = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    }

    override fun getStartData() {
        email = arguments?.getString("user_email")
    }

    override fun setUpViews(view: View) {
        setUpTimer()

        with(binding) {
            codeEdit.setCompleteListener { completed -> continueBtn.isEnabled = completed }

            continueBtn.setOnClickListener {
                email?.let { email -> viewModel.checkCode(email, codeEdit.text) }
            }

            codeHint.text = context?.getString(R.string.screen_enter_code_hint, email)

            header.title.text = context?.getString(R.string.screen_enter_code_title)
            header.backBtn.setOnClickListener { findNavController().navigateUp() }

            viewModel.startTimer()
        }
    }

    private fun setUpTimer() {
        val typedValue = TypedValue()
        context?.theme?.resolveAttribute(resR.attr.text_primary, typedValue, true)

        viewModel.setOnTimerTick { currentMilly ->
            binding.timer.text = HtmlCompat.fromHtml(
                context?.getString(
                    R.string.screen_enter_code_timer,
                    typedValue.data.toString(),
                    currentMilly.toTimeFormat()) ?: "",
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }

        viewModel.setOnTimerFinish {
            binding.timer.text = HtmlCompat.fromHtml(
                context?.getString(
                    R.string.screen_enter_code_send_code,
                    typedValue.data.toString()
                ) ?: "",
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )

            binding.timer.setOnClickListener{ _ ->
                email?.let { viewModel.sendMessageToEmail(it) }
                binding.codeEdit.text = ""
                viewModel.startTimer()
                binding.timer.setOnClickListener(null)
            }
        }
    }

    override fun setUpObservers() {
        viewModel.isVerified.observe(viewLifecycleOwner) { verified ->
            if(verified.isVerified) {
                sp?.edit()?.putInt("familyId", verified.familyId)?.apply()
                findNavController().navigate(navR.id.fragment_settings)
            } else {
                Toast.makeText(context, "Код неверный", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun inject() = DaggerAuthComponent.builder()
        .build()
        .inject(this)
}