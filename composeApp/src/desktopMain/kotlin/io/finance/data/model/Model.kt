package io.finance.data.model

import kotlinx.datetime.LocalDate
import java.math.BigDecimal
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class CompanyListItem(val id: Int, val name: String)

data class WalletListItem(val id: Int, val description: String)

data class InsertStocksData(val companyId: Int, val code: String)

data class OperationCSV(
    val date: String,
    val stockCode: String,
    val type: String,
    val amount: String,
    val price: String,
    val tax: String,
    val total: String,
    val costByStock: String,
    val netTotal: String,
    val finalHoldings: String,
    val averagePrice: String,
    val profitLoss: String,
    val finalBalance: String,


    ) {
    companion object {
        fun parseFormattedDateToLocalDate(dateString: String): LocalDate {
            val parts = dateString.split("/")
            return LocalDate(parts[2].toInt(), parts[1].toInt(), parts[0].toInt())
        }

        fun parseFormattedPriceToBigDecimal(priceString: String): BigDecimal {
            if (priceString.isEmpty()) return BigDecimal.ZERO
            return priceString
                .replace("R$ ", "")
                .replace(".", "")
                .replace(",", ".")
                .toBigDecimal()
        }
    }

}

data class InsertOperationData @OptIn(ExperimentalTime::class) constructor(
    val stockId: Int,
    val typeId: Int,
    val date: LocalDate,
    val amountQuotes: BigDecimal,
    val priceUnit: BigDecimal,
    val tax: BigDecimal,
    val amountQuotesTotal: BigDecimal,
    val createdAt: Instant,
)



