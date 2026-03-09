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

package com.wire.broadcastapp.dao

import org.jetbrains.exposed.v1.core.dao.id.CompositeIdTable

private const val DOMAIN_LENGTH = 255

object Senders : CompositeIdTable("senders") {
    val userId = uuid("user_id").entityId()
    val userDomain = varchar("user_domain", DOMAIN_LENGTH).entityId()

    override val primaryKey: PrimaryKey = PrimaryKey(userId, userDomain)
}
