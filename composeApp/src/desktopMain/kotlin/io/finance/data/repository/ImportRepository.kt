package io.finance.data.repository

interface ImportRepository {
    fun readCSVCalculo(filePath: String): List<String>
}