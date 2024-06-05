package ru.virtual.feature_settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.virtual.feature_settings.databinding.FragmentAboutAppBinding

class AboutAppFragment: Fragment() {

    private var _binding: FragmentAboutAppBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentAboutAppBinding.inflate(layoutInflater).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.header.title.text = context?.getString(R.string.screen_about_app_title)
        binding.header.backBtn.setOnClickListener{ findNavController().navigateUp() }
    }
}