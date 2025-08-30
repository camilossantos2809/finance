package io.finance.data.model

import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun OperationCSV.toInsertOperationData(stockId: Int, typeId: Int) = InsertOperationData(
    stockId = stockId,
    typeId = typeId,
    createdAt = Clock.System.now(),
    date = OperationCSV.parseFormattedDateToLocalDate(this.date),
    amountQuotes = this.amount.toBigDecimal(),
    priceUnit = OperationCSV.parseFormattedPriceToBigDecimal(this.price),
    tax = OperationCSV.parseFormattedPriceToBigDecimal(this.tax),
    amountQuotesTotal = OperationCSV.parseFormattedPriceToBigDecimal(this.finalHoldings),
)