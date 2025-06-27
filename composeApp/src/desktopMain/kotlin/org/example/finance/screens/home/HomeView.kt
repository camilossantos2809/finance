package org.example.finance.screens.home


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.finance.LocalNavController
import org.example.finance.Screen
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun HomeView() {
    val navController = LocalNavController.current

    Column(
        modifier = Modifier.safeContentPadding().fillMaxSize().background(color = Color(122, 122, 122)).padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(onClick = { navController.navigate(Screen.CompanyList.route) }) {
            Text("Companies", style = MaterialTheme.typography.titleLarge)
        }
        Card(onClick = { navController.navigate(Screen.WalletList.route) }) {
            Text("Wallets", style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Composable
fun Card(onClick: () -> Unit, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier.clickable(onClick = onClick)
            .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(8.dp))
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
    ) {
        content()
    }
}