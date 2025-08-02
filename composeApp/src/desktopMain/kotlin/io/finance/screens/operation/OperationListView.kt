package io.finance.screens.operation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.compose.currentBackStackEntryAsState
import io.finance.LocalNavController
import io.finance.WalletList
import io.finance.components.card.CardRow
import io.finance.screens.SharedState

@Composable
fun OperationListView() {
    val navController = LocalNavController.current
    val viewModel = remember { OperationViewModel() }
    val operations by viewModel.operations.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val lifecycle = navBackStackEntry?.lifecycle

    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    viewModel.fetchOperations()
                }

                else -> Unit
            }
        }
        lifecycle?.addObserver(observer)
        onDispose {
            lifecycle?.removeObserver(observer)
        }
    }


    Column(
        modifier = Modifier.safeContentPadding().fillMaxSize().padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Filled.Refresh, contentDescription = "Refresh")
            }
        }
        Text(
            "${SharedState.selectedStock?.code} - ${SharedState.selectedStock?.companyName}",
            style = MaterialTheme.typography.titleLarge
        )
        Button(onClick = { viewModel.onPressAddOperation(navController) }) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add operation")
            Text("Add Operation")
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(operations) { item ->
                CardRow(onClick = { navController.navigate(WalletList) }) {
                    Text(item.id.toString(), style = MaterialTheme.typography.bodyMedium)
                    Text(item.date.toString(), style = MaterialTheme.typography.bodyMedium)
                    Text(item.type, style = MaterialTheme.typography.bodyMedium)
                    Text(item.amount.toString(), style = MaterialTheme.typography.bodyMedium)
                    Text(item.price.toString(), style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

