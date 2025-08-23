package io.finance.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import io.finance.ui.company.CompanyListView
import io.finance.ui.home.HomeView
import io.finance.ui.operation.OperationFormView
import io.finance.ui.operation.OperationListView
import io.finance.ui.wallet.WalletListView
import io.finance.ui.import.ImportView

@Serializable
object Home

@Serializable
data class WalletList(val walletId: Int)

@Serializable
object CompanyList

@Serializable
object OperationsList

@Serializable
object OperationForm

@Serializable
object ImportView

val LocalNavController = compositionLocalOf<NavController> {
    error("No NavController provided")
}

@Composable
fun NavigationRoutes() {
    val navController = rememberNavController()
    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(navController = navController, startDestination = Home) {
            composable<Home> { HomeView() }
            composable<WalletList> { backStackEntry ->
                val walletList = backStackEntry.toRoute<WalletList>()
                WalletListView(walletId = walletList.walletId)
            }
            composable<CompanyList> { CompanyListView() }
            composable<OperationsList> { OperationListView() }
            composable<OperationForm> { OperationFormView() }
            composable<ImportView> { ImportView() }
        }
    }
}
