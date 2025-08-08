package com.instantsystem.app.feature.home.ui

import com.instantsystem.newsly.app.common.ui.base.BaseEvent

sealed interface HomeEvent : BaseEvent {
    data object LoadNews : HomeEvent
    data object RefreshNews : HomeEvent

}