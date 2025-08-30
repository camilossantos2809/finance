package io.finance.data.repository.impl

import io.finance.data.database.Operation
import io.finance.data.database.OperationType
import io.finance.data.model.InsertOperationData
import io.finance.data.repository.OperationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import kotlin.time.ExperimentalTime

class OperationRepositoryImpl : OperationRepository {
    @OptIn(ExperimentalTime::class)
    override suspend fun insertOperations(operations: List<InsertOperationData>)=withContext(Dispatchers.IO) {
        transaction {
            Operation.batchInsert(operations){
                this[Operation.stockId] = it.stockId
                this[Operation.typeId] = it.typeId
                this[Operation.createdAt] = it.createdAt
                this[Operation.date] = it.date
                this[Operation.amount] = it.amountQuotes
                this[Operation.priceUnit] = it.priceUnit
                this[Operation.tax] = it.tax
                this[Operation.amountQuotesTotal] = it.amountQuotesTotal
            }
        }
    }

    override suspend fun getTypeIdMapByCode() = withContext(Dispatchers.IO) {
        transaction {
            OperationType.select(OperationType.id, OperationType.code).associate {
                it[OperationType.code] to it[OperationType.id].value
            }
        }
    }

}