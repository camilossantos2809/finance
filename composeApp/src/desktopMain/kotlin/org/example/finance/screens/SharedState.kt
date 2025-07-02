package org.example.finance.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import org.example.finance.services.database.Company
import org.example.finance.services.database.Stock
import org.jetbrains.exposed.v1.core.StdOutSqlLogger
import org.jetbrains.exposed.v1.core.lowerCase
import org.jetbrains.exposed.v1.jdbc.addLogger
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

data class SelectedStock(val id: Int, val code: String, val companyName: String)

object SharedState {
    var companySearch by mutableStateOf(TextFieldValue(""))
    var selectedStock by mutableStateOf<SelectedStock?>(null)

    fun getStock(stockCode: String): SelectedStock? {
        return transaction {
            addLogger(StdOutSqlLogger)
            val stockQuery =
                (Stock innerJoin Company).selectAll().where { Stock.code.lowerCase() eq stockCode.lowercase() }
            if (stockQuery.empty()) {
                return@transaction null
            }
            return@transaction stockQuery.single().let {
                SelectedStock(
                    id = it[Stock.id].value,
                    code = it[Stock.code].uppercase(),
                    companyName = it[Company.description]
                )
            }
        }

    }

    fun selectStock(): SelectedStock? {
        selectedStock = getStock(companySearch.text)
        if (selectedStock == null) {
            throw NoSuchElementException("Stock ${companySearch.text.uppercase()} not found")
        }
        return selectedStock
    }
}