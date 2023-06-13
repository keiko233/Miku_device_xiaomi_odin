package com.odin.parts.display

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

class HbmTileService : TileService() {

    override fun onClick() {
        super.onClick()

        val tile = qsTile ?: return

        if (tile.state == Tile.STATE_ACTIVE) {
            setHbmStatus(HbmUtils.HBM_OFF)
            tile.state = Tile.STATE_INACTIVE
        } else {
            setHbmStatus(HbmUtils.HBM_ON)
            tile.state = Tile.STATE_ACTIVE
        }
        tile.updateTile()
    }

    override fun onStartListening() {
        super.onStartListening()

        val tile = qsTile ?: return
        val hbmStatus = getHbmStatus()

        tile.state = if (hbmStatus == HbmUtils.HBM_ON) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        tile.updateTile()
    }

    private fun getHbmStatus(): String {
        return HbmUtils.getHbmStatus()
    }

    private fun setHbmStatus(status: String) {
        HbmUtils.setHbmStatus(status)
    }
}
