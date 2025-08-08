package com.instantsystem.newsly.app.common.ui.base

fun interface BaseEventHandler {
    fun handleEvent(event: BaseEvent)
}