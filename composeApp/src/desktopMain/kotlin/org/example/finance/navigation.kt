package org.example.finance

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import org.example.finance.screens.home.HomeView

@Serializable
object Home

@Serializable
object WalletList

@Serializable
object WalletForm

@Composable
fun NavigationRoutes() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Home) {
        composable<Home> { HomeView() }
    }
}
