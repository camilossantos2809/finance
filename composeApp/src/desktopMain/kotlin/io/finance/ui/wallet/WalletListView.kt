package io.finance.ui.wallet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.finance.ui.navigation.LocalNavController
import io.finance.ui.shared.components.card.CardRow
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
@Preview
fun WalletListView(
    walletId: Int,
) {
    val navController = LocalNavController.current
    val viewModel: WalletViewModel = koinInject { parametersOf(walletId) }
    val companies by viewModel.companies.collectAsState()

    Column(
        modifier = Modifier.safeContentPadding().fillMaxSize().padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }

        // Adicionar dados da wallet

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(companies) { item ->
                CardRow(onClick = {}) {
                    Text(item.name, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}