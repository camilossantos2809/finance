package io.finance

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.finance.data.database.connectDatabase
import io.finance.ui.navigation.NavigationRoutes

fun main() {
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