package io.finance.data.repository

import io.finance.data.model.CompanyListItem
import io.finance.data.model.WalletListItem

interface WalletRepository {
    suspend fun getCompaniesByWallet(walletId: Int): List<CompanyListItem>
    suspend fun getAll(): List<WalletListItem>
}