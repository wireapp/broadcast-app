package com.wire.broadcastapp

import com.wire.broadcastapp.dao.DatabaseFactory
import com.wire.broadcastapp.dao.Repository
import com.wire.broadcastapp.utils.Env
import com.wire.integrations.jvm.WireAppSdk
import com.wire.integrations.jvm.WireEventsHandlerSuspending
import com.wire.integrations.jvm.model.WireMessage

fun main() {
    DatabaseFactory.init()
    val repository = Repository()

    val wireAppSdk = WireAppSdk(
        applicationId = Env.APPLICATION_ID,
        apiToken = Env.API_TOKEN,
        apiHost = Env.API_HOST,
        cryptographyStoragePassword = Env.CRYPTOGRAPHY_STORAGE_PASSWORD,
        wireEventsHandler = object : WireEventsHandlerSuspending() {
            private val broadcast = BroadcastService(manager, repository)

            override suspend fun onMessage(wireMessage: WireMessage.Text) {
                broadcast.handleMessage(wireMessage)
            }
        }
    )

    wireAppSdk.startListening()
}
