package notas.ServidorModule.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import notas.CommonModule.modelo.Parte;
import notas.CommonModule.modelo.PartesCompartidos;
import notas.CommonModule.modelo.Usuario;
import notas.CommonModule.modeloDTO.ParteProfesorPadre;
import notas.CommonModule.modeloDTO.UsuarioYRandom;
import notas.ServidorModule.EE.security.encriptaciones.Constantes;
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

import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.sql.PreparedStatement;
import java.util.Base64;
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
            var usuario = daoUsuario.getUsuarioById(parte.getIdProfesor());
            //Ahora para firmar, pillamos la privada del usuario.
            var firmaEnBase64 = encriptar.firmar(usuario, parte.getParte().getDescripcion());
            if (firmaEnBase64.isRight()) {

                var mensajeEncriptado = encriptar.encriptarAESTextoConRandom(parte.getParte().getDescripcion());
                if (mensajeEncriptado.isRight()) {

                    holder = new GeneratedKeyHolder();
                    jtm = new JdbcTemplate(Objects.requireNonNull(transactionManager.getDataSource()));
                    jtm.update(connection -> {
                        PreparedStatement preparedStatement = connection.prepareStatement(ConstantesSQL.INSERT_PARTE,
                                PreparedStatement.RETURN_GENERATED_KEYS);
                        preparedStatement.setString(1, mensajeEncriptado.get());
                        preparedStatement.setInt(2, parte.getParte().getIdAlumno());
                        preparedStatement.setInt(3, parte.getIdProfesor());
                        preparedStatement.setInt(4, ConstantesSQL.ESTADO_CREADO);
                        preparedStatement.setString(5, firmaEnBase64.get());
                        return preparedStatement;
                    }, holder);
                    idParte = Objects.requireNonNull(holder.getKey()).intValue();

                    //Primero compartimos con el profesor
                    addParteCompartido(parte.getIdProfesor(), idParte);

                    //Segundo compartimos con jefatura
                    addParteCompartido(ConstantesSQL.ID_JEFATURA, idParte);

                    result = "Parte creado correctamente";
                } else {
                    result = mensajeEncriptado.getLeft();
                }
            } else {
                result = firmaEnBase64.getLeft();
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
            Usuario jefatura = daoUsuario.getUsuarioById(ConstantesSQL.ID_JEFATURA);
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
                        var mensajeParte =
                                encriptar.desencriptarAESTextoConRandom(parte.getDescripcion(), randomDesencriptada.get());

                        var firmaEnBase64 = encriptar.firmar(jefatura, mensajeParte.get());
                        if (firmaEnBase64.isRight()) {
                            jdbcTemplate.update(ConstantesSQL.UPDATE_PARTE,
                                    estado,
                                    firmaEnBase64.get(),
                                    idParte);
                        } else {
                            log.error(firmaEnBase64.getLeft());
                            throw new OtraException(firmaEnBase64.getLeft());
                        }

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

    public String firmarPartePadre(int idUsuario, int idParte, String mensaje) {
        String result;
        Usuario padre = daoUsuario.getUsuarioById(idUsuario);

        try{
            var firmaEnBase64 = encriptar.firmar(padre, mensaje);
            if (firmaEnBase64.isRight()) {
                JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
                jdbcTemplate.update(ConstantesSQL.UPDATE_PARTE_PADRE,
                        firmaEnBase64.get(),
                        idParte);

                result = ConstantesSQL.FIRMADO;
            } else {
                log.error(firmaEnBase64.getLeft());
                throw new OtraException(firmaEnBase64.getLeft());
            }
        }catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new OtraException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return result;
    }

}
