package com.java.akdev.passengerservice

import jakarta.transaction.Transactional
import liquibase.Contexts
import liquibase.LabelExpression
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.DirectoryResourceAccessor
import lombok.extern.slf4j.Slf4j
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.nio.file.Path
import java.sql.DriverManager


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
@Slf4j
@Testcontainers
open class IntegrationTestBase {

    companion object {
        @Container
        val POSTGRES: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:16")
            .withDatabaseName("scrapper_test")
            .withUsername("postgres")
            .withPassword("postgres")
            .also {
                it.start()
                runMigrations(it)
            }

        @DynamicPropertySource
        @JvmStatic
        fun postgresProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { POSTGRES.jdbcUrl }
            registry.add("spring.datasource.username") { POSTGRES.username }
            registry.add("spring.datasource.password") { POSTGRES.password }
        }

        private fun runMigrations(c: JdbcDatabaseContainer<*>) {
            val changelogPath = Path.of("./migrations/")

            try {
                DriverManager.getConnection(c.jdbcUrl, c.username, c.password).use { connection ->
                    val db = DatabaseFactory.getInstance()
                        .findCorrectDatabaseImplementation(JdbcConnection(connection))
                    val liquibase = Liquibase("master.xml", DirectoryResourceAccessor(changelogPath), db)
                    liquibase.update(Contexts(), LabelExpression())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
