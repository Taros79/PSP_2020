package quevedo.ServerBasket.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import quevedo.ServerBasket.dao.dataMapper.PartidoMapper;
import quevedo.ServerBasket.dao.jdbc.DBConnectionPool;
import quevedo.ServerBasket.dao.utils.Queries;
import quevedo.ServerBasket.utils.constantes.MensajesErrores;
import quevedo.common.modelo.ResultadosPartidos;
import quevedo.common.modelo.dto.JornadaDTO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

@Log4j2
public class JornadasDao {

    private final DBConnectionPool pool;

    @Inject
    public JornadasDao(DBConnectionPool pool) {
        this.pool = pool;
    }

    public Either<String, List<ResultadosPartidos>> getPartidosPorJornada(LocalDate fecha) {
        Either<String, List<ResultadosPartidos>> result;

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            List<ResultadosPartidos> purchaseList = jdbcTemplate.query(Queries.PARTIDOS_JORNADA_QUERIE,
                    new PartidoMapper(), fecha);
            result = Either.right(purchaseList);
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
            result = Either.left(MensajesErrores.ERROR_AL_EXTRAER_LOS_DATOS);

        }


        return result;
    }


    public Either<String, List<JornadaDTO>> getAllJornadas() {
        Either<String, List<JornadaDTO>> result;

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            List<JornadaDTO> purchaseList = jdbcTemplate.query("SELECT  * from jornadas",
                    new BeanPropertyRowMapper<>(JornadaDTO.class));
            result = Either.right(purchaseList);
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
            result = Either.left(MensajesErrores.ERROR_AL_EXTRAER_LOS_DATOS);

        }


        return result;
    }


    public Either<String, JornadaDTO> addJornada(JornadaDTO jornadaDTO) {
        Either<String, JornadaDTO> result;

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement =
                        connection.prepareStatement(Queries.INSERT_JORNADA_QUERIE, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setDate(1, Date.valueOf(jornadaDTO.getFecha()));
                return preparedStatement;
            }, keyHolder);

            jornadaDTO.setId(keyHolder.getKey().intValue());
            result = Either.right(jornadaDTO);
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
            result = Either.left(MensajesErrores.ERROR_AL_EXTRAER_LOS_DATOS);

        }


        return result;
    }


}
