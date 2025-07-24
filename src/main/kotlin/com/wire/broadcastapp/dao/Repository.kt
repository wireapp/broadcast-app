package com.wire.broadcastapp.dao

import java.util.UUID
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object Repository {
    fun isAuthorized(userId: UUID): Boolean =
        transaction { Senders.selectAll().where { Senders.userId eq userId.toString() }.any() }
}
