package org.example.finance.screens.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.example.finance.services.database.Wallet
import org.jetbrains.exposed.v1.core.ResultRow

data class WalletListItem(val id: Int, val description: String)

fun ResultRow.toWalletListItem() = WalletListItem(
    id = this[Wallet.id].value,
    description = this[Wallet.description]
)

class HomeViewModel : ViewModel() {

    val wallets = MutableStateFlow(emptyList<WalletListItem>().toMutableList())

    init {
        transaction {
            Wallet.selectAll().forEach { wallets.value.add(it.toWalletListItem()) }
        }
    }


}