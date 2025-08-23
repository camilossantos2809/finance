package io.finance.data.filesystem

import okio.FileSystem
import okio.Path.Companion.toPath

object FileHelper {
    fun readCSVLines(path: String): List<List<String>> {
        val filePath = path.toPath()
        return FileSystem.SYSTEM.read(filePath) {
            generateSequence { readUtf8Line()?.split(",") }.toList()
        }
    }
}