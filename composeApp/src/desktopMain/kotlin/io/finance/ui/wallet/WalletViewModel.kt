package io.finance.ui.wallet

import androidx.lifecycle.ViewModel
import io.finance.data.repository.WalletRepository
import kotlinx.coroutines.flow.MutableStateFlow

data class CompanyListItem(val id: Int, val name: String)

class WalletViewModel(
    private val repository: WalletRepository, val walletId: Int
) : ViewModel() {
    val companies = repository.getCompaniesByWallet(walletId)
    val errorMessage = MutableStateFlow<String?>("")
}