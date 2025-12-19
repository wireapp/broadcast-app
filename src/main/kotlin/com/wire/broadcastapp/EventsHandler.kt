package com.wire.broadcastapp

import com.wire.broadcastapp.dao.Repository
import com.wire.sdk.WireEventsHandlerSuspending
import com.wire.sdk.model.WireMessage

class EventsHandler : WireEventsHandlerSuspending() {
    val repository = Repository()

    val broadcast = BroadcastService(repository)

    override suspend fun onTextMessageReceived(wireMessage: WireMessage.Text) {
        broadcast.handleMessage(wireMessage)
    }
}
