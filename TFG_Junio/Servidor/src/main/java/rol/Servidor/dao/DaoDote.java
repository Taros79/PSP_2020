package rol.Servidor.dao;

import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import rol.Common.modelo.Dote;
import rol.Common.modeloAO.RelacionId;
import rol.Servidor.dao.errores.BaseDatosCaidaException;
import rol.Servidor.dao.errores.DataViolationException;
import rol.Servidor.dao.errores.OtraException;
import rol.Servidor.dao.jdbc.DBConnectionPool;

import java.sql.PreparedStatement;
import java.util.List;

@Log4j2
public class DaoDote {

    private final DBConnectionPool pool;

    @Inject
    public DaoDote(DBConnectionPool pool) {
        this.pool = pool;
    }

    public List<Dote> getAllDotes() {
        List<Dote> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            result = jdbcTemplate.query(ConstantesSQL.SELECT_ALL_DOTES,
                    new BeanPropertyRowMapper<>(Dote.class));
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new OtraException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return result;
    }

    public String addDote(Dote d) {
        String result;
        int i;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            i = jdbcTemplate.update(con -> {
                PreparedStatement preparedStatement = con.prepareStatement(ConstantesSQL.INSERT_DOTES);
                preparedStatement.setString(1, d.getNombre());
                preparedStatement.setString(2, d.getDescripcion());
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

    public String delDote(int id) {
        String result;
        int i;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            i = jdbcTemplate.update(ConstantesSQL.DEL_DOTES, id);

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


    public String updateDote(Dote d) {
        String result;
        int i;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            i = jdbcTemplate.update(ConstantesSQL.UPDATE_DOTES,
                    d.getNombre(), d.getDescripcion(), d.getId());

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


    public List<Dote> getDotesByIdPersonaje(int id) {
        List<Dote> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            result = jdbcTemplate.query(
                    ConstantesSQL.SELECT_DOTES_BY_ID_PERSONAJE,
                    new BeanPropertyRowMapper<>(Dote.class), id
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new OtraException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return result;
    }

    public String addDoteToPersonaje(RelacionId r) {
        String result;
        int i;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            i = jdbcTemplate.update(con -> {
                PreparedStatement preparedStatement = con.prepareStatement(ConstantesSQL.INSERT_DOTE_TO_PERSONAJE);
                preparedStatement.setInt(1, r.getId_1());
                preparedStatement.setInt(2, r.getId_2());
                return preparedStatement;
            });

            if (i != 0) {
                result = ConstantesSQL.ANADIDO_CON_EXITO;
            } else {
                result = ConstantesSQL.NO_SE_PUDO_ANADIR;
            }

        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new OtraException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return result;
    }

    public String delDoteToPersonaje(int id_Dote, int id2_Personaje) {
        String result;
        int i;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            i = jdbcTemplate.update(ConstantesSQL.DEL_DOTE_TO_PERSONAJE,
                    id_Dote, id2_Personaje);
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
}
