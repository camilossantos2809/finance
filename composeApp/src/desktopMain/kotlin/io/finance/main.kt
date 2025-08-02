package io.finance

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.finance.data.database.connectDatabase
import io.finance.ui.navigation.NavigationRoutes
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(allModules)
    }
    connectDatabase()

    return application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "finance",
        ) {
            AppTheme {
                NavigationRoutes()
            }
        }
    }
}