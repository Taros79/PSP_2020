package quevedo.ServerBasket.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import quevedo.ServerBasket.dao.dataMapper.PartidoMapper;
import quevedo.ServerBasket.dao.jdbc.DBConnectionPool;
import quevedo.ServerBasket.dao.utils.Queries;
import quevedo.ServerBasket.utils.constantes.MensajesErrores;
import quevedo.common.modelo.ResultadosPartidos;
import quevedo.common.modelo.dto.EquipoDTO;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Log4j2
public class EquiposDao {

    private final DBConnectionPool pool;

    @Inject
    public EquiposDao(DBConnectionPool pool) {
        this.pool = pool;
    }


    public Either<String, List<ResultadosPartidos>> getPartidosFiltradosPorEquipo(String nombreEquipo) {

        Either<String, List<ResultadosPartidos>> result;

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            List<ResultadosPartidos> purchaseList = jdbcTemplate.query(Queries.PARTIDOS_POR_EQUIPO_QUERIE,
                    new PartidoMapper(), nombreEquipo, nombreEquipo);
            result = Either.right(purchaseList);
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
            result = Either.left(MensajesErrores.ERROR_AL_EXTRAER_LOS_DATOS);

        }


        return result;
    }


    public Either<String, List<EquipoDTO>> getAllEquipos() {

        Either<String, List<EquipoDTO>> result;

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            List<EquipoDTO> purchaseList = jdbcTemplate.query(Queries.SELECT_FROM_EQUIPOS,
                    new BeanPropertyRowMapper<>(EquipoDTO.class));
            result = Either.right(purchaseList);
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
            result = Either.left(MensajesErrores.ERROR_AL_EXTRAER_LOS_DATOS);

        }


        return result;
    }

    public Either<String, EquipoDTO> addEquipo(EquipoDTO equipoDTO) {
        Either<String, EquipoDTO> result;

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement =
                        connection.prepareStatement(Queries.INSERT_EQUIPO_QUERIE, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, equipoDTO.getNombre());
                return preparedStatement;
            }, keyHolder);

            equipoDTO.setId(keyHolder.getKey().intValue());
            result = Either.right(equipoDTO);
        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage());
            result = Either.left(MensajesErrores.ESE_NOMBRE_DE_EQUIPO_YA_ESTA_COGIDO);
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
            result = Either.left(MensajesErrores.ERROR_AL_EXTRAER_LOS_DATOS);

        }


        return result;
    }


}
