package com.instantsystem.newsly.designsystem.resources

import androidx.annotation.StringRes

sealed class UIText {

    data class DynamicString(val value: String) : UIText()

    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ) : UIText()

    object Empty : UIText()
}