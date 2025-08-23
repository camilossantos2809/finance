package io.finance

import io.finance.data.repository.ImportRepository
import io.finance.data.repository.WalletRepository
import io.finance.data.repository.impl.ImportRepositoryImpl
import io.finance.data.repository.impl.WalletRepositoryImpl
import io.finance.ui.home.HomeViewModel
import io.finance.ui.import.ImportViewModel
import io.finance.ui.wallet.WalletViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single<WalletRepository> { WalletRepositoryImpl() }
    single<ImportRepository> { ImportRepositoryImpl() }
}

val viewModelModule = module {
    viewModel { (walletId: Int) ->
        WalletViewModel(
            repository = get(), walletId = walletId
        )
    }
    viewModel { HomeViewModel(repository = get()) }
    viewModel { ImportViewModel(repository = get()) }
}

val allModules = listOf(repositoryModule, viewModelModule)
