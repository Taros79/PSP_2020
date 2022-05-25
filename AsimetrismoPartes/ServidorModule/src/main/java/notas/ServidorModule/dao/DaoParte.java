package notas.ServidorModule.dao;

import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import notas.CommonModule.modelo.Parte;
import notas.CommonModule.modelo.ParteProfesorPadre;
import notas.ServidorModule.EE.security.encriptaciones.Encriptar;
import notas.ServidorModule.dao.errores.BaseDatosCaidaException;
import notas.ServidorModule.dao.errores.OtraException;
import notas.ServidorModule.dao.jdbc.DBConnectionPool;
import notas.ServidorModule.utils.HashPassword;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.PreparedStatement;
import java.util.List;
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


   /* public List<Parte> getAllPartes() {
        List<Parte> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            result = jdbcTemplate.query(ConstantesSQL.SELECT_ALL_PARTES,
                    new BeanPropertyRowMapper<>(Parte.class));

            for (Parte parte : result) {
                var mensajeDesencriptado = encriptar.(parte.getDescripcion());
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

    public String addParte(ParteProfesorPadre parte) {
        String result;
        int idParte;
        JdbcTemplate jtm;
        KeyHolder holder;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(
                pool.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);

        try {
            var mensajeEncriptado = encriptar.encriptarAESTextoConRandom(parte.getParte().getDescripcion());

            if (mensajeEncriptado.isRight()) {
                holder = new GeneratedKeyHolder();
                jtm = new JdbcTemplate(Objects.requireNonNull(transactionManager.getDataSource()));
                jtm.update(connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(ConstantesSQL.INSERT_PARTE,
                            PreparedStatement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, mensajeEncriptado.get());
                    preparedStatement.setInt(2, parte.getParte().getIdAlumno());
                    preparedStatement.setInt(3, 1);
                    return preparedStatement;
                }, holder);
                idParte = Objects.requireNonNull(holder.getKey()).intValue();

                //Primero compartimos con el profesor
                addParteCompartido(parte.getIdProfesor(), idParte);

                //Segundo compartimos con jefatura
                addParteCompartido(1, idParte);

                result = "Parte creado correctamente";
            } else {
                result = mensajeEncriptado.getLeft();
            }

            transactionManager.commit(txStatus);

        } catch (DataAccessException e) {
            transactionManager.rollback(txStatus);
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            log.error(e.getMessage());
            throw new OtraException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return result;
    }


    public String addParteCompartido(int idUsuario, int idParte) {
        String result;

        try {
            var usuario = daoUsuario.getUsuarioById(idUsuario);

            if (usuario != null) {
                var claveCifrada = encriptar.encriptarRSARandomConPublica(usuario.getNombre(),encriptar.getClaveSimetrica());

                if (claveCifrada.isRight()) {
                    JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
                    jdbcTemplate.update(con -> {
                        PreparedStatement preparedStatement = con.prepareStatement(ConstantesSQL.INSERT_PARTE_COMPARTIDO);
                        preparedStatement.setInt(1, idUsuario);
                        preparedStatement.setInt(2, idParte);
                        preparedStatement.setString(3, claveCifrada.get());
                        return preparedStatement;
                    });
                    result = "Parte compartido con exito";
                } else {
                    throw new OtraException(ConstantesSQL.ERROR_DEL_SERVIDOR);
                }

            } else {
                result = "El usuario no existe";
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
