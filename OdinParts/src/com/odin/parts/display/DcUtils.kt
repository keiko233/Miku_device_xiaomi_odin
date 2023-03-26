package com.odin.parts.display

import android.util.Log

import com.android.internal.util.miku.FileUtils // Import FileUtils from Miku UI for now.

class DcUtils {
    companion object {
        private const val TAG = "DcUtils"
        const val DC_DIMMING_KEY = "dc_dimming"
        const val DC_DIMMING_NODE = "/sys/devices/virtual/mi_display/disp_feature/disp-DSI-0/disp_param"
        const val DC_ON = "08 01"
        const val DC_OFF = "08 00"
        const val DC_NOT_SUPPORT = "0"
        const val DC_SUPPORT = "1"

        fun getDcStatus(): String {
            FileUtils.fileExists(DC_DIMMING_NODE).apply {
                if (this) {
                    return DC_SUPPORT
                } else {
                    Log.e(TAG, "Kernel do not support DC Dimming!")
                }
            }
            return DC_NOT_SUPPORT
        }

        fun setDcStatus(status: String): Boolean {
            return FileUtils.writeLine(DC_DIMMING_NODE, status)
        }
    }
}
