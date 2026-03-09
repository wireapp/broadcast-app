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

import com.wire.broadcastapp.BroadcastService.Companion.COMMAND_PREFIX
import com.wire.broadcastapp.dao.Repository
import com.wire.sdk.WireEventsHandlerSuspending
import com.wire.sdk.model.ConversationData
import com.wire.sdk.model.ConversationMember
import com.wire.sdk.model.WireMessage

class EventsHandler : WireEventsHandlerSuspending() {
    val repository = Repository()

    val broadcast = BroadcastService(repository)

    override suspend fun onTextMessageReceived(wireMessage: WireMessage.Text) {
        broadcast.handleMessage(wireMessage)
    }

    override suspend fun onAppAddedToConversation(
        conversation: ConversationData,
        members: List<ConversationMember>
    ) {
        val welcomeMessage = WireMessage.Text.create(
            conversationId = conversation.id,
            text = WELCOME_TEXT
        )

        manager.sendMessage(welcomeMessage)
    }

    private companion object {
        const val WELCOME_TEXT =
            "👋 Hi, I'm the Broadcast App. Thanks for adding me to the conversation.\n" +
                "You can use me to message multiple conversations simultaneously.\n" +
                "I'm here to help make everyday work a little easier.\n" +
                "Use the `$COMMAND_PREFIX <your message here>` command to get started."
    }
}
