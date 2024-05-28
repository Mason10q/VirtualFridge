package ru.virtual.feature_auth.presentation

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.virtual.core_android.addTextChangedListener
import ru.virtual.core_android.ui.BaseFragment
import ru.virtual.core_android.ui.utils.addItemMargins
import ru.virtual.feature_auth.di.DaggerAuthComponent
import ru.virtual.mylibrary.R
import ru.virtual.mylibrary.databinding.FragmentFamilyAccessBinding
import javax.inject.Inject
import ru.virtual.core_android.addTextChangedListener
import ru.virtual.core_android.isValidEmail
import ru.virtual.feature_auth.di.AuthRepositoryModule

class FamilyAccessFragment: BaseFragment<FragmentFamilyAccessBinding>(FragmentFamilyAccessBinding::class.java) {

    private val adapter = SpaceAdapter()

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<AuthViewModel> { viewModelFactory }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject(context)
    }

    override fun setUpViews(view: View) {
        with(binding) {
            header.title.text = context?.getString(R.string.screen_family_access_title)
            header.backBtn.setOnClickListener{ findNavController().navigateUp() }

            spaceRecycler.also {
                it.adapter = adapter
                it.addItemMargins(0, 16)
            }

            adapter.addItems(arrayListOf(true, true, false, false))

            emptySpaces.text = context?.getString(R.string.screen_family_acess_empty_spaces, 4)

            emailEdit.addTextChangedListener{ s: CharSequence?, _: Int, _: Int, _: Int ->
                sendBtn.isEnabled = s?.isValidEmail() ?: false
            }

            sendBtn.setOnClickListener{
                viewModel.sendFamilyInvite(emailEdit.text.toString())
                Toast.makeText(context, "Запрос отправлен!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun inject(context: Context) = DaggerAuthComponent.builder()
        .authRepoModule(AuthRepositoryModule(context))
        .build()
        .inject(this)

}