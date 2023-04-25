package com.odin.parts.display

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.Preference.OnPreferenceChangeListener
import androidx.preference.PreferenceFragment
import androidx.preference.SwitchPreference

import com.odin.parts.R

class DcDimmingSettingsFragment : PreferenceFragment() {
    private var mDcDimmingPreference: SwitchPreference? = null
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.dc_dimming_settings)
        mDcDimmingPreference = findPreference(DcUtils.DC_DIMMING_KEY) as SwitchPreference?

        mDcDimmingPreference?.apply { 
            if (!DcUtils.getDcStatus().equals(DcUtils.DC_NOT_SUPPORT)) {
                isEnabled = true
                setOnPreferenceChangeListener { preference, any ->
                    if (DcUtils.DC_DIMMING_KEY.equals(preference.key)) {
                        if (!DcUtils.setDcStatus(if (any as Boolean) DcUtils.DC_ON else DcUtils.DC_OFF)) {
                            Log.e(TAG, "Failed to set DC node!")
                            Toast.makeText(context, R.string.dc_dimming_failed, Toast.LENGTH_SHORT).show()
                            isChecked = false
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
        private const val TAG = "DcDimmingSettingsFragment"
    }

}
