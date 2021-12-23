package org.example.ModuloServidor.modelo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j2;
import org.example.ModuloServidor.config.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


@Log4j2
public class DBConnection {

    private static DBConnection dbconection = null;

    private HikariDataSource hirakiDatasource = null;

    public DBConnection() {
        hirakiDatasource = getDataSourceHikari();
    }

    public static DBConnection getInstance() {
        if (dbconection == null) {
            dbconection = new DBConnection();
        }

        return dbconection;
    }

    public Connection getConnection() throws Exception {
        Class.forName(Configuration.getInstance().getDriver());
        Connection connection;
        connection = hirakiDatasource.getConnection();

        return connection;
    }

    private HikariDataSource getDataSourceHikari() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(Configuration.getInstance().getRuta());
        config.setUsername(Configuration.getInstance().getUser());
        config.setPassword(Configuration.getInstance().getPassword());
        config.setDriverClassName(Configuration.getInstance().getDriver());
        config.setMaximumPoolSize(2);

        HikariDataSource datasource = new HikariDataSource(config);

        return datasource;
    }

    public DataSource getDataSource() {
        return hirakiDatasource;
    }

    public void cerrarConexion(Connection connection) {
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cerrarPool() {
        hirakiDatasource.close();
    }
}

