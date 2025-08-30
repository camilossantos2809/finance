package io.finance.data.repository.impl

import io.finance.data.database.Stock
import io.finance.data.model.InsertStocksData
import io.finance.data.repository.StockRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class StockRepositoryImpl : StockRepository {

    override suspend fun insertStocks(stocks: List<InsertStocksData>) = withContext(Dispatchers.IO) {
        transaction {
            Stock.batchInsert(stocks) {
                this[Stock.companyId] = it.companyId
                this[Stock.code] = it.code
            }
        }
    }

    override suspend fun getStockIdMapByCodes() = withContext(Dispatchers.IO) {
        transaction {
            Stock.select(Stock.id, Stock.code).associate {
                it[Stock.code] to it[Stock.id].value
            }
        }
    }
}