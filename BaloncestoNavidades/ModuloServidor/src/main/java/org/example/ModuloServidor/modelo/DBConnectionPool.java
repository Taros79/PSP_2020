package org.example.ModuloServidor.modelo;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.ModuloServidor.config.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnectionPool {

    private static DBConnectionPool dbconection = null;

    private HikariDataSource hikariDataSource = null;

    private DBConnectionPool() {
        hikariDataSource = getDataSourceHikari();
    }

    public static DBConnectionPool getInstance() {
        if (dbconection == null) {
            dbconection = new DBConnectionPool();
        }

        return dbconection;
    }

    public Connection getConnection() throws Exception {
        Class.forName(Configuration.getInstance().getDriver());
        Connection connection;

        connection = hikariDataSource.getConnection();

        return connection;
    }

    private HikariDataSource getDataSourceHikari() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(Configuration.getInstance().getRuta());
        config.setUsername(Configuration.getInstance().getUser());
        config.setPassword(Configuration.getInstance().getPassword());
        config.setMaximumPoolSize(Configuration.getInstance().getMaxPoolCon());

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        HikariDataSource datasource = new HikariDataSource(config);

        return datasource;
    }

    public DataSource getDataSource() {
        return hikariDataSource;
    }

    public void cerrarConexion(Connection connection) {
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cerrarPool() {
       hikariDataSource.close();
    }
}
