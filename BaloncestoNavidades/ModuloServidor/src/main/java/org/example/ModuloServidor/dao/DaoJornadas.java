package org.example.ModuloServidor.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Jornada;
import org.example.ModuloServidor.utils.Constantes;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
public class DaoJornadas {

    private static final String INSERT_JORNADA = "insert into jornadas (fecha) values (?)";
    public static final String UPDATE_JORNADA = "UPDATE jornadas SET fecha = ? WHERE id = ?";
    public static final String DELETE_JORNADA = "delete from jornadas where id = ?";

    private DBConnectionPool dbConnection;

    @Inject
    public DaoJornadas(DBConnectionPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Either<ApiError, List<Jornada>> getJornadas() {
        Either<ApiError, List<Jornada>> resultado;
        JdbcTemplate jtm = new JdbcTemplate(
                dbConnection.getDataSource());

        if (!jtm.query("select * from jornadas",
                BeanPropertyRowMapper.newInstance(Jornada.class)).isEmpty()) {
            resultado = Either.right(jtm.query("select * from jornadas",
                    BeanPropertyRowMapper.newInstance(Jornada.class)));
        } else {
            resultado = Either.left(new ApiError(Constantes.NO_HAY_ELEMENTOS, LocalDateTime.now()));
        }
        return resultado;
    }

    public Either<ApiError, ApiRespuesta> addJornada(Jornada jornada) {
        Either<ApiError, ApiRespuesta> resultado;
        if (jornada != null) {
            try {
                JdbcTemplate jtm = new JdbcTemplate(
                        dbConnection.getDataSource());
                jtm.update(INSERT_JORNADA, jornada.getFecha());
                resultado = Either.right(new ApiRespuesta(Constantes.JORNADA_CREADA, LocalDateTime.now()));
            } catch (DataAccessException e) {
                resultado = Either.left(new ApiError(Constantes.FALLO_EN_LA_BBDD, LocalDateTime.now()));
                log.error(e.getMessage(), e);
            }
        } else {
            resultado = Either.left(new ApiError(Constantes.NO_HAY_ELEMENTOS, LocalDateTime.now()));
        }

        return resultado;
    }

    public String updateJornada(String date, String id) {
        String resultado;
        try {
            JdbcTemplate jtm = new JdbcTemplate(
                    dbConnection.getDataSource());
            jtm.update(UPDATE_JORNADA, date, id);
            resultado = Constantes.ACTUALIZADO;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Constantes.FALLO_EN_LA_BBDD;
        }
        return resultado;
    }

    public Either<ApiError, ApiRespuesta> delJornada(String id) {
        Either<ApiError, ApiRespuesta> resultado;
        if (id != null) {
            try {
                JdbcTemplate jtm = new JdbcTemplate(
                        dbConnection.getDataSource());
                jtm.update(DELETE_JORNADA, id);
                resultado = Either.right(new ApiRespuesta(Constantes.BORRADO, LocalDateTime.now()));
            } catch (DataAccessException e) {
                resultado = Either.left(new ApiError(Constantes.FALLO_EN_LA_BBDD, LocalDateTime.now()));
                log.error(e.getMessage(), e);
            }
        } else {
            resultado = Either.left(new ApiError(Constantes.NO_EXISTE_OBJETO, LocalDateTime.now()));
        }

        return resultado;
    }
}
