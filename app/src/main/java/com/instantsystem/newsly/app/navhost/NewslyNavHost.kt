package com.instantsystem.newsly.app.navhost

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.instantsystem.app.feature.home.ui.HomeScreen
import com.instantsystem.common.core.util.IBrowserNavigator
import com.instantsystem.newsly.app.feature.detail.ui.DetailScreen
import com.instantsystem.newsly.domain.model.Article
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun NewslyNavHost(
    modifier: Modifier = Modifier,
) {
    val backStack = rememberNavBackStack<NavKey>(Home)
    val browserNavigator: IBrowserNavigator = koinInject()

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
            rememberSceneSetupNavEntryDecorator()
        ),
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is Home -> {
                    NavEntry(key = key) {
                        HomeScreen(
                            viewModel = koinViewModel(),
                            toDetail = { article ->
                                backStack.add(Detail(article))
                            }
                        )
                    }
                }

               is Detail -> {
                   NavEntry(key = key) {
                        DetailScreen(
                            viewModel =  koinViewModel { parametersOf(key.article) },
                            onNavigateBack = { backStack.removeLastOrNull() },
                            onOpenInBrowser = { url ->
                                browserNavigator.openUrl(url = url)
                            }
                        )
                    }
                }

                else -> throw RuntimeException("Invalid NavKey.")
            }
        },
    )
}

@Serializable
data object Home : NavKey

@Serializable
data class Detail(val article: Article) : NavKey
