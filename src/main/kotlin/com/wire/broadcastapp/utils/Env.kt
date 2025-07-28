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
        getEnvOrDefault("API_HOST", "https://nginz-https.chala.wire.link")

    /**
     * Cryptography storage password
     * Used when setting up the user and client database.
     * If lost or forgotten, there is no future access to the database.
     * It must be exactly 32 characters long
     */
    val CRYPTOGRAPHY_STORAGE_PASSWORD: String =
        getEnvOrDefault("CRYPTOGRAPHY_STORAGE_PASSWORD", "")
}

object PostgresEnv {
    val DB_USER: String =
        getEnvOrDefault("POSTGRES_USER", "broadcast-app")
    val DB_PASSWORD: String =
        getEnvOrDefault("POSTGRES_PASSWORD", "super-secret-pwd")
    val DB_URL: String =
        getEnvOrDefault("POSTGRES_URL", "jdbc:postgresql://localhost:5432/broadcast-app")
}
