package io.finance.screens.wallet

import androidx.lifecycle.ViewModel
import io.finance.repository.WalletRepository
import kotlinx.coroutines.flow.MutableStateFlow
import io.finance.screens.SharedState

data class CompanyListItem(val id: Int, val name: String)

class WalletViewModel(private val repository: WalletRepository,
) : ViewModel() {
    val companies = repository.getCompaniesByWallet(SharedState.selectedWallet?:0)
    val errorMessage = MutableStateFlow<String?>("")
}