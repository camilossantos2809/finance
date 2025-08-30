package io.finance.data.repository

import io.finance.data.model.InsertStocksData
import org.jetbrains.exposed.v1.core.ResultRow

interface StockRepository {

    suspend fun insertStocks(stocks: List<InsertStocksData>): List<ResultRow>
    suspend fun getStockIdMapByCodes(): Map<String, Int>
}