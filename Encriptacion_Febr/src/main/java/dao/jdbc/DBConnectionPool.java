package dao.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import config.Configuration;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

@Singleton
public class DBConnectionPool {
    private final Configuration configYaml;
    private DataSource hikariDataSource = null;

    @Inject
    public DBConnectionPool(Configuration configYaml) {
        this.configYaml = configYaml;
        hikariDataSource = getDataSourceHikari();

    }

    private DataSource getDataSourceHikari() {
        HikariConfig hikariConfig = new HikariConfig();

        /*hikariConfig.setJdbcUrl(configYaml.getPath());
        hikariConfig.setUsername(configYaml.getUser());
        hikariConfig.setPassword(configYaml.getPassw());
        hikariConfig.setDriverClassName(configYaml.getDriver());
        hikariConfig.setMaximumPoolSize(1);*/

        hikariConfig.setJdbcUrl("jdbc:mysql://dam2.mysql.iesquevedo.es:3335/Stefan_Encriptacion");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("quevedo2dam");
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setMaximumPoolSize(1);

        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        return new HikariDataSource(hikariConfig);
    }

    public DataSource getDataSource() {
        return hikariDataSource;
    }


    @PreDestroy
    public void closePool() {
        ((HikariDataSource) hikariDataSource).close();
    }

}
