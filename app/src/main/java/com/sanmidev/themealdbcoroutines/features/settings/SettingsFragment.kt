package com.sanmidev.themealdbcoroutines.features.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.sanmidev.themealdbcoroutines.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}