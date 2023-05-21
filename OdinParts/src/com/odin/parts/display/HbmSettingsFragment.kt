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

class HbmSettingsFragment : PreferenceFragment() {
    private var mHbmPreference: SwitchPreference? = null
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.hbm_settings)
        mHbmPreference = findPreference(HbmUtils.HBM_KEY) as SwitchPreference?

        mHbmPreference?.apply { 
            if (!HbmUtils.getHbmSupportStatus().equals(HbmUtils.HBM_NOT_SUPPORT)) {
                isEnabled = true
                setOnPreferenceChangeListener { preference, any ->
                    if (HbmUtils.HBM_KEY.equals(preference.key)) {
                        if (!HbmUtils.setHbmStatus(if (any as Boolean) HbmUtils.HBM_ON else HbmUtils.HBM_OFF)) {
                            Log.e(TAG, "Failed to set HBM node!")
                            Toast.makeText(context, R.string.hbm_failed, Toast.LENGTH_SHORT).show()
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
        private const val TAG = "HbmSettingsFragment"
    }

}
