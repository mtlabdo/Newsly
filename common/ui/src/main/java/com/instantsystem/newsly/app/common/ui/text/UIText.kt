package com.instantsystem.newsly.app.common.ui.text

import androidx.annotation.StringRes

sealed class UIText {
    data class Text(val value: String) : UIText()
    data class Resource(@StringRes val resId: Int, val args: List<Any> = emptyList()) : UIText()

    companion object {
        fun text(value: String) = Text(value)
        fun resource(@StringRes resId: Int, vararg args: Any) = Resource(resId, args.toList())
    }
}


