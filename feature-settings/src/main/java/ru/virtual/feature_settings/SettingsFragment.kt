package ru.virtual.feature_settings

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.View
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
        with(binding) {
            Log.d("asd", sp?.getInt("familyId", -1).toString())
            if(sp?.getInt("familyId", -1)?.let { it > 0 } == true) {
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

            aboutAppBtn.setOnClickListener{ findNavController().navigate(navR.id.fragment_about_app) }
        }
    }

}