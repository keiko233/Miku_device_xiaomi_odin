package com.odin.parts.touch

import android.util.Log

import com.android.internal.util.miku.FileUtils // Import FileUtils from Miku UI for now.

class TouchUtils {
    companion object {
        private const val TAG = "TouchUtils"
        const val TOUCH_POLLING_RATE_KEY = "touch_polling_rate"
        const val TOUCH_POLLING_RATE_NODE = "/sys/devices/virtual/touch/touch_dev/bump_sample_rate"
        const val HIGH_POLLING_RATE_ON = "1"
        const val HIGH_POLLING_RATE_OFF = "0"
        const val HIGH_POLLING_RATE_NOT_SUPPORT = "-1"

        fun getTouchStatus(): String {
            FileUtils.fileExists(TOUCH_POLLING_RATE_NODE).apply {
                if (this) {
                    when (FileUtils.readOneLine(TOUCH_POLLING_RATE_NODE)) {
                        HIGH_POLLING_RATE_ON -> return HIGH_POLLING_RATE_ON
                        HIGH_POLLING_RATE_OFF -> return HIGH_POLLING_RATE_OFF
                    }
                } else {
                    Log.e(TAG, "Kernel do not support High touch polling rate!")
                }
            }
            return HIGH_POLLING_RATE_NOT_SUPPORT
        }

        fun setTouchStatus(status: String): Boolean {
            return FileUtils.writeLine(TOUCH_POLLING_RATE_NODE, status)
        }
    }
}
