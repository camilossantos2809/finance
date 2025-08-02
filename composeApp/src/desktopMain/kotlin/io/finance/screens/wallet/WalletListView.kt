package io.finance.screens.wallet

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.finance.LocalNavController
import io.finance.components.card.CardRow
import io.finance.screens.SharedState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun WalletListView() {
    val navController = LocalNavController.current
    val viewModel = remember { WalletViewModel() }
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
        Text(text = SharedState.selectedWallet.toString())
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(companies) { item ->
                CardRow(onClick = {}) {
                    Text(item.name, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}