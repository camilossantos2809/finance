package io.finance.data.repository

import io.finance.data.model.OperationCSV

interface ImportRepository {
    fun readCSVCalculo(filePath: String): List<String>
    fun readCSVNotas(filePath: String): List<OperationCSV>
}