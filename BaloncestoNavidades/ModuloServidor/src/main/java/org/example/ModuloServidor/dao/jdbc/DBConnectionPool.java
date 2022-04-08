package org.example.ModuloServidor.dao.jdbc;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.example.ModuloServidor.config.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class DBConnectionPool {

    private DataSource hirakiDatasource = null;
    private final Configuration config;

    @Inject
    public DBConnectionPool(Configuration config) {

        this.config = config;
        hirakiDatasource = getDataSourceHikari();
    }

    private DataSource getDataSourceHikari() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(this.config.getRuta());
        config.setUsername(this.config.getUser());
        config.setPassword(this.config.getPassword());
        config.setDriverClassName(this.config.getDriver());
        config.setMaximumPoolSize(1);

        config.addDataSourceProperty(JDBConstantes.CACHE_PREP_STMTS, JDBConstantes.TRUE);
        config.addDataSourceProperty(JDBConstantes.PREP_STMT_CACHE_SIZE, JDBConstantes.VALUE);
        config.addDataSourceProperty(JDBConstantes.PREP_STMT_CACHE_SQL_LIMIT, JDBConstantes.VALUE_SQL_LIMIT);


        HikariDataSource datasource = new HikariDataSource(config);

        return datasource;
    }

    public DataSource getDataSource() {
        return hirakiDatasource;
    }

    @PreDestroy
    public void closePool() {
        ((HikariDataSource) hirakiDatasource).close();
    }

}

