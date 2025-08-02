package io.finance.data.repository.impl

import io.finance.data.repository.WalletRepository
import io.finance.ui.wallet.CompanyListItem
import io.finance.data.database.Company
import kotlinx.coroutines.flow.MutableStateFlow
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class WalletRepositoryImpl : WalletRepository {
    override fun getCompaniesByWallet(walletId: Int): MutableStateFlow<List<CompanyListItem>> {
        val stateFlow = MutableStateFlow<List<CompanyListItem>>(emptyList())
        val companies = transaction {
            Company.selectAll().where {
                Company.walletId eq walletId
            }.map { CompanyListItem(it[Company.id].value, it[Company.description]) }
        }
        stateFlow.value = companies
        return stateFlow
    }
}