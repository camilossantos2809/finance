package org.example.finance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    val viewModel = remember { AppViewModel() }
    val formData by viewModel.formData.collectAsState()

    Column(
        modifier = Modifier.safeContentPadding().fillMaxSize().background(color = Color(40, 40, 40)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column {
            TextField(formData.description, { value -> viewModel.updateDescription(value) })
        }
        Button(onClick = { println("ok") }) {
            Text("Save")
        }
    }
}