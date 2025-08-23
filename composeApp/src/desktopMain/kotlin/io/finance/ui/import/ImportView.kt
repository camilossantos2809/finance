package io.finance.ui.import

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.finance.ui.navigation.LocalNavController
import org.koin.compose.koinInject

@Composable
fun ImportView() {
    val navController = LocalNavController.current
    val viewModel: ImportViewModel = koinInject()
    val selectedFilePath by viewModel.selectedFilePath.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Text("Import", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.openFileDialog() }) {
            Text("Select CSV File")
        }
        Spacer(modifier = Modifier.height(16.dp))
        selectedFilePath?.let { path ->
            Text("Selected file: $path", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                // Here you can call your import function with the selected path
                // importRepository.readCSVCalculo(path)
            }) {
                Text("Import Operations")
            }
            Button(onClick = { viewModel.clearSelectedFile() }) {
                Text("Clear Selection")
            }
        }
    }
}