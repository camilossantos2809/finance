package org.example.finance

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

data class Input(val description: String="")

class AppViewModel : ViewModel() {
    private val _formData = MutableStateFlow(Input())
    val formData get() = _formData.asStateFlow()
    val version = MutableStateFlow("")

    fun updateDescription(newValue: String) {
        _formData.update { it.copy(description = newValue) }
    }

    fun onPressSave(){
        transaction {
            val versionDB = exec("select version()"){ result ->
                result.next()
                result.getString(1)
            }
            version.value = versionDB?:""
        }
    }
}