package org.example.ModuloServidor.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.Common.modelo.User;
import org.example.ModuloServidor.dao.jdbc.DBConnectionPool;
import org.example.ModuloServidor.dao.model.UserDatabase;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

@Log4j2
public class DaoLogin {


    private final DBConnectionPool pool;

    @Inject
    public DaoLogin(DBConnectionPool pool) {
        this.pool = pool;
    }


    public Either<String, User> getUser(String correo) {
        Either<String, User> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            UserDatabase userDatabase = jdbcTemplate.queryForObject(
                    "SELECT * from usuarios where correo = ?",
                    BeanPropertyRowMapper.newInstance(UserDatabase.class), correo);
            if (userDatabase != null) {
                if (userDatabase.isActivo()) {
                    result = Either.right(new User(
                            userDatabase.getCorreo(),
                            userDatabase.getHashedPassword(),
                            userDatabase.getTipoUsuario()));
                } else {
                    result = Either.left("Debe activar la cuenta");
                }

            } else {
                result = Either.left("No exise ese usuario");
            }

        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
            result = Either.left("Usuario o contraseña mal");

        }

        return result;
    }


}
