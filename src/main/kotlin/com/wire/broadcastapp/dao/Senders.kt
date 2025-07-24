package com.wire.broadcastapp.dao

import org.jetbrains.exposed.v1.core.Table

internal const val UUID_LENGTH = 36

object Senders : Table("senders") {
    val userId = varchar("user_id", UUID_LENGTH).uniqueIndex()
}
