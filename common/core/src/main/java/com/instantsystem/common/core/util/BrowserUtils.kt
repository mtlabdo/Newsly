package com.instantsystem.common.core.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.instantsystem.newsly.common.core.R

interface IBrowserNavigator {
    fun openUrl(url: String)
}

class BrowserNavigator(
    private val context: Context
) : IBrowserNavigator {
    override fun openUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                context,
                context.getString(R.string.common_core_error_open_link),
                Toast.LENGTH_SHORT
            ).show()
            e.printStackTrace()
        }
    }
}