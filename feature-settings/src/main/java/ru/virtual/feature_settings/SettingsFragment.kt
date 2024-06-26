package ru.virtual.feature_settings

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import ru.virtual.core_android.ui.BaseFragment
import ru.virtual.feature_settings.databinding.FragmentSettingsBinding
import ru.virtual.core_navigation.R as navR

class SettingsFragment: BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::class.java) {

    private var sp: SharedPreferences? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sp = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    }
    override fun setUpViews(view: View) {
        val isConnected = sp?.getBoolean("connected", false)
        val isLoggedIn = sp?.getInt("familyId", -1)?.let { it > 0 }

        with(binding) {
            authBtn.isVisible =  isConnected ?: false

            binding.familyAccessBtn.setOnClickListener{
                if(isLoggedIn == false) {
                    Toast.makeText(context, "Сначала зарегестрируйтесь!", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                if(isConnected == false) {
                    Toast.makeText(context, "Вы в оффлайн режиме", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                findNavController().navigate(navR.id.fragment_family_access)
            }

            aboutAppBtn.setOnClickListener{ findNavController().navigate(navR.id.fragment_about_app) }

            if(isConnected == false) return

            if(isLoggedIn == true) {
                authBtn.text = context?.getString(R.string.screen_settings_exit)

                authBtn.setOnClickListener{
                    sp?.edit()?.remove("familyId")?.apply()
                    authBtn.text = context?.getString(R.string.screen_settings_auth)
                    authBtn.setOnClickListener{ findNavController().navigate(navR.id.nav_auth) }
                }
            } else {
                authBtn.text = context?.getString(R.string.screen_settings_auth)
                authBtn.setOnClickListener{ findNavController().navigate(navR.id.nav_auth) }
            }

            themeSwitch.setOnClickListener {
                if(themeSwitch.isChecked){
                }
            }
        }
    }

}