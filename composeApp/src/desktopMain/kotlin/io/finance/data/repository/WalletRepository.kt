package io.finance.data.repository

import io.finance.ui.wallet.CompanyListItem
import kotlinx.coroutines.flow.MutableStateFlow

interface WalletRepository {
    fun getCompaniesByWallet(walletId: Int): MutableStateFlow<List<CompanyListItem>>
}