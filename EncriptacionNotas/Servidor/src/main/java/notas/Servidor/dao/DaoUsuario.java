package notas.Servidor.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import notas.Common.modelo.Usuario;
import notas.Servidor.dao.errores.BaseDatosCaidaException;
import notas.Servidor.dao.errores.OtraException;
import notas.Servidor.dao.jdbc.DBConnectionPool;
import notas.Servidor.utils.HashPassword;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;


@Log4j2
public class DaoUsuario {

    private final DBConnectionPool pool;
    private final HashPassword hashPassword;

    @Inject
    public DaoUsuario(DBConnectionPool pool, HashPassword hashPassword) {
        this.pool = pool;
        this.hashPassword = hashPassword;
    }


    public List<Usuario> getAllUsuarios() {
        List<Usuario> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            result = jdbcTemplate.query(ConstantesSQL.SELECT_ALL_USUARIOS,
                    new BeanPropertyRowMapper<>(Usuario.class));
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new OtraException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return result;
    }

    public Usuario getUsuarioByNombre(String nombre, String pass) {
        Usuario sql;
        Usuario result = null;
        String passwordHasheada = hashPassword.hashPassword(pass);
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            sql = jdbcTemplate.queryForObject(ConstantesSQL.SELECT_USUARIO_BY_USUARIO,
                    new BeanPropertyRowMapper<>(Usuario.class), nombre);

            if (sql != null) {
                if (passwordHasheada.equals(sql.getPass())) {
                    result = sql;
                } else {
                    throw new OtraException(ConstantesSQL.CONTRASEÑA_CORREO_INCORRECTO);
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

    public Usuario getUsuarioByName(String correo) {
        Usuario sql;
        Usuario result = null;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            sql = jdbcTemplate.queryForObject(ConstantesSQL.SELECT_USUARIO_BY_USUARIO,
                    new BeanPropertyRowMapper<>(Usuario.class), correo);

            if (sql != null) {
                result = sql;
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

    public Either<String, Usuario> getUsuarioByCorreoCredentials(String correo, String pass) {
        Either<String, Usuario> result = null;
        Usuario sql;
        String passwordHasheada = hashPassword.hashPassword(pass);
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            sql = jdbcTemplate.queryForObject(ConstantesSQL.SELECT_USUARIO_BY_USUARIO,
                    new BeanPropertyRowMapper<>(Usuario.class), correo);

            if (sql != null) {
                if (passwordHasheada.equals(sql.getPass())) {
                    result = Either.right(sql);
                } else {
                    result = Either.left(ConstantesSQL.CONTRASEÑA_CORREO_INCORRECTO);
                }
            }
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());
            result = Either.left(ConstantesSQL.CONTRASEÑA_CORREO_INCORRECTO);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            result = Either.left(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (Exception e) {
            log.error(e.getMessage());
            result = Either.left(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return result;
    }
}
