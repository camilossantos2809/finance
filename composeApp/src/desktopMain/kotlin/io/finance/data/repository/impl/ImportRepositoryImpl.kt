package io.finance.data.repository.impl

import io.finance.data.filesystem.FileHelper
import io.finance.data.model.OperationCSV
import io.finance.data.repository.ImportRepository

class ImportRepositoryImpl : ImportRepository {
    //    Efetua leitura do arquivo calculo.csv para obter referência da wallet de cada stock
    override fun readCSVCalculo(filePath: String) = FileHelper.readLines(filePath)

    override fun readCSVNotas(filePath: String) = FileHelper.readCSVLines(filePath).map {
        OperationCSV(
            date = it[0],
            stockCode = it[1],
            type = it[2],
            amount = it[3],
            price = it[4],
            tax = it[5],
            total = it[6],
            costByStock = it[7],
            netTotal = it[8].replace("#VALUE!", ""),
            finalHoldings = it[9].replace("#VALUE!", ""),
            averagePrice = it[10],
            profitLoss = it[11],
            finalBalance = it[12].replace("#VALUE!", "")
        )


    }
}