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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.example.finance.CompanyList
import org.example.finance.LocalNavController
import org.example.finance.WalletList
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun HomeView() {
    val navController = LocalNavController.current
    val viewModel = remember { HomeViewModel() }
    var companySearch by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier.safeContentPadding().fillMaxSize().background(color = Color(122, 122, 122)).padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Wallets", style = MaterialTheme.typography.titleLarge)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(viewModel.wallets.value) { item ->
                Card(onClick = { navController.navigate(WalletList) }) {
                    Text(item.description, style = MaterialTheme.typography.titleMedium)
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            TextField(
                value = companySearch,
                onValueChange = { companySearch = it },
                label = { Text("Search company by stock") })
            Button(onClick = {}) {
                Text("Search")
            }
        }

        Card(onClick = { navController.navigate(CompanyList) }) {
            Text("Companies", style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Composable
fun Card(onClick: () -> Unit, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier.width(IntrinsicSize.Max)
            .clickable(onClick = onClick)
            .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(8.dp))
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
    ) {
        content()
    }
}