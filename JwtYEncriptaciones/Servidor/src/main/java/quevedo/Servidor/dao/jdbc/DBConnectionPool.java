package quevedo.Servidor.dao.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import quevedo.Servidor.config.ConfigYaml;

import javax.sql.DataSource;

@Singleton
public class DBConnectionPool {
    private final ConfigYaml configYaml;
    private DataSource hikariDataSource;

    @Inject
    public DBConnectionPool(ConfigYaml configYaml) {
        this.configYaml = configYaml;
        hikariDataSource = getDataSourceHikari();

    }

    private DataSource getDataSourceHikari() {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(this.configYaml.getPath());
        hikariConfig.setUsername(this.configYaml.getUser());
        hikariConfig.setPassword(this.configYaml.getPassw());
        hikariConfig.setDriverClassName(this.configYaml.getDriver());
        hikariConfig.setMaximumPoolSize(1);

        hikariConfig.addDataSourceProperty(JDBConstantes.CACHE_PREP_STMTS, JDBConstantes.TRUE);
        hikariConfig.addDataSourceProperty(JDBConstantes.PREP_STMT_CACHE_SIZE, JDBConstantes.VALUE);
        hikariConfig.addDataSourceProperty(JDBConstantes.PREP_STMT_CACHE_SQL_LIMIT, JDBConstantes.VALUE_SQL_LIMIT);

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
