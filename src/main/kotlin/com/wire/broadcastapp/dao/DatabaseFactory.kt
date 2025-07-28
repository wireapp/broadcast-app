package com.wire.broadcastapp.dao

import com.wire.broadcastapp.utils.PostgresEnv
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object DatabaseFactory {
    fun init() {
        Database.connect(
            user = PostgresEnv.DB_USER,
            password = PostgresEnv.DB_PASSWORD,
            url = PostgresEnv.DB_URL,
            driver = "org.postgresql.Driver"
        )

        transaction {
            SchemaUtils.create(Senders)
        }
    }
}
