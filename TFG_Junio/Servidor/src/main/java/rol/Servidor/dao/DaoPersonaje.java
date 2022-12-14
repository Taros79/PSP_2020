package rol.Servidor.dao;

import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import rol.Common.modelo.Personaje;
import rol.Common.modeloAO.PersonajeBBDD;
import rol.Servidor.dao.errores.BaseDatosCaidaException;
import rol.Servidor.dao.errores.OtraException;
import rol.Servidor.dao.jdbc.DBConnectionPool;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

import static rol.Servidor.dao.ConstantesSQL.SELECT_ALL_PERSONAJES;


@Log4j2
public class DaoPersonaje {

    private final DBConnectionPool pool;

    @Inject
    public DaoPersonaje(DBConnectionPool pool) {
        this.pool = pool;
    }


    public List<Personaje> getAllPersonajes() {
        List<Personaje> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            result = jdbcTemplate.query(SELECT_ALL_PERSONAJES,
                    new BeanPropertyRowMapper<>(Personaje.class));
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new OtraException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return result;
    }

    public String addPersonaje(PersonajeBBDD p) {
        String result;
        int i;

        JdbcTemplate jtm;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(
                pool.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);

        try {
            KeyHolder holder = new GeneratedKeyHolder();
            jtm = new JdbcTemplate(pool.getDataSource());
            jtm.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(ConstantesSQL.INSERT_ESTADISTICAS,
                        PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, p.getEstadistica().getVida());
                preparedStatement.setInt(2, p.getEstadistica().getAc());
                preparedStatement.setInt(3, p.getEstadistica().getFortaleza());
                preparedStatement.setInt(4, p.getEstadistica().getReflejos());
                preparedStatement.setInt(5, p.getEstadistica().getVoluntad());
                preparedStatement.setInt(6, p.getEstadistica().getFuerza());
                preparedStatement.setInt(7, p.getEstadistica().getDestreza());
                preparedStatement.setInt(8, p.getEstadistica().getConstitucion());
                preparedStatement.setInt(9, p.getEstadistica().getInteligencia());
                preparedStatement.setInt(10, p.getEstadistica().getSabiduria());
                preparedStatement.setInt(11, p.getEstadistica().getCarisma());
                return preparedStatement;
            }, holder);

            int filas = jtm.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(ConstantesSQL.INSERT_PERSONAJES);
                preparedStatement.setString(1, p.getPersonaje().getNombre());
                preparedStatement.setString(2, p.getPersonaje().getRaza());
                preparedStatement.setString(3, p.getPersonaje().getClase());
                preparedStatement.setString(4, p.getPersonaje().getAlineamiento());
                preparedStatement.setInt(5, p.getPersonaje().getNivel());
                preparedStatement.setInt(6, p.getPersonaje().getExperiencia());
                preparedStatement.setInt(7, Objects.requireNonNull(holder.getKey()).intValue());
                return preparedStatement;
            });

            if (filas > 0) {
                result = ConstantesSQL.ANADIDO_CON_EXITO;
            } else {
                result = ConstantesSQL.NO_SE_PUDO_BORRAR;
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

    public String delPersonaje(int id_Personaje, int id_Estadistica) {
        String result;
        int numeroFilas;

        JdbcTemplate jtm;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(
                pool.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);

        try {
            jtm = new JdbcTemplate(
                    Objects.requireNonNull(transactionManager.getDataSource()));
            jtm.update(ConstantesSQL.DEL_ALL_OBJETO_TO_PERSONAJE, id_Personaje);

            jtm.update(ConstantesSQL.DEL_ALL_HECHIZO_TO_PERSONAJE, id_Personaje);

            jtm.update(ConstantesSQL.DEL_ALL_DOTE_TO_PERSONAJE, id_Personaje);

            jtm.update(ConstantesSQL.DEL_PERSONAJES, id_Personaje);

            numeroFilas = jtm.update(ConstantesSQL.DEL_ESTADISTICAS, id_Estadistica);

            if (numeroFilas > 0) {
                result = ConstantesSQL.BORRADO_CON_EXITO;
            } else {
                result = ConstantesSQL.NO_SE_PUDO_BORRAR;
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


    public String updatePersonaje(Personaje p) {
        String result;
        int i;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            i = jdbcTemplate.update(ConstantesSQL.UPDATE_PERSONAJES,
                    p.getNombre(), p.getRaza(), p.getClase(), p.getAlineamiento(), p.getNivel(),
                    p.getExperiencia(), p.getId_Estadistica(), p.getId());

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

    public List<Personaje> getPersonajesByIdUsuario(int id) {
        List<Personaje> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            result = jdbcTemplate.query(
                    ConstantesSQL.SELECT_PERSONAJE_BY_ID_USUARIO,
                    new BeanPropertyRowMapper<>(Personaje.class), id
            );
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new OtraException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return result;
    }

    public String addPersonajeToUsuario(PersonajeBBDD p) {
        String result;
        int i;
        JdbcTemplate jtm;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(
                pool.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);

        try {
            KeyHolder holder = new GeneratedKeyHolder();
            jtm = new JdbcTemplate(pool.getDataSource());
            jtm.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(ConstantesSQL.INSERT_ESTADISTICAS,
                        PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, p.getEstadistica().getVida());
                preparedStatement.setInt(2, p.getEstadistica().getAc());
                preparedStatement.setInt(3, p.getEstadistica().getFortaleza());
                preparedStatement.setInt(4, p.getEstadistica().getReflejos());
                preparedStatement.setInt(5, p.getEstadistica().getVoluntad());
                preparedStatement.setInt(6, p.getEstadistica().getFuerza());
                preparedStatement.setInt(7, p.getEstadistica().getDestreza());
                preparedStatement.setInt(8, p.getEstadistica().getConstitucion());
                preparedStatement.setInt(9, p.getEstadistica().getInteligencia());
                preparedStatement.setInt(10, p.getEstadistica().getSabiduria());
                preparedStatement.setInt(11, p.getEstadistica().getCarisma());
                return preparedStatement;
            }, holder);

            jtm.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(ConstantesSQL.INSERT_PERSONAJES,
                        PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, p.getPersonaje().getNombre());
                preparedStatement.setString(2, p.getPersonaje().getRaza());
                preparedStatement.setString(3, p.getPersonaje().getClase());
                preparedStatement.setString(4, p.getPersonaje().getAlineamiento());
                preparedStatement.setInt(5, p.getPersonaje().getNivel());
                preparedStatement.setInt(6, p.getPersonaje().getExperiencia());
                preparedStatement.setString(8, p.getPersonaje().getImage());
                preparedStatement.setInt(7, Objects.requireNonNull(holder.getKey()).intValue());
                return preparedStatement;
            }, holder);

            i = jtm.update(ConstantesSQL.ADD_PERSONAJE_TO_USUARIO,
                    holder.getKey().intValue(),
                    p.getIdUsuario());
            if (i != 0) {
                result = ConstantesSQL.ACTUALIZADO_CON_EXITO;
            } else {
                result = ConstantesSQL.NO_SE_HA_ACTUALIZADO;
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
}
