package quevedo.ServerBasket.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import quevedo.ServerBasket.dao.dataMapper.PartidoMapper;
import quevedo.ServerBasket.dao.jdbc.DBConnectionPool;
import quevedo.ServerBasket.dao.utils.Queries;
import quevedo.ServerBasket.utils.constantes.MensajesErrores;
import quevedo.common.modelo.ResultadosPartidos;
import quevedo.common.modelo.dto.PartidoDTO;

import java.sql.PreparedStatement;
import java.util.List;

@Log4j2
public class PartidosDao {

    private final DBConnectionPool pool;

    @Inject
    public PartidosDao(DBConnectionPool pool) {
        this.pool = pool;
    }

    public Either<String, List<ResultadosPartidos>> getAllPartidos() {

        Either<String, List<ResultadosPartidos>> result;

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            List<ResultadosPartidos> purchaseList = jdbcTemplate.query(Queries.GET_PARTIDOS_Y_RESULTADOS_QUERIE,
                    new PartidoMapper());
            result = Either.right(purchaseList);
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
            result = Either.left(MensajesErrores.ERROR_AL_EXTRAER_LOS_DATOS);

        }


        return result;
    }


    public Either<String, Boolean> addPartido(PartidoDTO partidoDTO) {
        Either<String, Boolean> result;

        try {

            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement =
                        connection.prepareStatement(Queries.INSERT_PARTIDO_QUERIE);
                preparedStatement.setInt(1, partidoDTO.getIdJornada());
                preparedStatement.setInt(2, partidoDTO.getIdEquipoLocal());
                preparedStatement.setInt(3, partidoDTO.getIdEquipoVisitante());
                preparedStatement.setInt(4, partidoDTO.getResultadoLocal());
                preparedStatement.setInt(5, partidoDTO.getResultadoVisitante());
                return preparedStatement;
            });
            result = Either.right(true);
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
            result = Either.left(MensajesErrores.ERROR_AL_EXTRAER_LOS_DATOS);

        }


        return result;
    }


}

