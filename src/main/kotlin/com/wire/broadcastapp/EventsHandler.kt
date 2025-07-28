package com.wire.broadcastapp

import com.wire.broadcastapp.dao.Repository
import com.wire.integrations.jvm.WireEventsHandlerSuspending
import com.wire.integrations.jvm.model.WireMessage

class EventsHandler : WireEventsHandlerSuspending() {
    val repository = Repository()

    val broadcast = BroadcastService(repository)

    override suspend fun onMessage(wireMessage: WireMessage.Text) {
        broadcast.handleMessage(wireMessage)
    }
}
