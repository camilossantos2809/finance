package org.example.finance

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class Input(val description: String="")

class AppViewModel : ViewModel() {
    private val _formData = MutableStateFlow(Input())
    val formData get() = _formData.asStateFlow()

    fun updateDescription(newValue: String) {
        _formData.update { it.copy(description = newValue) }
    }
}