package com.instantsystem.newsly.app.common.ui.text

import com.instantsystem.common.core.exception.NewsError
import com.instantsystem.newsly.common.ui.R

fun NewsError.toUIText(): UIText {
    return when (this) {
        NewsError.NETWORK -> UIText.resource(R.string.common_ui_error_network)
        NewsError.AUTH -> UIText.resource(R.string.common_ui_error_api_key)
        NewsError.LIMIT -> UIText.resource(R.string.common_ui_error_limit)
        NewsError.NO_DATA -> UIText.resource(R.string.common_ui_error_no_data)
        NewsError.SERVER -> UIText.resource(R.string.common_ui_error_server, 500)
        NewsError.UNKNOWN -> UIText.resource(R.string.common_ui_error_unknown)
    }
}
