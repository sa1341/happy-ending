package com.kakaopaysec.happyending.mysql.configuration

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.support.TransactionSynchronizationManager
import javax.sql.DataSource

@Configuration
@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration::class])
@EnableTransactionManagement
@EnableJpaAuditing
@EnableConfigurationProperties
@EnableJpaRepositories(basePackages = ["com.kakaopaysec.happyending.mysql"])
@EntityScan(basePackages = ["com.kakaopaysec.happyending.mysql"])
class DatabaseConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "datasource.hikari.write")
    fun writeDataSource(): DataSource {
        return DataSourceBuilder.create().type(HikariDataSource::class.java).build()
    }

    @Bean
    @ConfigurationProperties(prefix = "datasource.hikari.read")
    fun readDataSource(): DataSource {
        return DataSourceBuilder.create().type(HikariDataSource::class.java).build()
    }

    @Autowired
    @Bean
    fun routingDataSource(
        @Qualifier("writeDataSource") writeDataSource: DataSource,
        @Qualifier("readDataSource") readDataSource: DataSource,
    ): ReplicationRoutingDataSource {
        val routingDataSource = ReplicationRoutingDataSource()

        routingDataSource.setTargetDataSources(
            mapOf(
                "write" to writeDataSource,
                "read" to readDataSource
            )
        )

        routingDataSource.setDefaultTargetDataSource(readDataSource)

        return routingDataSource
    }

    @Autowired
    @Primary
    @Bean
    fun happyDataSource(@Qualifier("routingDataSource") routingDataSource: DataSource): DataSource {
        return LazyConnectionDataSourceProxy(routingDataSource)
    }
}

class ReplicationRoutingDataSource : AbstractRoutingDataSource() {
    override fun determineCurrentLookupKey(): String {
        return when {
            TransactionSynchronizationManager.isCurrentTransactionReadOnly() -> "read"
            else -> "write"
        }
    }
}
