package org.example.finance.screens.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun HomeView() {
    val viewModel = remember { HomeViewModel() }
    val formData by viewModel.formData.collectAsState()

    Column(
        modifier = Modifier.safeContentPadding().fillMaxSize().background(color = Color(40, 40, 40)).padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column{
            Text("New Wallet", style = MaterialTheme.typography.titleLarge)
        }
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            TextField(
                formData.description,
                { value -> viewModel.updateDescription(value) },
                label = { Text("Description") })
            TextField(
                formData.description,
                { value -> viewModel.updateDescription(value) },
                label = { Text("Description 2") })
        }
        Text("Version: ${viewModel.version.collectAsState().value}", fontSize = 12.sp)
        Button(onClick = { viewModel.onPressSave() }) {
            Text("Save")
        }
    }
}