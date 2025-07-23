package com.wire.broadcastapp

import com.wire.integrations.jvm.WireEventsHandlerSuspending
import com.wire.integrations.jvm.model.WireMessage

object EventsHandler : WireEventsHandlerSuspending() {
    private const val COMMAND_PREFIX = "/broadcast "

    override suspend fun onMessage(wireMessage: WireMessage.Text) {
        if (wireMessage.text.startsWith(COMMAND_PREFIX)) {
            broadcastMessage(wireMessage)
        }
    }

    private suspend fun broadcastMessage(wireMessage: WireMessage.Text) {
        val conversations = manager.getStoredConversations()
        val username = manager.getUserSuspending(wireMessage.sender).name
        val content = wireMessage.text.removePrefix(COMMAND_PREFIX)

        conversations
            .filter { it.id != wireMessage.conversationId }
            .forEach { conversation ->
                WireMessage.Text.create(
                    conversationId = conversation.id,
                    text = "$username: $content"
                ).let { manager.sendMessageSuspending(it) }
            }
    }
}
