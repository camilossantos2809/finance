package org.example.finance

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.finance.services.database.connectDatabase

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