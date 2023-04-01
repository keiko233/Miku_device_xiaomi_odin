package com.odin.parts.touch

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.preference.Preference
import androidx.preference.Preference.OnPreferenceChangeListener
import androidx.preference.PreferenceFragment
import androidx.preference.SwitchPreference

import com.odin.parts.R

class TouchSettingsFragment : PreferenceFragment() {
    private var mTouchPreference: SwitchPreference? = null
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.touch_polling_rate_settings)
        mTouchPreference = findPreference(TouchUtils.TOUCH_POLLING_RATE_KEY) as SwitchPreference?

        mTouchPreference?.apply { 
            if (!TouchUtils.getTouchStatus().equals(TouchUtils.HIGH_POLLING_RATE_NOT_SUPPORT)) {
                isEnabled = true
                setOnPreferenceChangeListener { preference, any ->
                    if (TouchUtils.TOUCH_POLLING_RATE_KEY.equals(preference.key)) {
                        if (!TouchUtils.setTouchStatus(if (any as Boolean) TouchUtils.HIGH_POLLING_RATE_ON else TouchUtils.HIGH_POLLING_RATE_OFF)) {
                            Log.e(TAG, "Failed to set High touch poling rate node!")
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
        private const val TAG = "TouchSettingsFragment"
    }

}
