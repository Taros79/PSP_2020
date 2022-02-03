package quevedo.ServerBasket.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import quevedo.ServerBasket.dao.jdbc.DBConnectionPool;
import quevedo.ServerBasket.dao.model.UserDatabase;
import quevedo.ServerBasket.dao.utils.Queries;
import quevedo.ServerBasket.utils.constantes.MensajesErrores;
import quevedo.common.modelo.User;

@Log4j2
public class LoginDao {


    private final DBConnectionPool pool;

    @Inject
    public LoginDao(DBConnectionPool pool) {
        this.pool = pool;
    }


    public Either<String, User> getUser(String correo) {
        Either<String, User> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            UserDatabase userDatabase = jdbcTemplate.queryForObject(Queries.GET_USER_CORREO_QUERIE,
                    BeanPropertyRowMapper.newInstance(UserDatabase.class), correo);
            if (userDatabase != null) {
                if (userDatabase.isActivo()) {
                    result = Either.right(new User(userDatabase.getCorreo(), userDatabase.getPassword(), userDatabase.getIdTipoUser()));
                } else {
                    result = Either.left(MensajesErrores.DEBE_ACTIVAR_LA_CUENTA);
                }

            } else {
                result = Either.left(MensajesErrores.NO_EXISE_ESE_USUARIO);
            }

        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
            result = Either.left(MensajesErrores.ERROR_AL_EXTRAER_LOS_DATOS);

        }

        return result;
    }


}
