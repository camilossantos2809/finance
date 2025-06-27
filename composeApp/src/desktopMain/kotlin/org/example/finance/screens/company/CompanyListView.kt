package org.example.finance.screens.company

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.example.finance.LocalNavController

@Composable
fun CompanyListView() {
    val navController = LocalNavController.current

    Column(modifier = Modifier.fillMaxSize()) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")

        }
        Text("CompanyList")
    }
}