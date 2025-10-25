package io.finance.data.database

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.v1.jdbc.Database
import kotlin.io.path.Path
import kotlin.io.path.readText

@Serializable
data class DatabaseConfig(
    val name: String,
    val user: String,
    val password: String,
)

@Serializable
data class Config(
    val database: DatabaseConfig,
)

fun getConfig(): Config {
    val configPath = System.getProperty("user.home") + "/money-config.json"
    return Json.decodeFromString<Config>(Path(configPath).readText())
}

fun connectDatabase(): Database {
    val config = getConfig()

    return Database.connect(
        "jdbc:postgresql://localhost:5432/${config.database.name}",
        driver = "org.postgresql.Driver",
        user = config.database.user,
        password = config.database.password,
    )
}
