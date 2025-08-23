package io.finance.data.repository.impl

import io.finance.data.database.Company
import io.finance.data.repository.CompanyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class CompanyRepositoryImpl : CompanyRepository {
    override suspend fun getStockIdMapByCodes(stocks: List<String>): Map<String, Int> = withContext(Dispatchers.IO) {
        transaction {
            Company.selectAll().where {
                Company.razaoSocial inList stocks
            }.associate {
                it[Company.razaoSocial] to it[Company.id].value
            }

        }
    }

}