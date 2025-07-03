package org.example.finance.services.database

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.date
import org.jetbrains.exposed.v1.datetime.timestamp

const val MAX_VARCHAR_LENGTH = 100

object Wallet : IntIdTable() {
    val description = varchar("description", MAX_VARCHAR_LENGTH)
}

object Company: IntIdTable(){
    val cnpj = varchar("cnpj", MAX_VARCHAR_LENGTH)
    val razaoSocial = varchar("razao_social", MAX_VARCHAR_LENGTH)
    val description = varchar("description", MAX_VARCHAR_LENGTH)
    val walletId = reference("wallet_id", Wallet)
}

object Stock: IntIdTable(){
    val code = varchar("code", MAX_VARCHAR_LENGTH)
    val companyId = reference("company_id", Company)
}

object OperationType: IntIdTable("operation_type"){
    val description = varchar("description", MAX_VARCHAR_LENGTH)
}

object Operation: IntIdTable(){
    val stockId = reference("stock_id", Stock)
    val typeId = reference("type_id", OperationType)
    val date = date("date")
    val createdAt = timestamp("created_at")
    val amount = decimal("amount_quotes", 15, 5)
    val priceUnit = decimal("price_unit", 15, 5)
    val tax = decimal("tax", 15, 5)
    val amountQuotesTotal = decimal("amount_quotes_total", 15, 5)
    val priceTotal = decimal("price_total", 15, 5)
}