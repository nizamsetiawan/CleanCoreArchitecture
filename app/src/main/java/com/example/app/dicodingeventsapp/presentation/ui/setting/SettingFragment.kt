package com.example.app.dicodingeventsapp.presentation.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.app.dicodingeventsapp.core.CoreFragment
import com.example.app.dicodingeventsapp.data.store.ThemePreferences
import com.example.app.dicodingeventsapp.databinding.FragmentSettingBinding
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.coroutines.launch

class SettingFragment : CoreFragment<FragmentSettingBinding>() {
    private lateinit var themePreferences: ThemePreferences
    private lateinit var switchTheme: SwitchMaterial

    override fun setupFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        themePreferences = ThemePreferences(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        switchTheme = binding.switchTheme

        lifecycleScope.launch {
            themePreferences.darkModeState.collect { isDarkMode ->
                switchTheme.isChecked = isDarkMode
            }
        }

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                themePreferences.saveDarkModeState(isChecked)
                updateTheme(isChecked)
            }
        }
    }

    private fun updateTheme(isDarkMode: Boolean) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
