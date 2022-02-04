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

        hikariConfig.setJdbcUrl(configYaml.getPath());
        hikariConfig.setUsername(configYaml.getUser());
        hikariConfig.setPassword(configYaml.getPassw());
        hikariConfig.setDriverClassName(configYaml.getDriver());
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
