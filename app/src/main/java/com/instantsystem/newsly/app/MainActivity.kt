package com.instantsystem.newsly.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.instantsystem.newsly.app.common.ui.theme.NewslyAppTheme
import com.instantsystem.newsly.app.di.NewslyAppDI
import com.instantsystem.newsly.app.navhost.NewslyNavHost
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            NewslyAppDI(
                appDeclaration = {
                    modules()
                    androidContext(this@MainActivity)
                }
            ) {
                NewslyAppTheme(darkTheme = false) {
                    NewslyNavHost(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}