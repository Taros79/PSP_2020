package rol.Servidor.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import rol.Common.modelo.Usuario;
import rol.Servidor.dao.errores.BaseDatosCaidaException;
import rol.Servidor.dao.errores.DataViolationException;
import rol.Servidor.dao.errores.OtraException;
import rol.Servidor.dao.jdbc.DBConnectionPool;
import rol.Servidor.utils.HashPassword;

import java.sql.PreparedStatement;
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

    public String addUsuario(Usuario u) {
        String result;
        int i;
        String passwordHasheada = hashPassword.hashPassword(u.getContraseña());
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            i = jdbcTemplate.update(con -> {
                PreparedStatement preparedStatement = con.prepareStatement(ConstantesSQL.INSERT_USUARIOS);
                preparedStatement.setString(1, u.getCorreo());
                preparedStatement.setString(2, passwordHasheada);
                preparedStatement.setInt(3, u.getTipo_Usuario());
                preparedStatement.setInt(4, u.getBaneado());
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

    public String delUsuario(int id) {
        String result;
        int i;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            i = jdbcTemplate.update(ConstantesSQL.DEL_USUARIOS, id);

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


    public String updateUsuario(Usuario u) {
        String result;
        int i;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            i = jdbcTemplate.update(ConstantesSQL.UPDATE_USUARIOS,
                    u.getCorreo(), u.getContraseña(), u.getTipo_Usuario(), u.getId());

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

    public Usuario getUsuarioByCorreo(String correo, String pass) {
        Usuario sql;
        Usuario result = null;
        String passwordHasheada = hashPassword.hashPassword(pass);
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            sql = jdbcTemplate.queryForObject(ConstantesSQL.SELECT_USUARIO_BY_CORREO,
                    new BeanPropertyRowMapper<>(Usuario.class), correo);

            if (sql != null) {
                if (passwordHasheada.equals(sql.getContraseña())) {
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
            sql = jdbcTemplate.queryForObject(ConstantesSQL.SELECT_USUARIO_BY_CORREO,
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

    public Either <String,Usuario> getUsuarioByCorreoCredentials(String correo, String pass) {
        Either<String, Usuario> result = null;
        Usuario sql;
        String passwordHasheada = hashPassword.hashPassword(pass);
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            sql = jdbcTemplate.queryForObject(ConstantesSQL.SELECT_USUARIO_BY_CORREO,
                    new BeanPropertyRowMapper<>(Usuario.class), correo);

            if (sql != null) {
                if (passwordHasheada.equals(sql.getContraseña())) {
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
