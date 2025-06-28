package org.example.finance.services.database

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

const val MAX_VARCHAR_LENGTH = 100

object Wallet : IntIdTable() {
    val description = varchar("description", MAX_VARCHAR_LENGTH)
}