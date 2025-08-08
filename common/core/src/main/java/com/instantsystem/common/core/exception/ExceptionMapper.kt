package com.instantsystem.common.core.exception

import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.toNewsException(): NewsException {
    return when (this) {
        is UnknownHostException, is SocketTimeoutException -> NewsException(NewsError.NETWORK)
        else -> NewsException(NewsError.UNKNOWN)
    }
}