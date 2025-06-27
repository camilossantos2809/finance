package org.example.finance

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.finance.screens.company.CompanyListView
import org.example.finance.screens.home.HomeView

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object WalletList : Screen("walletList")
    object CompanyList : Screen("companyList")
}


val LocalNavController = compositionLocalOf<NavController> {
    error("No NavController provided")
}


@Composable
fun NavigationRoutes() {
    val navController = rememberNavController()
    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(navController = navController, startDestination = Screen.Home.route) {
            composable(Screen.Home.route) { HomeView() }
            composable(Screen.WalletList.route) { }
            composable(Screen.CompanyList.route) { CompanyListView() }
        }
    }
}
