package io.finance.ui.import

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.finance.data.model.InsertStocksData
import io.finance.data.repository.CompanyRepository
import io.finance.data.repository.ImportRepository
import io.finance.data.repository.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.awt.FileDialog
import java.awt.Frame
import java.io.File

class ImportViewModel(
    val importRepository: ImportRepository,
    val stockRepository: StockRepository,
    val companyRepository: CompanyRepository
) : ViewModel() {
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
            viewModelScope.launch {
                val textValues = importRepository.readCSVCalculo(_selectedFilePath.value!!)
                val stockMap = companyRepository.getStockIdMapByCodes(textValues)
                stockRepository.insertStocks(textValues.map { textLine ->
                    val stockId = stockMap[textLine]
                    if (stockId == null) {
                        println(textLine)
                        return@launch
                    }
                    InsertStocksData(
                        code = textLine,
                        companyId = stockId
                    )
                })
                println("OK!")
            }

        }
    }


    fun clearSelectedFile() {
        _selectedFilePath.value = null
    }
}