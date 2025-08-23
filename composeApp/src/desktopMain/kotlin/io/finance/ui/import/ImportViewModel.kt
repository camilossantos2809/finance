package io.finance.ui.import

import androidx.lifecycle.ViewModel
import io.finance.data.repository.ImportRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.awt.FileDialog
import java.awt.Frame
import java.io.File

class ImportViewModel(val repository: ImportRepository) : ViewModel() {
    private val _selectedFilePath = MutableStateFlow<String?>(null)
    val selectedFilePath: StateFlow<String?> = _selectedFilePath.asStateFlow()

    fun openFileDialog() {
        val fileDialog = FileDialog(null as Frame?, "Select CSV File", FileDialog.LOAD)
        fileDialog.setFile("*.csv")
        fileDialog.isVisible = true

        val selectedFile = fileDialog.file
        val selectedDir = fileDialog.directory

        if (selectedFile != null && selectedDir != null) {
            _selectedFilePath.value = File(selectedDir, selectedFile).absolutePath
            repository.readCSVCalculo(_selectedFilePath.value!!)
        }
    }


    fun clearSelectedFile() {
        _selectedFilePath.value = null
    }
}