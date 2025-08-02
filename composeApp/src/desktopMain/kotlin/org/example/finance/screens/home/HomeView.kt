package org.example.finance.screens.home


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.example.finance.LocalNavController
import org.example.finance.screens.SharedState
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun HomeView() {
    val navController = LocalNavController.current
    val viewModel = remember { HomeViewModel() }
    val errorMessage by viewModel.errorMessage.collectAsState()

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
        }
        Text("Wallets", style = MaterialTheme.typography.titleLarge)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(viewModel.wallets.value) { item ->
                Card(onClick = { viewModel.onPressWallet(item.id, navController) }) {
                    Text(item.description, style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}

@Composable
fun Card(onClick: () -> Unit, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier.width(IntrinsicSize.Max).clickable(onClick = onClick)
            .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(8.dp))
            .background(color = Color.White, shape = RoundedCornerShape(8.dp)).padding(16.dp),
    ) {
        content()
    }
}