package org.example.ModuloServidor.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Equipo;
import org.example.Common.modelo.Jornada;
import org.example.Common.modelo.Partido;
import org.example.Common.modelo.UsuarioRegistro;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
public class DaoPartidos {

    private DBConnectionPool dbConnection;

    @Inject
    public DaoPartidos(DBConnectionPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    private static final String INSERT_PARTIDO =
            "insert into partidos (idJornada, idLocal, idVisitante, resultadoLocal, resultadoVisitante) values (?,?,?,?,?)";

    private static final String INSERT_EQUIPO =
            "insert into equipos (nombre) values (?)";

    private static final String INSERT_JORNADA =
            "insert into jornadas (fecha) values (?)";


    public Either<ApiError, List<Partido>> getPartidos() {
        Either<ApiError, List<Partido>> resultado;
        JdbcTemplate jtm = new JdbcTemplate(
                dbConnection.getDataSource());

        if (!jtm.query("select * from partidos",
                BeanPropertyRowMapper.newInstance(Partido.class)).isEmpty()) {
            resultado = Either.right(jtm.query("select * from partidos",
                    BeanPropertyRowMapper.newInstance(Partido.class)));
        } else {
            resultado = Either.left(new ApiError("ConstantesDao.NO_HAY_ELEMENTOS", LocalDateTime.now()));
        }
        return resultado;
    }

    public Either<ApiError, ApiRespuesta>  addPartido(Partido p) {
        Either<ApiError, ApiRespuesta> resultado;
        if (p != null) {
            try {
                JdbcTemplate jtm = new JdbcTemplate(
                        dbConnection.getDataSource());
                jtm.update(INSERT_PARTIDO,
                        p.getIdJornada(),
                        p.getIdLocal(),
                        p.getIdLocal(),
                        p.getIdVisitante(),
                        p.getResultadoVisitante());
                resultado = Either.right(new ApiRespuesta("Equipo creado", LocalDateTime.now()));
            } catch (DataAccessException e) {
                resultado = Either.left(new ApiError("Fallo en la base de datos", LocalDateTime.now()));
                log.error(e.getMessage(), e);
            }
        } else {
            resultado = Either.left(new ApiError("El objeto esta a null", LocalDateTime.now()));
        }

        return resultado;
    }

    public Either<ApiError, ApiRespuesta>  addEquipo(Equipo equipo) {
        Either<ApiError, ApiRespuesta> resultado;
        if (equipo != null) {
            try {
                JdbcTemplate jtm = new JdbcTemplate(
                        dbConnection.getDataSource());
                jtm.update(INSERT_EQUIPO, equipo.getNombre());
                resultado = Either.right(new ApiRespuesta("Equipo creado", LocalDateTime.now()));
            } catch (DataAccessException e) {
                resultado = Either.left(new ApiError("Fallo en la base de datos", LocalDateTime.now()));
                log.error(e.getMessage(), e);
            }
        } else {
            resultado = Either.left(new ApiError("El objeto esta a null", LocalDateTime.now()));
        }

        return resultado;
    }

    public Either<ApiError, ApiRespuesta>  addJornada(Jornada jornada) {
        Either<ApiError, ApiRespuesta> resultado;
        if (jornada != null) {
            try {
                JdbcTemplate jtm = new JdbcTemplate(
                        dbConnection.getDataSource());
                jtm.update(INSERT_JORNADA, jornada.getFecha());
                resultado = Either.right(new ApiRespuesta("Jornada creada", LocalDateTime.now()));
            } catch (DataAccessException e) {
                resultado = Either.left(new ApiError("Fallo en la base de datos", LocalDateTime.now()));
                log.error(e.getMessage(), e);
            }
        } else {
            resultado = Either.left(new ApiError("El objeto esta a null", LocalDateTime.now()));
        }

        return resultado;
    }

}
