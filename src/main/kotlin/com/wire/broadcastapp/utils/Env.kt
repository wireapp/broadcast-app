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

package com.wire.broadcastapp.utils

import java.util.UUID

private fun getEnvOrDefault(
    name: String,
    default: String
): String = System.getenv(name) ?: default

object Env {
    /**
     * Application ID received when Onboarding the App.
     */
    val APPLICATION_ID: UUID = UUID.fromString(
        getEnvOrDefault("APP_ID", UUID.randomUUID().toString())
    )

    /**
     * API Token received when Onboarding the App.
     */
    val API_TOKEN: String = getEnvOrDefault("API_TOKEN", "dummyApiToken")

    /**
     * API Host to be used as a backend contact point for the SDK.
     */
    val API_HOST: String =
        getEnvOrDefault("API_HOST", "https://staging-nginz-https.zinfra.io")

    /**
     * Cryptography storage password
     * Used when setting up the user and client database.
     * If lost or forgotten, there is no future access to the database.
     * It must be exactly 32 characters long
     */
    val CRYPTOGRAPHY_STORAGE_PASSWORD: String =
        getEnvOrDefault("CRYPTOGRAPHY_STORAGE_PASSWORD", "myDummyPasswordOfRandom32BytesCH")
}

object PostgresEnv {
    val DB_USER: String =
        getEnvOrDefault("POSTGRES_USER", "broadcast-app")
    val DB_PASSWORD: String =
        getEnvOrDefault("POSTGRES_PASSWORD", "super-secret-pwd")
    val DB_URL: String =
        getEnvOrDefault("POSTGRES_URL", "jdbc:postgresql://localhost:5432/broadcast-app")
}
