package io.finance.ui.home


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import io.finance.ui.SharedState
import io.finance.ui.navigation.ImportView
import io.finance.ui.navigation.LocalNavController
import io.finance.ui.shared.components.card.CardRow
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject


@Composable
@Preview
fun HomeView() {
    val navController = LocalNavController.current
    val viewModel: HomeViewModel = koinInject()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val wallets by viewModel.wallets.collectAsState()

    Column(
        modifier = Modifier.safeContentPadding().fillMaxSize().padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = SharedState.companySearch,
                onValueChange = { SharedState.companySearch = it },
                label = { Text("Search company by stock") },
                isError = !(errorMessage?.isEmpty())!!,
                supportingText = { Text(errorMessage ?: "", style = TextStyle(color = Color.Red)) })
            Button(onClick = { viewModel.onPressSearch(navController) }) {
                Text("Search")
            }
            Button(onClick = { navController.navigate(ImportView) }) {
                Text("Importar")
            }
        }
        Text("Wallets", style = MaterialTheme.typography.titleLarge)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(wallets) { item ->
                CardRow(onClick = { viewModel.onPressWallet(item.id, navController) }) {
                    Text(item.description, style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}

