package com.odin.parts.powershare

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceFragment
import androidx.preference.SwitchPreference

import com.odin.parts.R

class PowerShareSettingsFragment : PreferenceFragment() {
    private var mPowerSharePreference: SwitchPreference? = null
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.power_share_settings)
        mPowerSharePreference = findPreference(PowerShareUtils.POWER_SHARE_KEY) as SwitchPreference?

        mPowerSharePreference?.apply { 
            if (!PowerShareUtils.getPowerShareStatus().equals(PowerShareUtils.POWER_SHARE_NOT_SUPPORT)) {
                isEnabled = true
                setOnPreferenceChangeListener { preference, any ->
                    if (PowerShareUtils.POWER_SHARE_KEY.equals(preference.key)) {
                        if (!PowerShareUtils.setPowerShareStatus(if (any as Boolean) PowerShareUtils.POWER_SHARE_ON else PowerShareUtils.POWER_SHARE_OFF)) {
                            Log.e(TAG, "Failed to set power share node!")
                        }
                    }
                    true
                }
            } else {
                isEnabled = false
            }
        }
    }

    companion object {
        private const val TAG = "PowerShareSettingsFragment"
    }

}
