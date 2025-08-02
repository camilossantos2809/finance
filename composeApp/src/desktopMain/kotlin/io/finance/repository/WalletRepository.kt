package io.finance.repository

import io.finance.screens.wallet.CompanyListItem
import kotlinx.coroutines.flow.MutableStateFlow

interface WalletRepository {
    fun getCompaniesByWallet(walletId: Int): MutableStateFlow<List<CompanyListItem>>
}