package com.odin.parts.fastcharge

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.preference.Preference
import androidx.preference.Preference.OnPreferenceChangeListener
import androidx.preference.PreferenceFragment
import androidx.preference.SwitchPreference

import com.odin.parts.R

class ChargeSettingsFragment : PreferenceFragment() {
    private var mChargePreference: SwitchPreference? = null
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.fast_charge_settings)
        mChargePreference = findPreference(ChargeUtils.FAST_CHARGE_KEY) as SwitchPreference?

        mChargePreference?.apply { 
            if (!ChargeUtils.getFastChargeStatus().equals(ChargeUtils.FAST_CHARGE_NOT_SUPPORT)) {
                isEnabled = true
                setOnPreferenceChangeListener { preference, any ->
                    if (ChargeUtils.FAST_CHARGE_KEY.equals(preference.key)) {
                        ChargeUtils.setFastChargeStatus(if (any as Boolean) ChargeUtils.FAST_CHARGE_ON else ChargeUtils.FAST_CHARGE_OFF)
                    }
                    true
                }
            } else {
                isEnabled = false
            }
        }
    }

    companion object {
        private const val TAG = "ChargeSettingsFragment"
    }

}
