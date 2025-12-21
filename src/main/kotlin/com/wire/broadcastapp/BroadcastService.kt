package com.wire.broadcastapp

import com.wire.broadcastapp.dao.Repository
import com.wire.sdk.model.QualifiedId
import com.wire.sdk.model.WireMessage
import com.wire.sdk.service.WireApplicationManager

class BroadcastService(private val repository: Repository) {
    private lateinit var manager: WireApplicationManager

    companion object {
        private const val COMMAND_PREFIX = "/broadcast"
        private val NOT_AUTHORIZED = """
            ⛔ You’re not authorized to send broadcasts.
            Only approved broadcasters can use the `$COMMAND_PREFIX` command.
        """.trim()
    }

    suspend fun handleMessage(wireMessage: WireMessage.Text) {
        if (!wireMessage.text.startsWith(COMMAND_PREFIX)) return
        if (repository.isSenderAuthorized(wireMessage.sender)) {
            broadcastMessage(wireMessage)
        } else {
            createAndSend(
                conversationId = wireMessage.conversationId,
                text = NOT_AUTHORIZED
            )
        }
    }

    private suspend fun broadcastMessage(wireMessage: WireMessage.Text) {
        val content = wireMessage.text.removePrefix(COMMAND_PREFIX).trim()
        val username = manager.getUserSuspending(wireMessage.sender).name
        val conversations = manager.getStoredConversations()

        conversations
            .filter { it.id != wireMessage.conversationId }
            .forEach { conversation ->
                createAndSend(
                    conversationId = conversation.id,
                    text = "$username: $content"
                )
            }
    }

    private suspend fun createAndSend(
        conversationId: QualifiedId,
        text: String
    ) {
        val message = WireMessage.Text.create(
            conversationId = conversationId,
            text = text
        )
        manager.sendMessageSuspending(message)
    }

    fun initManager(manager: WireApplicationManager) {
        this.manager = manager
    }
}
