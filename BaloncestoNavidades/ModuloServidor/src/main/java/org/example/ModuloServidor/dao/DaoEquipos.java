package org.example.ModuloServidor.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Equipo;
import org.example.ModuloServidor.dao.jdbc.DBConnectionPool;
import org.example.ModuloServidor.utils.Constantes;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
public class DaoEquipos {

    private static final String INSERT_EQUIPO = "insert into equipos (nombre) values (?)";
    public static final String UPDATE_EQUIPO = "UPDATE equipos SET nombre = ? WHERE idEquipo = ?";
    public static final String DELETE_EQUIPO = "delete from equipos where nombre = ?";

    private DBConnectionPool dbConnection;

    @Inject
    public DaoEquipos(DBConnectionPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Either<ApiError, List<Equipo>> getEquipos() {
        Either<ApiError, List<Equipo>> resultado;
        JdbcTemplate jtm = new JdbcTemplate(
                dbConnection.getDataSource());

        if (!jtm.query("select * from equipos",
                BeanPropertyRowMapper.newInstance(Equipo.class)).isEmpty()) {
            resultado = Either.right(jtm.query("select * from equipos",
                    BeanPropertyRowMapper.newInstance(Equipo.class)));
        } else {
            resultado = Either.left(new ApiError(Constantes.NO_HAY_ELEMENTOS, LocalDateTime.now()));
        }
        return resultado;
    }

    public Either<ApiError, ApiRespuesta> addEquipo(Equipo equipo) {
        Either<ApiError, ApiRespuesta> resultado;
        if (equipo != null) {
            try {
                JdbcTemplate jtm = new JdbcTemplate(
                        dbConnection.getDataSource());
                jtm.update(INSERT_EQUIPO, equipo.getNombre());
                resultado = Either.right(new ApiRespuesta(Constantes.EQUIPO_CREADO, LocalDateTime.now()));
            } catch (DataAccessException e) {
                resultado = Either.left(new ApiError(Constantes.FALLO_EN_LA_BBDD, LocalDateTime.now()));
                log.error(e.getMessage(), e);
            }
        } else {
            resultado = Either.left(new ApiError(Constantes.NO_HAY_ELEMENTOS, LocalDateTime.now()));
        }

        return resultado;
    }

    public String updateEquipo(String nombreNew, String id) {
        String resultado;
        try {
            JdbcTemplate jtm = new JdbcTemplate(
                    dbConnection.getDataSource());
            jtm.update(UPDATE_EQUIPO, nombreNew, id);
            resultado = Constantes.ACTUALIZADO;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Constantes.FALLO_EN_LA_BBDD;
        }
        return resultado;
    }

    public Either<ApiError, ApiRespuesta> delEquipo(String u) {
        Either<ApiError, ApiRespuesta> resultado;
        if (u != null) {
            try {
                JdbcTemplate jtm = new JdbcTemplate(
                        dbConnection.getDataSource());
                jtm.update(DELETE_EQUIPO, u);
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
