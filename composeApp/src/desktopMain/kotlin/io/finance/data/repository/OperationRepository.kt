package io.finance.data.repository

import io.finance.data.model.InsertOperationData
import org.jetbrains.exposed.v1.core.ResultRow

interface OperationRepository {
    suspend fun insertOperations(operations: List<InsertOperationData>): List<ResultRow>
    suspend fun getTypeIdMapByCode(): Map<String, Int>
}