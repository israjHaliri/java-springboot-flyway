package com.digi.emas.b2c.common.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by israjHaliri
 */

@Configuration
@EnableJpaRepositories(
        basePackages = {"com.haliri.israj.javaspringbootflyway"},
        entityManagerFactoryRef = "entityManager",
        transactionManagerRef = "transactionManager"
)
public class DataSourceConfig {

    @Autowired
    private Environment env;

    @Primary
    @Bean
    public DataSource dataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        hikariDataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
        hikariDataSource.setUsername(env.getProperty("spring.datasource.username"));
        hikariDataSource.setPassword(env.getProperty("spring.datasource.password"));
        hikariDataSource.setAutoCommit(false);
        hikariDataSource.addDataSourceProperty("cachePrepStmts", "true");
        hikariDataSource.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariDataSource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariDataSource.setMaximumPoolSize(20);
        hikariDataSource.setMinimumIdle(20);
        hikariDataSource.setIdleTimeout(30000);
        hikariDataSource.setPoolName("grootPoolB2B");
        hikariDataSource.setMaxLifetime(2000000);
        hikariDataSource.setConnectionTimeout(30000);

        return hikariDataSource;
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[]{"com.haliri.israj.javaspringbootflyway"});
        em.setJpaProperties(additionalProperties());

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        em.setJpaVendorAdapter(vendorAdapter);

        return em;
    }

    @Primary
    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManager().getObject());

        return transactionManager;
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.setProperty("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
        properties.setProperty("hibernate.current_session_context_class", env.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));
        properties.setProperty("hibernate.format_sql", env.getProperty("spring.jpa.properties.hibernate.format_sql"));
        return properties;
    }
}
