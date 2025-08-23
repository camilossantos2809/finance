package io.finance.data.model

data class CompanyListItem(val id: Int, val name: String)

data class WalletListItem(val id: Int, val description: String)

data class InsertStocksData(val companyId:Int, val code:String)