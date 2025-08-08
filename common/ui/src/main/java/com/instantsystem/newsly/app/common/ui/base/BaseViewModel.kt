package com.instantsystem.newsly.app.common.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<U : BaseUIState>(
    initialUIState: U
) : ViewModel(), BaseEventHandler {

    private val _uiState = MutableStateFlow(initialUIState)
    val uiState: StateFlow<U> = _uiState.asStateFlow()

    abstract override fun handleEvent(event: BaseEvent)

    protected fun updateUIState(block: (U) -> U) {
        _uiState.value = block.invoke(_uiState.value)
    }
}