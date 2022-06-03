package notas.ServidorModule.dao;

import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import notas.CommonModule.modelo.Parte;
import notas.CommonModule.modelo.PartesCompartidos;
import notas.CommonModule.modelo.Usuario;
import notas.CommonModule.modeloDTO.ParteDesencriptadoDTO;
import notas.CommonModule.modeloDTO.ParteProfesorPadre;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Log4j2
public class DaoParte {

    private final DBConnectionPool pool;
    private final HashPassword hashPassword;
    private final Encriptar encriptar;
    private final DaoUsuario daoUsuario;
    private final DaoAlumno daoAlumno;

    @Inject
    public DaoParte(DBConnectionPool pool, HashPassword hashPassword,
                    Encriptar encriptar, DaoUsuario daoUsuario, DaoAlumno daoAlumno) {
        this.pool = pool;
        this.hashPassword = hashPassword;
        this.encriptar = encriptar;
        this.daoUsuario = daoUsuario;
        this.daoAlumno = daoAlumno;
    }

    public Parte getParteById(int id) {
        Parte result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            result = jdbcTemplate.queryForObject(ConstantesSQL.SELECT_PARTE_BY_ID,
                    new BeanPropertyRowMapper<>(Parte.class), id);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new OtraException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return result;
    }

    public List<ParteDesencriptadoDTO> getPartesByUser(int idUsuario) {
        List<PartesCompartidos> pc;
        List<ParteDesencriptadoDTO> partesDesencriptados = new ArrayList<>();

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            pc = jdbcTemplate.query(ConstantesSQL.SELECT_ALL_PARTESCOMPARTIDOS_BY_USER,
                    new BeanPropertyRowMapper<>(PartesCompartidos.class), idUsuario);

            Usuario usuario = daoUsuario.getUsuarioById(idUsuario);

            //Recorremos todos los partes compartidos
            for (PartesCompartidos partesCompartidos : pc) {
                //Con la idParte de partes compartidos obtenemos el parte
                var parte = getParteById(partesCompartidos.getIdParte());

                //Si el usuario es padre solo vera los aceptados, el profesor los suyos y jefatura todos
                if (usuario.getIdTipoUsuario() == 2 || parte.getIdTipoEstado() == 2 && usuario.getIdTipoUsuario() != 2
                        || parte.getIdTipoEstado() == 3 && usuario.getIdTipoUsuario() == 1) {
                    var randomDesencriptada =
                            encriptar.desencriptarRSAClaveCifrada(partesCompartidos.getClaveCifrada(), usuario);
                    if (randomDesencriptada.isRight()) {
                        var mensajeParte = encriptar.desencriptarAESTextoConRandom(parte.getDescripcion(), randomDesencriptada.get());
                        if (mensajeParte.isRight()) {
                            var alumno = daoAlumno.getAlumnoById(parte.getIdAlumno());
                            if (Objects.nonNull(alumno)) {
                                partesDesencriptados.add(new ParteDesencriptadoDTO(parte.getId(), mensajeParte.get(),
                                        alumno.getNombre(), parte.getIdTipoEstado()));
                            } else {
                                log.error("No se ha encontrado el alumno con id: " + parte.getIdAlumno());
                                throw new OtraException(ConstantesSQL.ALUMNO_NO_ENCONTRADO);
                            }
                        } else {
                            log.error("Error al desencriptar el mensaje de la parte con id: " + partesCompartidos.getIdParte());
                            throw new OtraException(ConstantesSQL.ERROR_AL_DESENCRIPTAR_MENSAJE);
                        }

                    } else {
                        log.error(randomDesencriptada.getLeft());
                        throw new OtraException(ConstantesSQL.ERROR_AL_DESENCRIPTAR_CLAVECIFRADA);
                    }
                }
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new OtraException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return partesDesencriptados;
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

    public String deleteParteYParteCompartido(int idParte) {
        String result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            jdbcTemplate.update(ConstantesSQL.DELETE_PARTE_Y_PARTE_COMPARTIDO, idParte);
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
        String result;
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
                            new BeanPropertyRowMapper<>(PartesCompartidos.class), parte.getIdAlumno());
                    if (usuario != null) {
                        jdbcTemplate.update(ConstantesSQL.UPDATE_PARTE,
                                estado,
                                idParte);
                        addParteCompartidoPadres(usuario.getId(), idParte, randomDesencriptada.get());
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

            result = ConstantesSQL.ACTUALIZADO_CON_EXITO;
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

    public String addParteCompartidoPadres(int idUsuario, int idParte, String random) {
        String result;

        try {
            var usuario = daoUsuario.getUsuarioById(idUsuario);

            if (usuario != null) {
                var claveCifrada = encriptar.encriptarRSA_Padres(usuario.getNombre(), random);

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


}
