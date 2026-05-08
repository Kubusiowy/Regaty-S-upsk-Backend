package com.example.core.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.ApplicationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import javax.sql.DataSource

class DatabaseFactory(
    private val config: ApplicationConfig
){

   private lateinit var dataSource: DataSource


    fun init (){

        val host = config.property("mysql.host").getString()
        val port = config.property("mysql.port").getString()
        val database = config.property("mysql.database").getString()
        val username = config.property("mysql.user").getString()
        val password = config.property("mysql.pass").getString()

        val hikariConfig = HikariConfig().apply {
            driverClassName = "com.mysql.cj.jdbc.Driver"
            jdbcUrl = "jdbc:mysql://${host}:${port}/${database}"
            this.username = username
            this.password = password

            maximumPoolSize = 10
            minimumIdle = 2
            isAutoCommit = false
            poolName = "regaty-pool"
        }

        dataSource = HikariDataSource(hikariConfig)

        Flyway.configure()
            .dataSource(dataSource)
            .locations("classpath:db/migration")
            .load()
            .migrate()

        Database.connect(dataSource)

    }

}

suspend fun <T> dbQuery(block: () -> T): T = withContext(Dispatchers.IO) {
    transaction {
        block()
    }
}