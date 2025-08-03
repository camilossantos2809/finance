package io.finance.ui.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.finance.data.model.CompanyListItem
import io.finance.data.repository.WalletRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class WalletViewModel(
    private val repository: WalletRepository, val walletId: Int
) : ViewModel() {
    private val _companies = MutableStateFlow<List<CompanyListItem>>(emptyList())
    val companies: StateFlow<List<CompanyListItem>> = _companies

    val errorMessage = MutableStateFlow<String?>("")

    init {
        loadCompanies()
    }

    private fun loadCompanies() {
        viewModelScope.launch {
            try {
                _companies.value = repository.getCompaniesByWallet(walletId)
            } catch (e: Exception) {
                errorMessage.value = "${e.message}"
            }
        }
    }
}