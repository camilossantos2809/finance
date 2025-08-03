package io.finance.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import io.finance.data.model.WalletListItem
import io.finance.data.repository.WalletRepository
import io.finance.ui.SharedState
import io.finance.ui.navigation.OperationsList
import io.finance.ui.navigation.WalletList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class HomeViewModel(private val repository: WalletRepository) : ViewModel() {
    private val _wallets = MutableStateFlow<List<WalletListItem>>(emptyList())
    val wallets: StateFlow<List<WalletListItem>> = _wallets

    val errorMessage = MutableStateFlow<String?>("")

    init {
        loadWallets()
    }

    private fun loadWallets(){
        viewModelScope.launch {
            try {
                _wallets.value = repository.getAll()
            } catch (e: Exception) {
                errorMessage.value = "${e.message}"
            }
        }
    }

    fun onPressSearch(navController: NavController) {
        try {
            SharedState.selectStock()
            navController.navigate(OperationsList)
        } catch (e: Exception) {
            errorMessage.value = "${e.message}"
            e.printStackTrace()
        }
    }

    fun onPressWallet(walletId: Int, navController: NavController) {
        navController.navigate(WalletList(walletId))
    }

}