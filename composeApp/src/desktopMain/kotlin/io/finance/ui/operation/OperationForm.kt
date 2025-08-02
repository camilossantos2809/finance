package io.finance.ui.operation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import io.finance.ui.navigation.LocalNavController
import io.finance.ui.RadioButtonItem
import io.finance.ui.SharedState

@Composable
fun OperationFormView() {
    val navController = LocalNavController.current
    val viewModel = remember { OperationViewModel() }
    val formData by viewModel.formData.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Text(
                "${SharedState.selectedStock?.code} - ${SharedState.selectedStock?.companyName}",
                style = MaterialTheme.typography.titleLarge
            )
        }
        TextField(
            value = formData.date,
            onValueChange = { viewModel.onChangeDate(it) },
            label = { Text("Date") })
        RadioButtonSingleSelection(radioOptions = SharedState.operationTypes.value)
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
        Button(onClick = { viewModel.onPressSaveButton(navController) }) {
            Text("Save")
        }
        Text(errorMessage ?: "", style = TextStyle(color = Color.Red))
    }
}

@Composable
fun RadioButtonSingleSelection(modifier: Modifier = Modifier, radioOptions: List<RadioButtonItem>) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions.firstOrNull()) }
    Row(modifier.selectableGroup()) {
        radioOptions.forEach { item ->
            Row(
                Modifier
                    .height(56.dp)
                    .selectable(
                        selected = (item == selectedOption),
                        onClick = { onOptionSelected(item) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (item == selectedOption),
                    onClick = null
                )
                Text(
                    text = item.text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

