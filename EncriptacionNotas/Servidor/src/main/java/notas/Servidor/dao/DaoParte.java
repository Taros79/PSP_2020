package notas.Servidor.dao;

import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import notas.Common.modelo.Parte;
import notas.Servidor.dao.encriptaciones.EncriptarSimetrico;
import notas.Servidor.dao.errores.BaseDatosCaidaException;
import notas.Servidor.dao.errores.OtraException;
import notas.Servidor.dao.jdbc.DBConnectionPool;
import notas.Servidor.utils.HashPassword;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;


@Log4j2
public class DaoParte {

    private final DBConnectionPool pool;
    private final HashPassword hashPassword;
    private final EncriptarSimetrico encriptarSimetrico;

    @Inject
    public DaoParte(DBConnectionPool pool, HashPassword hashPassword, EncriptarSimetrico encriptarSimetrico) {
        this.pool = pool;
        this.hashPassword = hashPassword;
        this.encriptarSimetrico = encriptarSimetrico;
    }


    public List<Parte> getAllPartes() {
        List<Parte> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            result = jdbcTemplate.query(ConstantesSQL.SELECT_ALL_PARTES,
                    new BeanPropertyRowMapper<>(Parte.class));

            for (Parte parte : result) {
                var mensajeDesencriptado = encriptarSimetrico.desencriptarTexto(parte.getDescripcion());
                if (mensajeDesencriptado.isRight()) {
                    parte.setDescripcion(mensajeDesencriptado.get());
                }
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

    public List<Parte> getPartesByUser(int idPadre) {
        List<Parte> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            result = jdbcTemplate.query(ConstantesSQL.SELECT_PARTES_PADRE_ALUMNOS,
                    new BeanPropertyRowMapper<>(Parte.class), idPadre);

            for (Parte parte : result) {
                var mensajeDesencriptado = encriptarSimetrico.desencriptarTexto(parte.getDescripcion());
                if (mensajeDesencriptado.isRight()) {
                    parte.setDescripcion(mensajeDesencriptado.get());
                }
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

    public String addParte(Parte parte) {
        String result = null;
        try {
            var mensajeEncriptado = encriptarSimetrico.encriptarTexto(parte.getDescripcion());

            if (mensajeEncriptado.isRight()) {
                JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
                jdbcTemplate.update(ConstantesSQL.INSERT_PARTE,
                        mensajeEncriptado.get(),
                        parte.getIdAlumno(),
                        1);
                result = ConstantesSQL.ANADIDO_CON_EXITO;
            } else {
                result = mensajeEncriptado.getLeft();
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

    public String deleteParte(int idParte) {
        String result = null;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            jdbcTemplate.update(ConstantesSQL.DELETE_PARTE, idParte);
            result = ConstantesSQL.BORRADO_CON_EXITO;
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new OtraException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return result;
    }

    public String updateParte(int idParte, int estado) {
        String result = null;
        try {
                JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
                jdbcTemplate.update(ConstantesSQL.UPDATE_PARTE,
                        estado,
                        idParte);
                result = ConstantesSQL.ACTUALIZADO_CON_EXITO;

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
