package io.finance.screens.home

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import io.finance.OperationsList
import io.finance.WalletList
import io.finance.screens.SharedState
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import io.finance.services.database.Wallet
import org.jetbrains.exposed.v1.core.ResultRow

data class WalletListItem(val id: Int, val description: String)

fun ResultRow.toWalletListItem() = WalletListItem(
    id = this[Wallet.id].value, description = this[Wallet.description]
)

class HomeViewModel : ViewModel() {
    val wallets = MutableStateFlow(emptyList<WalletListItem>().toMutableList())
    val errorMessage = MutableStateFlow<String?>("")

    init {
        transaction {
            Wallet.selectAll().forEach { wallets.value.add(it.toWalletListItem()) }
        }
    }

    fun onPressSearch(navController: NavController) {
        try {
            SharedState.selectStock()
            navController.navigate(OperationsList)
        } catch (e: Exception) {
            errorMessage.value = "${e.message}"
            e.printStackTrace()
        }
    }

    fun onPressWallet(walletId: Int, navController: NavController) {
        navController.navigate(WalletList(walletId))
    }

}