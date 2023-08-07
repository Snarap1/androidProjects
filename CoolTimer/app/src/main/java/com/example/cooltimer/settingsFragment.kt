package com.example.cooltimer

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.preference.CheckBoxPreference
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import java.lang.NumberFormatException

class settingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener,Preference.OnPreferenceChangeListener{

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.timer_preferences)
        val sharedPreferences = preferenceScreen.sharedPreferences
        preferenceScreen = preferenceScreen

        val count = preferenceScreen.preferenceCount
        for (i in 0 until count){
            val preference = preferenceScreen.getPreference(i)
            if(preference !is CheckBoxPreference){
                val value = sharedPreferences?.getString(preference.key,"")
                setPreferenceLabel(preference, value!!)
            }
        }
            val preference = findPreference<Preference>("default_interval")
        preference!!.onPreferenceChangeListener = this
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        val preference = findPreference<Preference>(key!!)
        if (preference !is CheckBoxPreference){
            val value = sharedPreferences.getString(preference!!.key,"")
            setPreferenceLabel(preference, value!!)
        }
    }

    private  fun setPreferenceLabel(preference:Preference, value:String ){
        if(preference is ListPreference){
            val listPreference:ListPreference = preference
            val index = listPreference.findIndexOfValue(value)
            if(index>= 0){
                listPreference.summary = listPreference.entries[index]
            }
        }else if (preference is EditTextPreference){
            preference.summary = value
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceScreen.sharedPreferences!!.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        preferenceScreen.sharedPreferences!!.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
        if(preference.key.equals("default_interval")){
            val defaultIntervalString = newValue.toString()
            try {
                var defaultInterval =    defaultIntervalString.toInt()
            }catch (nef:NumberFormatException)
            {
                Toast.makeText(context, "enter a correct integer number", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }

}