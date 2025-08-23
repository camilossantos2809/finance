package io.finance.data.repository

interface CompanyRepository {
    /**
     * Usado para obter o id da empresa, de acordo o stock, para uso na importação do .csv
     */
    suspend fun getStockIdMapByCodes(stocks: List<String>): Map<String, Int>
}