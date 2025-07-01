package org.example.finance.screens.operation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.finance.LocalNavController
import org.example.finance.OperationForm
import org.example.finance.screens.home.HomeViewModel

@Composable
fun OperationListView(stockId: String) {
    val navController = LocalNavController.current
    val viewModel = remember { OperationViewModel() }

    Column(
        modifier = Modifier.safeContentPadding().fillMaxSize().background(color = Color(122, 122, 122)).padding(12.dp)
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Text(stockId)
        IconButton(onClick = {navController.navigate(OperationForm(stockId))},){
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add operation")
        }
    }
}