package io.finance.ui.operation

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import io.finance.data.database.Operation
import io.finance.data.database.OperationType
import io.finance.ui.RadioButtonItem
import io.finance.ui.SharedState
import io.finance.ui.navigation.OperationForm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.toKotlinLocalDate
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.StdOutSqlLogger
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import kotlinx.datetime.LocalDate as KxLocalDate

fun parseDateInput(input: String): KxLocalDate? =
    try {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val parsed = LocalDate.parse(input, formatter)
        parsed.toKotlinLocalDate() // Converts java.time.LocalDate → kotlinx.datetime.LocalDate
    } catch (e: DateTimeParseException) {
        e.printStackTrace()
        null
    }

fun getFormattedCurrentDate(): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val currentDate = LocalDate.now()
    return currentDate.format(formatter)
}

data class FormData(
    val date: TextFieldValue,
    val type: String,
    val amount: String,
    val price: String,
)

data class OperationListItem(
    val id: Int,
    val date: KxLocalDate,
    val type: String,
    val amount: BigDecimal,
    val price: BigDecimal,
)

class OperationViewModel : ViewModel() {
    private val _formData =
        MutableStateFlow(
            FormData(
                type = "C",
                amount = "0,0",
                price = "0,0",
                date = TextFieldValue(getFormattedCurrentDate()),
            ),
        )
    val formData get() = _formData.asStateFlow()
    val errorMessage = MutableStateFlow<String?>("")
    val operations = MutableStateFlow<List<OperationListItem>>(emptyList())

    fun fetchOperations() {
        try {
            transaction {
                val stockId = SharedState.selectedStock?.id
                operations.value =
                    Operation
                        .selectAll()
                        .where { Operation.stockId eq stockId }
                        .orderBy(Operation.date to SortOrder.DESC)
                        .map {
                            OperationListItem(
                                id = it[Operation.id].value,
                                date = it[Operation.date],
                                type = it[Operation.typeId].value.toString(),
                                amount = it[Operation.amount],
                                price = it[Operation.priceUnit],
                            )
                        }
            }
        } catch (e: Exception) {
            errorMessage.value = "${e.message}"
            e.printStackTrace()
        }
    }

    fun onPressSaveButton(navController: NavController) {
        try {
            if (_formData.value.date.text
                    .isEmpty()
            ) {
                errorMessage.value = "Date cannot be empty."
                throw IllegalStateException(errorMessage.value)
            }
            val amountQuotes =
                _formData.value.amount
                    .replace(",", ".")
                    .toBigDecimal()

            require(amountQuotes <= BigDecimal.ZERO) { "Amount must be greater than zero." }

            val priceUnit =
                _formData.value.price
                    .replace(",", ".")
                    .toBigDecimal()
            require(priceUnit <= BigDecimal.ZERO) {
                "Price must be greater than zero."
            }
            val parsedDate = checkNotNull(parseDateInput(_formData.value.date.text)) { "Invalid date" }
            val stock = checkNotNull(SharedState.selectedStock?.id) { "Stock not selected" }

            transaction {
                Operation.insert {
                    it[Operation.date] = parsedDate
                    it[Operation.typeId] = 1
                    it[Operation.amount] = amountQuotes
                    it[Operation.priceUnit] = priceUnit
                    it[Operation.stockId] = stock
                }
            }
            navController.popBackStack()
            errorMessage.value = null
        } catch (e: Exception) {
            errorMessage.value = "${e.message}"
            e.printStackTrace()
        }
    }

    fun onPressAddOperation(navController: NavController) {
        try {
            transaction {
                addLogger(StdOutSqlLogger)
                SharedState.operationTypes.value =
                    OperationType.selectAll().map {
                        RadioButtonItem(
                            id = it[OperationType.id].value,
                            text = it[OperationType.description].replaceFirstChar { c -> c.uppercase() },
                        )
                    }
            }
            navController.navigate(OperationForm)
        } catch (e: Exception) {
            errorMessage.value = "${e.message}"
            e.printStackTrace()
        }
    }

    fun onChangeDate(inputValue: TextFieldValue) {
        _formData.value = _formData.value.copy(date = inputValue)
    }

    fun onChangeType(inputValue: String) {
        _formData.update {
            it.copy(type = inputValue)
        }
    }

    fun onChangeAmount(inputValue: String) {
        _formData.update {
            it.copy(amount = inputValue)
        }
    }

    fun onChangePrice(inputValue: String) {
        _formData.update {
            it.copy(price = inputValue)
        }
    }
}
