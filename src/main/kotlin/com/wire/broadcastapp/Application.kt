/*
 * Wire
 * Copyright (C) 2026 Wire Swiss GmbH
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses/.
 */

package com.wire.broadcastapp

import com.wire.broadcastapp.dao.DatabaseFactory
import com.wire.broadcastapp.utils.Env
import com.wire.sdk.WireAppSdk

fun main() {
    DatabaseFactory.init()
    val eventsHandler = EventsHandler()

    val wireAppSdk = WireAppSdk(
        applicationId = Env.APPLICATION_ID,
        apiToken = Env.API_TOKEN,
        apiHost = Env.API_HOST,
        cryptographyStoragePassword = Env.CRYPTOGRAPHY_STORAGE_PASSWORD,
        wireEventsHandler = eventsHandler
    )

    eventsHandler.broadcast.initManager(wireAppSdk.getApplicationManager())
    wireAppSdk.startListening()
}
