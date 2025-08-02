package io.finance.screens.wallet

import androidx.lifecycle.ViewModel
import io.finance.repository.WalletRepository
import kotlinx.coroutines.flow.MutableStateFlow

data class CompanyListItem(val id: Int, val name: String)

class WalletViewModel(
    private val repository: WalletRepository, val walletId: Int
) : ViewModel() {
    val companies = repository.getCompaniesByWallet(walletId)
    val errorMessage = MutableStateFlow<String?>("")
}