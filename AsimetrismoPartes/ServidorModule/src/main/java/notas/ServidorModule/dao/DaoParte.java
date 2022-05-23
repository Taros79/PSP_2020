package notas.ServidorModule.dao;

import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import notas.CommonModule.modelo.Parte;
import notas.ServidorModule.EE.security.encriptaciones.Encriptar;
import notas.ServidorModule.dao.errores.BaseDatosCaidaException;
import notas.ServidorModule.dao.errores.OtraException;
import notas.ServidorModule.dao.jdbc.DBConnectionPool;
import notas.ServidorModule.utils.HashPassword;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.Objects;


@Log4j2
public class DaoParte {

    private final DBConnectionPool pool;
    private final HashPassword hashPassword;
    private final Encriptar encriptar;
    private final DaoUsuario daoUsuario;

    @Inject
    public DaoParte(DBConnectionPool pool, HashPassword hashPassword,
                    Encriptar encriptar, DaoUsuario daoUsuario) {
        this.pool = pool;
        this.hashPassword = hashPassword;
        this.encriptar = encriptar;
        this.daoUsuario = daoUsuario;
    }


    /*public List<Parte> getAllPartes() {
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
    }*/

/*    public List<Parte> getPartesByUser(int idPadre) {
        List<Parte> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            result = jdbcTemplate.query(ConstantesSQL.SELECT_PARTES_PADRE_ALUMNOS,
                    new BeanPropertyRowMapper<>(Parte.class), idPadre);

            for (Parte parte : result) {
                var mensajeDesencriptado = encriptar.desencriptarTexto(parte.getDescripcion());
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
    }*/

    public Integer addParte(Parte parte) {
        int result;
        JdbcTemplate jtm;
        try {
            var mensajeEncriptado = encriptar.encriptarAESTextoConRandom(parte.getDescripcion());

            if (mensajeEncriptado.isRight()) {
                KeyHolder holder = new GeneratedKeyHolder();
                jtm = new JdbcTemplate(pool.getDataSource());
                jtm.update(connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(ConstantesSQL.INSERT_PARTE,
                            PreparedStatement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, mensajeEncriptado.get());
                    preparedStatement.setInt(2, parte.getIdAlumno());
                    preparedStatement.setInt(3, 1);
                    return preparedStatement;
                }, holder);
                result = Objects.requireNonNull(holder.getKey()).intValue();
            } else {
                result = 0;
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


    public String addParteCompartido(String username, int idParte) {
        String result = null;

        try {
            var usuario = daoUsuario.getUsuarioByName(username);

            if (usuario != null) {
                var claveCifrada = encriptar.encriptarRSARandomConPublica(username);

                if (claveCifrada.isRight()) {
                    JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
                    jdbcTemplate.update(con -> {
                        PreparedStatement preparedStatement = con.prepareStatement(ConstantesSQL.INSERT_PARTE_COMPARTIDO);
                        preparedStatement.setInt(1, usuario.getId());
                        preparedStatement.setString(2, claveCifrada.get());
                        preparedStatement.setInt(3, idParte);
                        return preparedStatement;
                    });
                    result = "Parte compartido con exito";
                } else {
                    throw new OtraException(ConstantesSQL.ERROR_DEL_SERVIDOR);
                }

            } else {
                result = "No existe el usuario";
            }

        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage());
            throw new OtraException(ConstantesSQL.YA_EXISTE);
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
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
