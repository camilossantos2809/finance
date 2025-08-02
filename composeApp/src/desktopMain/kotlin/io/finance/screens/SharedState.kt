package io.finance.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.flow.MutableStateFlow
import io.finance.services.database.Company
import io.finance.services.database.Stock
import org.jetbrains.exposed.v1.core.lowerCase
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

data class SelectedStock(val id: Int, val code: String, val companyName: String)

data class RadioButtonItem(val id: Int, val text: String)

object SharedState {
    var companySearch by mutableStateOf(TextFieldValue(""))
    var selectedStock by mutableStateOf<SelectedStock?>(null)
    val operationTypes = MutableStateFlow<List<RadioButtonItem>>(emptyList())
    var selectedWallet by mutableStateOf<Int?>(null)

    fun getStock(stockCode: String): SelectedStock? {
        return transaction {
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

    fun selectWallet(id: Int) {
        selectedWallet = id
    }
}