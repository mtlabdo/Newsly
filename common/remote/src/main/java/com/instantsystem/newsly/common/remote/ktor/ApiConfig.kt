package com.instantsystem.newsly.common.remote.ktor

interface NetworkConfig {
    val baseUrl: String
    val timeout: Long
    val isDebug: Boolean
}