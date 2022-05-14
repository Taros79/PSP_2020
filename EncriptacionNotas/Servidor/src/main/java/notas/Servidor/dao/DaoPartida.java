package notas.Servidor.dao;

import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import notas.Servidor.dao.jdbc.DBConnectionPool;

@Log4j2
public class DaoPartida {

    private final DBConnectionPool pool;

    @Inject
    public DaoPartida(DBConnectionPool pool) {
        this.pool = pool;
    }


}
