package io.finance.ui.import

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.finance.data.model.InsertStocksData
import io.finance.data.model.toInsertOperationData
import io.finance.data.repository.CompanyRepository
import io.finance.data.repository.ImportRepository
import io.finance.data.repository.OperationRepository
import io.finance.data.repository.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import kotlin.time.ExperimentalTime

class ImportViewModel(
    val importRepository: ImportRepository,
    val stockRepository: StockRepository,
    val companyRepository: CompanyRepository,
    val operationRepository: OperationRepository
) : ViewModel() {
    private val _statusStock = MutableStateFlow<String?>(null)
    val statusStock: StateFlow<String?> = _statusStock.asStateFlow()
    private val _statusOperations = MutableStateFlow<String?>(null)
    val statusOperations: StateFlow<String?> = _statusOperations.asStateFlow()

    private fun openFileDialog(onFileOpen: (String) -> Unit) {
        val fileDialog = FileDialog(null as Frame?, "Select CSV File", FileDialog.LOAD)
        fileDialog.setFile("*.csv")
        fileDialog.isVisible = true

        val selectedFile = fileDialog.file
        val selectedDir = fileDialog.directory

        if (selectedFile != null && selectedDir != null) {
            val selectedFilePath = MutableStateFlow<String?>(null)
            selectedFilePath.value = File(selectedDir, selectedFile).absolutePath
            onFileOpen(selectedFilePath.value!!)
        }
    }

    fun importStocks() {
        openFileDialog {
            viewModelScope.launch {
                _statusStock.value = "Loading CSV file..."
                val textValues = importRepository.readCSVCalculo(it)
                val stockMap = companyRepository.getStockIdMapByCodes(textValues)
                _statusStock.value = "Inserting data..."
                stockRepository.insertStocks(textValues.map { textLine ->
                    val stockId = stockMap[textLine]
                    if (stockId == null) {
                        println(textLine)
                        return@launch
                    }
                    InsertStocksData(
                        code = textLine, companyId = stockId
                    )
                })
                _statusStock.value = "Success"
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    fun importOperations() {
        openFileDialog { filePath ->
            viewModelScope.launch {
                _statusOperations.value = "Loading CSV file..."
                val csvData = importRepository.readCSVNotas(filePath)
                val stockMap = stockRepository.getStockIdMapByCodes()
                val opTypeMap = operationRepository.getTypeIdMapByCode()
                _statusOperations.value = "Inserting data..."
                operationRepository.insertOperations(csvData.map { csvLine ->
                    val stockId = requireNotNull(stockMap[csvLine.stockCode]) { "stock can't be null" }
                    val typeId = requireNotNull(opTypeMap[csvLine.type]) { "type operation can't be null" }
                    csvLine.toInsertOperationData(stockId, typeId)
                })
                _statusOperations.value = "Success"
            }
        }
    }

}