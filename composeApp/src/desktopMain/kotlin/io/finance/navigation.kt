package io.finance

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import io.finance.screens.company.CompanyListView
import io.finance.screens.home.HomeView
import io.finance.screens.operation.OperationFormView
import io.finance.screens.operation.OperationListView
import io.finance.screens.wallet.WalletListView

@Serializable
object Home

@Serializable
object WalletList

@Serializable
object CompanyList

@Serializable
object OperationsList

@Serializable
object OperationForm

val LocalNavController = compositionLocalOf<NavController> {
    error("No NavController provided")
}

@Composable
fun NavigationRoutes() {
    val navController = rememberNavController()
    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(navController = navController, startDestination = Home) {
            composable<Home> { HomeView() }
            composable<WalletList> { WalletListView() }
            composable<CompanyList> { CompanyListView() }
            composable<OperationsList> { OperationListView() }
            composable<OperationForm> { OperationFormView() }
        }
    }
}
