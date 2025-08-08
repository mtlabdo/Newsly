package com.instantsystem.newsly.app.common.ui.text

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun UIText.asString(): String {
    return when (this) {
        is UIText.Text -> value
        is UIText.Resource -> {
            if (args.isEmpty()) {
                stringResource(resId)
            } else {
                stringResource(resId, *args.toTypedArray())
            }
        }
    }
}

fun UIText.asString(context: Context): String {
    return when (this) {
        is UIText.Text -> value
        is UIText.Resource -> {
            if (args.isEmpty()) {
                context.getString(resId)
            } else {
                context.getString(resId, *args.toTypedArray())
            }
        }
    }
}