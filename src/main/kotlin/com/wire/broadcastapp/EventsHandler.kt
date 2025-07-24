package com.wire.broadcastapp

import com.wire.broadcastapp.dao.Repository.isAuthorized
import com.wire.integrations.jvm.WireEventsHandlerSuspending
import com.wire.integrations.jvm.model.QualifiedId
import com.wire.integrations.jvm.model.WireMessage

object EventsHandler : WireEventsHandlerSuspending() {
    private const val COMMAND_PREFIX = "/broadcast"
    private val NOT_AUTHORIZED = """
        ⛔ You’re not authorized to send broadcasts.
        Only approved broadcasters can use the `$COMMAND_PREFIX` command.
    """.trim()

    override suspend fun onMessage(wireMessage: WireMessage.Text) {
        val content = extractCommandContent(wireMessage.text) ?: return
        if (wireMessage.sender.isAuthorized()) {
            broadcastMessage(
                senderId = wireMessage.sender,
                baseConversationId = wireMessage.conversationId,
                content = content
            )
        } else {
            val message = WireMessage.Text.create(
                conversationId = wireMessage.conversationId,
                text = NOT_AUTHORIZED
            )
            manager.sendMessageSuspending(message)
        }
    }

    private fun extractCommandContent(text: String): String? {
        return if (text.startsWith(COMMAND_PREFIX)) {
            text.removePrefix(COMMAND_PREFIX).trim()
        } else {
            null
        }
    }

    private suspend fun broadcastMessage(
        senderId: QualifiedId,
        baseConversationId: QualifiedId,
        content: String
    ) {
        val conversations = manager.getStoredConversations().filter { it.id != baseConversationId }
        val username = manager.getUserSuspending(senderId).name

        conversations
            .forEach { conversation ->
                WireMessage.Text.create(
                    conversationId = conversation.id,
                    text = "$username: $content"
                ).let { manager.sendMessageSuspending(it) }
            }
    }
}
