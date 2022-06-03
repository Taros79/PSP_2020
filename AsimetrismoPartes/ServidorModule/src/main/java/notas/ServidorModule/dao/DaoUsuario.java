package notas.ServidorModule.dao;

import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import notas.CommonModule.modelo.Usuario;
import notas.ServidorModule.EE.security.encriptaciones.KeyStoreBuild;
import notas.ServidorModule.dao.errores.BaseDatosCaidaException;
import notas.ServidorModule.dao.errores.OtraException;
import notas.ServidorModule.dao.errores.ServidorException;
import notas.ServidorModule.dao.jdbc.DBConnectionPool;
import notas.ServidorModule.utils.HashPassword;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;


@Log4j2
public class DaoUsuario {

    private final DBConnectionPool pool;
    private final HashPassword hashPassword;

    @Inject
    public DaoUsuario(DBConnectionPool pool, HashPassword hashPassword, KeyStoreBuild keyStoreBuild) {
        this.pool = pool;
        this.hashPassword = hashPassword;
    }


    public List<Usuario> getAllUsuarios() {
        List<Usuario> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            result = jdbcTemplate.query(ConstantesSQL.SELECT_ALL_USUARIOS,
                    new BeanPropertyRowMapper<>(Usuario.class));
        } catch (BaseDatosCaidaException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (ServidorException e) {
            log.error(e.getMessage());
            throw new ServidorException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return result;
    }

    public Usuario getUsuarioByNombre(String nombre, String pass) {
        Usuario sql;
        Usuario result;
        String passwordHasheada = hashPassword.hashPassword(pass);
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            sql = jdbcTemplate.queryForObject(ConstantesSQL.SELECT_USUARIO_BY_NAME,
                    new BeanPropertyRowMapper<>(Usuario.class), nombre);

            if (passwordHasheada.equals(sql.getPass())) {
                result = sql;
            } else {
                log.error(ConstantesSQL.CONTRASEÑA_USUARIO_INCORRECTO);
                throw new OtraException(ConstantesSQL.CONTRASEÑA_USUARIO_INCORRECTO);
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new OtraException(ConstantesSQL.CONTRASEÑA_USUARIO_INCORRECTO);
        } catch (BaseDatosCaidaException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (ServidorException e) {
            log.error(e.getMessage());
            throw new ServidorException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return result;
    }

    public Usuario getUsuarioById(int idUsuario) {
        Usuario result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            result = jdbcTemplate.queryForObject(ConstantesSQL.SELECT_USUARIO_BY_ID,
                    new BeanPropertyRowMapper<>(Usuario.class), idUsuario);

        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new OtraException(ConstantesSQL.CONTRASEÑA_USUARIO_INCORRECTO);
        } catch (BaseDatosCaidaException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (ServidorException e) {
            log.error(e.getMessage());
            throw new ServidorException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return result;
    }

    public void addUsuario(Usuario usuario) {
        try {
            String passwordHasheada = hashPassword.hashPassword(usuario.getPass());

            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            var sql = jdbcTemplate.update(ConstantesSQL.INSERT_USUARIO,
                    usuario.getNombre(),
                    passwordHasheada,
                    usuario.getIdTipoUsuario());
            if (sql != 1) {
                log.error(ConstantesSQL.ERROR_AL_INSERTAR_USUARIO);
                throw new OtraException(ConstantesSQL.ERROR_AL_INSERTAR_USUARIO);
            }

        } catch (BaseDatosCaidaException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (ServidorException e) {
            log.error(e.getMessage());
            throw new ServidorException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
    }
}
