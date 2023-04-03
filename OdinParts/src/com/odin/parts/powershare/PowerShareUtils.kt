package com.odin.parts.powershare

import android.util.Log

import com.android.internal.util.miku.FileUtils // Import FileUtils from Miku UI for now.

class PowerShareUtils {
    companion object {
        private const val TAG = "PowerShareUtils"
        const val POWER_SHARE_KEY = "power_wireless_share_key"
        const val POWER_SHARE_NODE = "/sys/class/qcom-battery/reverse_chg_mode"
        const val POWER_SHARE_ON = "1"
        const val POWER_SHARE_OFF = "0"
        const val POWER_SHARE_NOT_SUPPORT = "-1"

        fun getPowerShareStatus(): String {
            FileUtils.fileExists(POWER_SHARE_NODE).apply {
                if (this) {
                    when (FileUtils.readOneLine(POWER_SHARE_NODE)) {
                        POWER_SHARE_ON -> return POWER_SHARE_ON
                        POWER_SHARE_OFF -> return POWER_SHARE_OFF
                    }
                } else {
                    Log.e(TAG, "Device do not support wireless reverse charging!")
                }
            }
            return POWER_SHARE_NOT_SUPPORT
        }

        fun setPowerShareStatus(status: String): Boolean {
            return FileUtils.writeLine(POWER_SHARE_NODE, status)
        }
    }
}
