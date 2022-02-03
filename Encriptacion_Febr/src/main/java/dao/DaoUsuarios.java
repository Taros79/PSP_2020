package dao;

import dao.jdbc.DBConnectionPool;
import dao.modelo.Usuario;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import java.util.List;

@Log4j2
public class DaoUsuarios {

    private final DBConnectionPool pool;

    @Inject
    public DaoUsuarios(DBConnectionPool pool) {
        this.pool = pool;
    }

    public Either<String, List<Usuario>> getUsuarios() {

        Either<String, List<Usuario>> result;

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            List<Usuario> purchaseList = jdbcTemplate.query("SELECT * from usuarios;",
                    new BeanPropertyRowMapper<>(Usuario.class));
            result = Either.right(purchaseList);
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
            result = Either.left("Mamabicho");

        }
        return result;
    }
}
