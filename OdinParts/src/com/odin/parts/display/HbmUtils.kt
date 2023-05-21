package com.odin.parts.display

import android.util.Log

import com.android.internal.util.miku.FileUtils // Import FileUtils from Miku UI for now.

class HbmUtils {
    companion object {
        private const val TAG = "HbmUtils"
        const val HBM_KEY = "hbm"
        const val HBM_NODE = "/sys/devices/virtual/mi_display/disp_feature/disp-DSI-0/disp_param"
        const val HBM_ON = "01 01"
        const val HBM_OFF = "01 00"
        const val HBM_NOT_SUPPORT = "0"
        const val HBM_SUPPORT = "1"

        fun getHbmSupportStatus(): String {
            FileUtils.fileExists(HBM_NODE).apply {
                if (this) {
                    return HBM_SUPPORT
                } else {
                    Log.e(TAG, "Kernel do not support HBM!")
                }
            }
            return HBM_NOT_SUPPORT
        }

        fun setHbmStatus(status: String): Boolean {
            return FileUtils.writeLine(HBM_NODE, status)
        }
    }
}
