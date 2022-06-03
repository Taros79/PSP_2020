package notas.ServidorModule.dao;

import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import notas.CommonModule.modelo.Parte;
import notas.CommonModule.modelo.PartesCompartidos;
import notas.CommonModule.modelo.Usuario;
import notas.CommonModule.modeloDTO.ParteDesencriptadoDTO;
import notas.CommonModule.modeloDTO.ParteProfesorPadre;
import notas.CommonModule.modeloDTO.UsuarioYRandom;
import notas.ServidorModule.EE.security.encriptaciones.Encriptar;
import notas.ServidorModule.dao.errores.BaseDatosCaidaException;
import notas.ServidorModule.dao.errores.OtraException;
import notas.ServidorModule.dao.errores.ServidorException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Log4j2
public class DaoParte {

    private final DBConnectionPool pool;
    private final Encriptar encriptar;
    private final DaoUsuario daoUsuario;

    @Inject
    public DaoParte(DBConnectionPool pool, HashPassword hashPassword,
                    Encriptar encriptar, DaoUsuario daoUsuario) {
        this.pool = pool;
        this.encriptar = encriptar;
        this.daoUsuario = daoUsuario;
    }

    public Parte getParteById(int id) {
        Parte result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            result = jdbcTemplate.queryForObject(ConstantesSQL.SELECT_PARTE_BY_ID,
                    new BeanPropertyRowMapper<>(Parte.class), id);

        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new OtraException(ConstantesSQL.NO_HAY_DATOS);
        } catch (BaseDatosCaidaException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (ServidorException e) {
            log.error(e.getMessage());
            throw new ServidorException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return result;
    }

    public List<PartesCompartidos> getPartesByUser(int idUsuario) {
        List<PartesCompartidos> pc;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            pc = jdbcTemplate.query(ConstantesSQL.SELECT_ALL_PARTESCOMPARTIDOS_BY_USER,
                    new BeanPropertyRowMapper<>(PartesCompartidos.class), idUsuario);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new OtraException(ConstantesSQL.NO_HAY_DATOS);
        } catch (BaseDatosCaidaException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (ServidorException e) {
            log.error(e.getMessage());
            throw new ServidorException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return pc;
    }

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
                var claveCifrada = encriptar.encriptarRSARandomConPublica(usuario.getNombre());

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

    public UsuarioYRandom updateParte(int idParte, int estado) {
        UsuarioYRandom result;
        JdbcTemplate jdbcTemplate;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(
                pool.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);

        try {
            Usuario jefatura = daoUsuario.getUsuarioById(1);
            jdbcTemplate = new JdbcTemplate(Objects.requireNonNull(transactionManager.getDataSource()));
            var parteComp = jdbcTemplate.queryForObject(ConstantesSQL.SELECT_PARTECOMPARTIDO_BY_IDS,
                    new BeanPropertyRowMapper<>(PartesCompartidos.class), idParte);

            if (parteComp != null) {
                var randomDesencriptada =
                        encriptar.desencriptarRSAClaveCifrada(parteComp.getClaveCifrada(), jefatura);
                if (randomDesencriptada.isRight()) {
                    var parte = getParteById(idParte);
                    var usuario = jdbcTemplate.queryForObject(ConstantesSQL.SELECT_USUARIO_BY_ID_ALUMNO,
                            new BeanPropertyRowMapper<>(Usuario.class), parte.getIdAlumno());
                    if (usuario != null) {
                        jdbcTemplate.update(ConstantesSQL.UPDATE_PARTE,
                                estado,
                                idParte);

                        result = new UsuarioYRandom(usuario, randomDesencriptada.get());
                    } else {
                        log.error("El usuario no existe");
                        throw new OtraException("El usuario no existe");
                    }
                } else {
                    log.error(randomDesencriptada.getLeft());
                    throw new OtraException(randomDesencriptada.getLeft());
                }
            } else {
                log.error("No se encontro el parte compartido");
                throw new OtraException("No se encontro el parte compartido");
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

    public String addParteCompartidoPadres(int idUsuario, int idParte, String claveCifrada) {
        String result;

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            jdbcTemplate.update(con -> {
                PreparedStatement preparedStatement = con.prepareStatement(ConstantesSQL.INSERT_PARTE_COMPARTIDO);
                preparedStatement.setInt(1, idUsuario);
                preparedStatement.setInt(2, idParte);
                preparedStatement.setString(3, claveCifrada);
                return preparedStatement;
            });
            result = ConstantesSQL.ANADIDO_CON_EXITO;
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


}
