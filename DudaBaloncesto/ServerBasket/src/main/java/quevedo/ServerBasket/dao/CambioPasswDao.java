package quevedo.ServerBasket.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import quevedo.ServerBasket.dao.jdbc.DBConnectionPool;
import quevedo.ServerBasket.dao.utils.Queries;
import quevedo.ServerBasket.utils.constantes.ConstantesParametros;
import quevedo.ServerBasket.utils.constantes.MensajesErrores;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Log4j2
public class CambioPasswDao {

    private final DBConnectionPool pool;

    @Inject
    public CambioPasswDao(DBConnectionPool pool) {
        this.pool = pool;
    }

    public Either<String, Boolean> comprobarUser(String correo) {
        Either<String, Boolean> result;
        int obtenido = 0;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            obtenido = jdbcTemplate.queryForObject(Queries.CONFIRMAR_CORREO_QUERIE,
                    Integer.class, correo);
            result = Either.right(true);
        } catch (IncorrectResultSizeDataAccessException exception) {
            log.error(exception.getMessage());
            result = Either.left(MensajesErrores.ERROR_DE_CONSULTA);
        }

        if (obtenido == 0) {
            result = Either.left(MensajesErrores.NO_EXISTE_ESE_USUARIO);
        }

        return result;


    }


    public Either<String, Boolean> addCodPasswAndTime(String cod, String correo) {

        Either<String, Boolean> result = null;

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            jdbcTemplate.update(Queries.ADD_TIME_AND_COOD_QUERIE,
                    cod, LocalDateTime.now(ZoneId.of(ConstantesParametros.EUROPE_MADRID)).plusMinutes(35), correo);
            result = Either.right(true);
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
            result = Either.left(MensajesErrores.NO_SE_HA_PODIDO_AÑADIR_EL_CODIGO_DE_CAMBIO_DE_CONTRASEÑA);
        }


        return result;


    }


    public Either<String, Boolean> comprobarFecha(String codActivacion) {

        Either<String, Boolean> result = null;
        LocalDateTime tiempoLimite = null;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            tiempoLimite = jdbcTemplate.queryForObject(Queries.TIEMPO_ACTIVACION_ACTUALIZACINO_PASSW_QUIERIE,
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


    public Either<String, Boolean> modificarPassw(String passw, String codPassw) {

        Either<String, Boolean> result = null;

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            jdbcTemplate.update(Queries.PASSW_MODIFICATION_QUIERIE,
                    passw, codPassw);
            result = Either.right(true);
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
            result = Either.left(MensajesErrores.NO_SE_HA_PODIDO_MODIFICAR_LA_CONTRASEÑA);
        }


        return result;


    }
}
