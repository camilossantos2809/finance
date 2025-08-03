package io.finance.data.repository.impl

import io.finance.data.repository.WalletRepository
import io.finance.data.database.Company
import io.finance.data.database.Wallet
import io.finance.data.model.CompanyListItem
import io.finance.data.model.WalletListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class WalletRepositoryImpl : WalletRepository {
    override suspend fun getCompaniesByWallet(walletId: Int) = withContext(Dispatchers.IO) {
        transaction {
            Company.selectAll().where {
                Company.walletId eq walletId
            }.map { CompanyListItem(it[Company.id].value, it[Company.description]) }
        }
    }


    override suspend fun getAll() = withContext(Dispatchers.IO) {
        transaction {
            Wallet.selectAll().map { WalletListItem(id = it[Wallet.id].value, description = it[Wallet.description]) }
        }
    }

}