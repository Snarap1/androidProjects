package com.example.cooltimer

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class settingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.timer_preferences)
    }
}