/*
 * Copyright (C) 2021-2023 Miku UI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.odin.parts.powershare

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

class PowerShareTileService : TileService() {
    private val mTile: Tile by lazy { qsTile }
    override fun onStartListening() {
        PowerShareUtils.getPowerShareStatus().apply {
            when(this) {
                PowerShareUtils.POWER_SHARE_ON -> mTile.state = Tile.STATE_ACTIVE
                PowerShareUtils.POWER_SHARE_OFF -> mTile.state = Tile.STATE_INACTIVE
                else -> mTile.state = Tile.STATE_UNAVAILABLE
            }
        }
        mTile.updateTile()
        super.onStartListening()
    }

    override fun onClick() {
        PowerShareUtils.getPowerShareStatus().apply {
            when(this) {
                PowerShareUtils.POWER_SHARE_ON -> {
                    PowerShareUtils.setPowerShareStatus(PowerShareUtils.POWER_SHARE_OFF)
                    mTile.state = Tile.STATE_INACTIVE
                }
                PowerShareUtils.POWER_SHARE_OFF -> {
                    PowerShareUtils.setPowerShareStatus(PowerShareUtils.POWER_SHARE_ON)
                    mTile.state = Tile.STATE_ACTIVE
                }
                else -> mTile.state = Tile.STATE_UNAVAILABLE
            }
        }
        mTile.updateTile()
        super.onClick()
    }
}
