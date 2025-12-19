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
