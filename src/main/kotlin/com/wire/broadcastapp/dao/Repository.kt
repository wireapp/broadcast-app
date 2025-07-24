package com.wire.broadcastapp.dao

import com.wire.integrations.jvm.model.QualifiedId
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object Repository {
    fun QualifiedId.isAuthorized(): Boolean =
        transaction {
            Senders.selectAll().where {
                (Senders.userId eq this@isAuthorized.id) and
                    (Senders.userDomain eq domain)
            }.any()
        }
}
