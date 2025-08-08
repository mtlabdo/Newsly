package com.instantsystem.common.core

import org.koin.core.qualifier.named

object ConfigConstants {
    // KOIN NAMED KEYS
    val newsApiNamedKey = named("news_api_key")
    val debugModeNamedKey = named("debug_mode")
}