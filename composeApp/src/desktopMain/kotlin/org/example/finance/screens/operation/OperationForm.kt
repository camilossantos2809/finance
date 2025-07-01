package org.example.finance.screens.operation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

@Composable
fun OperationFormView(stockId: String) {
    val navController = LocalNavController.current
    val viewModel = remember { OperationViewModel() }
    val formData by viewModel.formData.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        TextField(
            value = formData.date,
            onValueChange = { viewModel.onChangeDate(it) },
            label = { Text("Date") })
        TextField(
            value = viewModel.formData.value.type,
            onValueChange = viewModel::onChangeType,
            label = { Text("Type") }
        )
        TextField(
            value = viewModel.formData.value.amount,
            onValueChange = viewModel::onChangeAmount,
            label = { Text("Amount") }
        )
        TextField(
            value = viewModel.formData.value.price,
            onValueChange = viewModel::onChangePrice,
            label = { Text("Price") }
        )
        Button(onClick = { viewModel.onPressSaveButton(stockId) }) {
            Text("Save")
        }
        Text(errorMessage ?: "", style = TextStyle(color = Color.Red))
    }
}

