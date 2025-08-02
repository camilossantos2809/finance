package io.finance

import io.finance.data.repository.WalletRepository
import io.finance.data.repository.impl.WalletRepositoryImpl
import io.finance.ui.wallet.WalletViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<WalletRepository> { WalletRepositoryImpl() }

    viewModel { (walletId: Int) ->
        WalletViewModel(
            repository = get(),
            walletId = walletId
        )
    }
}

val allModules = listOf(appModule )
