package quevedo.ServerBasket.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import quevedo.ServerBasket.dao.jdbc.DBConnectionPool;
import quevedo.ServerBasket.dao.model.UserDatabase;
import quevedo.ServerBasket.dao.utils.Queries;
import quevedo.ServerBasket.utils.constantes.ConstantesParametros;
import quevedo.ServerBasket.utils.constantes.MensajesErrores;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


@Log4j2
public class RegisterDao {


    private final DBConnectionPool pool;

    @Inject
    public RegisterDao(DBConnectionPool pool) {
        this.pool = pool;
    }


    public Either<String, Boolean> addUser(UserDatabase user) {
        Either<String, Boolean> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            jdbcTemplate.update(con -> {
                PreparedStatement preparedStatement = con.prepareStatement(Queries.INSERT_USER);
                preparedStatement.setString(1, user.getPassword());
                preparedStatement.setString(2, user.getCorreo());
                preparedStatement.setString(3, user.getCodActivacion());
                preparedStatement.setObject(4, user.getFechaLimiteActivacion());
                preparedStatement.setBoolean(5, user.isActivo());
                preparedStatement.setInt(6, user.getIdTipoUser());
                return preparedStatement;
            });
            result = Either.right(true);

        } catch (EmptyResultDataAccessException sqle) {
            log.error(sqle.getMessage());
            result = Either.left(MensajesErrores.FALTA_ALGUN_PARAMETRO);

        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage());
            result = Either.left(MensajesErrores.ESE_USUARIO_YA_EXISTE);
        }


        return result;

    }

    public Either<String, Boolean> activarUser(String codActivacion) {

        Either<String, Boolean> result = null;

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            jdbcTemplate.update(Queries.ACTIVAR_QUERIE, true, codActivacion);
            result = Either.right(true);
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
            result = Either.left(MensajesErrores.NO_SE_HA_PODIDO_ACTIVAR_EL_USUARIO);
        }


        return result;

    }


    public Either<String, Boolean> comprobarFecha(String codActivacion) {

        Either<String, Boolean> result = null;
        LocalDateTime tiempoLimite = null;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            tiempoLimite = jdbcTemplate.queryForObject(Queries.OBTENER_FECHA_QUERIE,
                    LocalDateTime.class, codActivacion);
            if (LocalDateTime.now(ZoneId.of(ConstantesParametros.EUROPE_MADRID)).isBefore(tiempoLimite)) {
                result = Either.right(true);
            } else {
//                Mando de nuevo el correo
                result = Either.right(false);
            }

        } catch (IncorrectResultSizeDataAccessException ex) {
            log.error(ex.getMessage());
            result = Either.left(MensajesErrores.NO_SE_HA_ENCONTRADO_ESE_CODIGO_DE_ACTIVACION);
        }
        return result;

    }


    public Either<String, Boolean> cambiarCodActivacion(String codNuevo, String codActivacion) {

        Either<String, Boolean> result = null;

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            jdbcTemplate.update(Queries.ADD_MINS_QUERIE, LocalDateTime.now(ZoneId.of(ConstantesParametros.EUROPE_MADRID)).plusMinutes(10), codNuevo, codActivacion);
            result = Either.right(true);
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
            result = Either.left(MensajesErrores.NO_SE_HA_PODIDO_ACTUALIZAR_EL_CODIGO_DE_ACTIVACION);
        }


        return result;

    }


}
