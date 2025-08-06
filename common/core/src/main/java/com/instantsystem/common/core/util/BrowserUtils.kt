package com.instantsystem.common.core.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.instantsystem.newsly.common.core.R


interface IBrowserNavigator {
    fun openUrl(url: String): Result<Unit>
}

class BrowserNavigator(
    private val context: Context,
) : IBrowserNavigator {
    override fun openUrl(url: String): Result<Unit> {
        return try {
            if (!isValidUrl(url)) {
                return Result.failure(
                    IllegalArgumentException(
                        context.getString(R.string.common_core_error_invalid_url),
                    )
                )
            }

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            if (!canHandleIntent(intent)) {
                return Result.failure(Exception(context.getString(R.string.common_core_error_no_app_to_open_link)))
            }

            context.startActivity(intent)
            Result.success(Unit)

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(
                Exception(
                    context.getString(R.string.common_core_error_open_link),
                )
            )
        }
    }

    private fun canHandleIntent(intent: Intent): Boolean {
        return try {
            val packageManager = context.packageManager
            val activities = packageManager.queryIntentActivities(intent, 0)
            activities.isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }

    private fun isValidUrl(url: String): Boolean {
        if (url.isBlank()) return false
        return try {
            val uri = Uri.parse(url)
            uri.scheme != null && uri.host != null
        } catch (e: Exception) {
            false
        }
    }
}

