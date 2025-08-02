package org.example.finance.screens.wallet

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.example.finance.screens.SharedState
import org.example.finance.services.database.Company
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import kotlin.collections.emptyList

data class CompanyListItem(val id: Int, val name: String)

class WalletViewModel : ViewModel() {
    val companies = MutableStateFlow(emptyList<CompanyListItem>().toMutableList())
    val errorMessage = MutableStateFlow<String?>("")

    init {
        transaction {
            Company.selectAll().where {
                Company.walletId eq SharedState.selectedWallet
            }.forEach { companies.value.add(CompanyListItem(it[Company.id].value, it[Company.description])) }
        }
    }
}