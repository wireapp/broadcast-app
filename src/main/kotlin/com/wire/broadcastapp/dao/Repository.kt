package com.wire.broadcastapp.dao

import com.wire.integrations.jvm.model.QualifiedId
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class Repository {
    fun isSenderAuthorized(userId: QualifiedId): Boolean =
        transaction {
            Senders.selectAll().where {
                (Senders.userId eq userId.id) and
                    (Senders.userDomain eq userId.domain)
            }.any()
        }
}
