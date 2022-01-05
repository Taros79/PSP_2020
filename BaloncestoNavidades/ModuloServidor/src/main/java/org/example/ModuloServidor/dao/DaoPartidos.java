package org.example.ModuloServidor.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.HashPassword;
import org.example.Common.modelo.Partido;
import org.example.Common.modelo.Usuario;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;

public class DaoPartidos {

    private DBConnectionPool dbConnection;

    @Inject
    public DaoPartidos(DBConnectionPool dbConnection) {
        this.dbConnection = dbConnection;
    }

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


}
