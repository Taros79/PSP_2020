package rol.Servidor.dao;

import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import rol.Common.modelo.Partida;
import rol.Common.modelo.PersonajesPartida;
import rol.Servidor.dao.errores.BaseDatosCaidaException;
import rol.Servidor.dao.errores.DataViolationException;
import rol.Servidor.dao.errores.OtraException;
import rol.Servidor.dao.jdbc.DBConnectionPool;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;


@Log4j2
public class DaoPartida {

    private final DBConnectionPool pool;

    @Inject
    public DaoPartida(DBConnectionPool pool) {
        this.pool = pool;
    }


    public List<Partida> getAllPartidasByMaster(int idMaster) {
        List<Partida> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            result = jdbcTemplate.query(ConstantesSQL.SELECT_PARTIDAS_BY_MASTER,
                    new BeanPropertyRowMapper<>(Partida.class), idMaster);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new OtraException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return result;
    }

    public String addPartida(Partida p) {
        String result;
        int i;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            i = jdbcTemplate.update(con -> {
                PreparedStatement preparedStatement = con.prepareStatement(ConstantesSQL.INSERT_PARTIDAS);
                preparedStatement.setDate(1, Date.valueOf(p.getHoraInicio()));
                preparedStatement.setDouble(2, p.getDuracion());
                preparedStatement.setInt(3, p.getNumMaxParticipantes());
                preparedStatement.setInt(4, p.getId_Master());
                return preparedStatement;
            });

            if (i != 0) {
                result = ConstantesSQL.ANADIDO_CON_EXITO;
            } else {
                result = ConstantesSQL.NO_SE_PUDO_ANADIR;
            }

        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new OtraException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return result;
    }

    public String delPartida(int id) {
        String result;
        int i;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            i = jdbcTemplate.update(ConstantesSQL.DEL_PARTIDAS, id);

            if (i != 0) {
                result = ConstantesSQL.BORRADO_CON_EXITO;
            } else {
                result = ConstantesSQL.NO_SE_PUDO_BORRAR;
            }

        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage(), e);
            throw new DataViolationException(ConstantesSQL.DATOS_RELACIONADOS_NO_SE_PUEDE_BORRAR);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new OtraException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return result;
    }


    public String updatePartida(Partida p) {
        String result;
        int i;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            i = jdbcTemplate.update(ConstantesSQL.UPDATE_PARTIDAS,
                    p.getHoraInicio(), p.getDuracion(), p.getId_Master(), p.getId());

            if (i != 0) {
                result = ConstantesSQL.ACTUALIZADO_CON_EXITO;
            } else {
                result = ConstantesSQL.NO_SE_HA_ACTUALIZADO;
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new OtraException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return result;
    }

    public String addPersonajeToPartida(int idPartida, int idPersonaje) {
        String result;
        List<PersonajesPartida> numeroFilas;
        Partida partida;

        JdbcTemplate jtm;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(
                pool.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);

        try {
            jtm = new JdbcTemplate(transactionManager.getDataSource());

            partida = jtm.queryForObject(ConstantesSQL.SELECT_PARTIDA_BY_ID,
                    new BeanPropertyRowMapper<>(Partida.class), idPartida);

            numeroFilas = jtm.query(ConstantesSQL.SELECT_PERSONAJES_PARTIDA_BY_IDPARTIDA,
                    new BeanPropertyRowMapper<>(PersonajesPartida.class), idPartida);

            assert partida != null;
            if (numeroFilas.size() >= partida.getNumMaxParticipantes()) {
                jtm.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(ConstantesSQL.INSERT_PERSONAJE_PARTIDA);
                    preparedStatement.setInt(1, idPartida);
                    preparedStatement.setInt(2, idPersonaje);
                    return preparedStatement;
                });
            }

            if (numeroFilas.size() >= partida.getNumMaxParticipantes()) {
                result = ConstantesSQL.ANADIDO_CON_EXITO;
            } else {
                result = ConstantesSQL.NO_SE_PUDO_ANADIR;
            }

            transactionManager.commit(txStatus);

        } catch (DataAccessException e) {
            log.error(e.getMessage());
            transactionManager.rollback(txStatus);
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (Exception e) {
            log.error(e.getMessage());
            transactionManager.rollback(txStatus);
            throw new OtraException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return result;
    }

    public List<Partida> getAllPartidas() {
        List<Partida> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            result = jdbcTemplate.query(ConstantesSQL.SELECT_ALL_PARTIDAS,
                    new BeanPropertyRowMapper<>(Partida.class));
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new OtraException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return result;
    }

/*
    public List<Partida> getPartidasByJugador(int id) {
        List<Partida> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            result = jdbcTemplate.query(ConstantesSQL.SELECT_PARTIDAS_BY_USUARIO,
                    new Object[]{id}, new BeanPropertyRowMapper<>(Partida.class));
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new OtraException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return result;
    }

    public List<Partida> getPartidasByJugadorAndMaster(int idJugador, int idMaster) {
        List<Partida> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            result = jdbcTemplate.query(ConstantesSQL.SELECT_PARTIDAS_BY_JUGADOR_AND_MASTER,
                    new Object[]{idJugador, idMaster}, new BeanPropertyRowMapper<>(Partida.class));
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new OtraException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return result;
    }

    public void deletePartidaByMaster(int idMaster) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            jdbcTemplate.update(ConstantesSQL.DELETE_PARTIDA_BY_MASTER, idMaster);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new OtraException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
    }*/
}
