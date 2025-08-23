package io.finance.data.repository.impl

import io.finance.data.filesystem.FileHelper
import io.finance.data.repository.ImportRepository

class ImportRepositoryImpl : ImportRepository {
    //    Efetua leitura do arquivo calculo.csv para obter referência da wallet de cada stock
    override fun readCSVCalculo(filePath: String) = FileHelper.readLines(filePath)
}