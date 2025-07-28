package com.wire.broadcastapp.dao

import org.jetbrains.exposed.v1.core.dao.id.CompositeIdTable

private const val DOMAIN_LENGTH = 255

object Senders : CompositeIdTable("senders") {
    val userId = uuid("user_id").entityId()
    val userDomain = varchar("user_domain", DOMAIN_LENGTH).entityId()

    override val primaryKey: PrimaryKey = PrimaryKey(userId, userDomain)
}
