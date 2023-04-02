package com.odin.parts.fastcharge

import android.os.SystemProperties
import android.util.Log

class ChargeUtils {
    companion object {
        private const val TAG = "ChargeUtils"
        const val FAST_CHARGE_KEY = "fast_charge_key"
        const val FAST_CHARGE_PROP = "persist.vendor.fastchg_enabled"
        const val FAST_CHARGE_ON = "1"
        const val FAST_CHARGE_OFF = "0"
        const val FAST_CHARGE_NOT_SUPPORT = "-1"

        fun getFastChargeStatus(): String {
            SystemProperties.get(FAST_CHARGE_PROP).apply {
                if (equals(FAST_CHARGE_ON) || equals(FAST_CHARGE_OFF)) {
                    return this
                } else {
                    Log.e(TAG, "Device do not support fast charge!")
                }
            }
            return FAST_CHARGE_NOT_SUPPORT
        }

        fun setFastChargeStatus(status: String) {
            SystemProperties.set(FAST_CHARGE_PROP, status)
        }
    }
}
