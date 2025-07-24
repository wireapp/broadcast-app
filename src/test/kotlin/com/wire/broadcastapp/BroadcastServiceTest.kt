package com.wire.broadcastapp

import com.wire.broadcastapp.dao.Repository
import com.wire.integrations.jvm.model.ConversationData
import com.wire.integrations.jvm.model.QualifiedId
import com.wire.integrations.jvm.model.WireMessage
import com.wire.integrations.jvm.model.http.user.UserResponse
import com.wire.integrations.jvm.service.WireApplicationManager
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class BroadcastServiceTest {
    val managerMock = mockk<WireApplicationManager> {
        coEvery { sendMessageSuspending(any()) } returns UUID.randomUUID()
    }
    val repositoryMock = mockk<Repository>()
    val broadcast = BroadcastService(managerMock, repositoryMock)

    fun stubConversationId() = QualifiedId(UUID.randomUUID(), "test.domain")

    fun createWireMessage(text: String) = WireMessage.Text.create(stubConversationId(), text)

    @Test
    fun `when message is not a command, then ignore it`() {
        // arrange
        val wireMessage = createWireMessage("this is a test message")

        // act
        runBlocking {
            broadcast.handleMessage(wireMessage)
        }

        // assert
        coVerify {
            managerMock wasNot Called
            repositoryMock wasNot Called
        }
    }

    private val commandMessage = createWireMessage("/broadcast test")

    @Test
    fun `when authorized user sends message, then broadcast message`() {
        // arrange
        coEvery { repositoryMock.isSenderAuthorized(any()) } returns true
        val conversationsMock = listOf(
            mockk<ConversationData> {
                every { id } returns stubConversationId()
            },
            mockk<ConversationData> {
                every { id } returns commandMessage.conversationId
            }
        )
        coEvery { managerMock.getStoredConversations() } returns conversationsMock
        coEvery { managerMock.getUserSuspending(any()) } returns mockk<UserResponse> {
            every { name } returns "user"
        }

        // act
        runBlocking {
            broadcast.handleMessage(commandMessage)
        }

        // assert
        coVerify {
            repositoryMock.isSenderAuthorized(commandMessage.sender)
            managerMock.getUserSuspending(any())
        }
        coVerify(exactly = conversationsMock.size - 1) {
            managerMock.sendMessageSuspending(
                match<WireMessage.Text> { it.text == "user: test" }
            )
        }
    }

    @Test
    fun `when unauthorized user sends message, then do not broadcast message`() {
        // arrange
        coEvery { repositoryMock.isSenderAuthorized(any()) } returns false

        // act
        runBlocking {
            broadcast.handleMessage(commandMessage)
        }

        // assert
        coVerify(exactly = 1) {
            repositoryMock.isSenderAuthorized(commandMessage.sender)
            managerMock.sendMessageSuspending(any())
        }
        confirmVerified(repositoryMock, managerMock)
    }
}
