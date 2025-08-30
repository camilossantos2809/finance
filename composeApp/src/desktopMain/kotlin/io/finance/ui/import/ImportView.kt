package io.finance.ui.import

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.finance.ui.navigation.LocalNavController
import org.koin.compose.koinInject

@Composable
fun ImportView() {
    val navController = LocalNavController.current
    val viewModel: ImportViewModel = koinInject()
    val statusStock = viewModel.statusStock.collectAsState()
    val statusOperations = viewModel.statusOperations.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Text("Import", style = MaterialTheme.typography.headlineMedium)
        ImportButton(text = "Import stock codes", status = statusStock.value, onClick = { viewModel.importStocks() })
        ImportButton(
            text = "Import operations",
            status = statusOperations.value,
            onClick = { viewModel.importOperations() })
    }
}

@Composable
fun ImportButton(text: String, status: String?, onClick: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { onClick() }) {
            Text(text)
        }
        status?.let {
            Text(it, style = MaterialTheme.typography.bodyMedium)
        }
    }
}