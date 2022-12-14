package dao;

import dao.jdbc.DBConnectionPool;
import dao.modelo.Secreto;
import dao.modelo.SecretoCompartido;
import dao.modelo.Usuario;
import gui.utils.HashPassword;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Log4j2
public class DaoUsuarios {

    private static final String INSERT_USUARIO =
            "insert into usuarios (nombre, password) values (?, ?)";

    private static final String INSERT_SECRETO =
            "insert into secretos (nombre, secretoEncriptado) values (?, ?)";

    private static final String INSERT_SECRETO_COMPARTIDO =
            "insert into secretosCompartidos (userACompartir, claveCifrada, idSecreto) values (?, ?, ?)";

    private static final String SELECT_USUARIOS = "select * from usuarios;";

    private static final String SELECT_SECRETOS = "select * from secretos;";

    private static final String GET_SECRETOSCOMPARTIDOS = "select * from secretosCompartidos;";

    private static final String GET_SECRETOS_POR_ID = "select * from secretos where id = ?;";

    private final Encriptaciones encriptaciones;
    private final KeyStoreBuild keyStoreBuild;
    private final DBConnectionPool pool;
    private HashPassword hashP;

    @Inject
    public DaoUsuarios(Encriptaciones encriptaciones, KeyStoreBuild keyStoreBuild, DBConnectionPool pool, HashPassword hashP) {
        this.encriptaciones = encriptaciones;
        this.keyStoreBuild = keyStoreBuild;
        this.pool = pool;
        this.hashP = hashP;
    }

    public Either<String, List<Usuario>> getUsuarios() {

        Either<String, List<Usuario>> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            List<Usuario> purchaseList = jdbcTemplate.query(SELECT_USUARIOS,
                    new BeanPropertyRowMapper<>(Usuario.class));
            result = Either.right(purchaseList);
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            result = Either.left(ConstantesDAO.ERROR_BBDD);
        }
        return result;
    }

    public Either<String, List<Secreto>> getSecretos() {

        Either<String, List<Secreto>> result;

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            List<Secreto> purchaseList = jdbcTemplate.query(SELECT_SECRETOS,
                    new BeanPropertyRowMapper<>(Secreto.class));
            result = Either.right(purchaseList);
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
            result = Either.left(ConstantesDAO.ERROR_BBDD);

        }
        return result;
    }

    public Either<String, Usuario> addUsuarioYSecreto(Secreto s, Usuario usuario) {
        Either<String, Secreto> resultSecreto;
        Either<String, Usuario> resultUsuario = null;
        String claveCifrada = null;
        try {
            var mensajeEncriptado = encriptaciones.encriptarAESTextoConRandom(s);
            if (mensajeEncriptado.isRight()) {
                String secretito = mensajeEncriptado.get();
                KeyHolder keyHolder = new GeneratedKeyHolder();
                JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
                jdbcTemplate.update(connection -> {
                    PreparedStatement preparedStatement =
                            connection.prepareStatement(INSERT_SECRETO, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, s.getNombre());
                    preparedStatement.setString(2, secretito);
                    return preparedStatement;
                }, keyHolder);

                s.setId(keyHolder.getKey().intValue());
                int idSecreto = s.getId();

                resultSecreto = Either.right(s);

                if (resultSecreto.isRight()) {
                    String passwordHasheada = hashP.hashPassword(usuario.getPassword());
                    try {
                        JdbcTemplate jdbcTemplate2 = new JdbcTemplate(pool.getDataSource());
                        jdbcTemplate2.update(con -> {
                            PreparedStatement preparedStatement = con.prepareStatement(INSERT_USUARIO);
                            preparedStatement.setString(1, usuario.getNombre());
                            preparedStatement.setString(2, passwordHasheada);
                            return preparedStatement;
                        });

                        resultUsuario = Either.right(usuario);
                        keyStoreBuild.crearKeystoreYCertificado(usuario);


                        var encriptarConPublica = encriptaciones.encriptarRSARandomConPublica(usuario);

                        if (encriptarConPublica.isRight()) {
                            claveCifrada = encriptarConPublica.get();
                        }

                    } catch (DataIntegrityViolationException ex) {
                        log.error(ex.getMessage());
                        resultUsuario = Either.left(ConstantesDAO.YA_EXISTE);
                    } catch (DataAccessException ex) {
                        log.error(ex.getMessage());
                        resultUsuario = Either.left(ConstantesDAO.ERROR_BBDD);
                    }
                } else {
                    resultUsuario = Either.left(ConstantesDAO.ERROR_EN_CREAR_USUARIO);
                }

                if (resultUsuario.isRight()) {
                    try {
                        JdbcTemplate jdbcTemplate3 = new JdbcTemplate(pool.getDataSource());
                        String finalClaveCifrada = claveCifrada;
                        jdbcTemplate3.update(con -> {
                            PreparedStatement preparedStatement = con.prepareStatement(INSERT_SECRETO_COMPARTIDO);
                            preparedStatement.setString(1, usuario.getNombre());
                            preparedStatement.setString(2, finalClaveCifrada);
                            preparedStatement.setInt(3, idSecreto);
                            return preparedStatement;
                        });

                    } catch (DataIntegrityViolationException ex) {
                        log.error(ex.getMessage());
                        resultUsuario = Either.left(ConstantesDAO.YA_EXISTE);
                    } catch (DataAccessException ex) {
                        log.error(ex.getMessage());
                        resultUsuario = Either.left(ConstantesDAO.ERROR_BBDD);
                    }
                } else {
                    resultUsuario = Either.left(ConstantesDAO.ERROR_AL_COMPARTIR_SECRETO);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            resultUsuario = Either.left(ConstantesDAO.ERROR_BBDD);
        }
        return resultUsuario;
    }

    public Either<String, String> compartirSecreto(SecretoCompartido s) {
        Either<String, String> result;
        try {
            JdbcTemplate jdbcTemplate3 = new JdbcTemplate(pool.getDataSource());
            jdbcTemplate3.update(con -> {
                PreparedStatement preparedStatement = con.prepareStatement(INSERT_SECRETO_COMPARTIDO);
                preparedStatement.setString(1, s.getUserACompartir());
                preparedStatement.setString(2, s.getClaveCifrada());
                preparedStatement.setInt(3, s.getIdSecreto());
                return preparedStatement;
            });

            result = Either.right(ConstantesDAO.SECRETO_COMPARTIDO);
        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage());
            result = Either.left(ConstantesDAO.YA_EXISTE);
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
            result = Either.left(ConstantesDAO.ERROR_BBDD);
        }
        return result;
    }

    public Either<String, List<SecretoCompartido>> getSecretosCompartidos() {

        Either<String, List<SecretoCompartido>> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            List<SecretoCompartido> purchaseList = jdbcTemplate.query(GET_SECRETOSCOMPARTIDOS,
                    new BeanPropertyRowMapper<>(SecretoCompartido.class));
            result = Either.right(purchaseList);
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
            result = Either.left(ConstantesDAO.ERROR_BBDD);

        }
        return result;
    }

    public Either<String, Secreto> getSecretoPorID(int id) {
        Secreto c = null;
        Either<String, Secreto> resultado = null;

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());

            c = jdbcTemplate.queryForObject(GET_SECRETOS_POR_ID, new Object[]{id}, (rs, rowNum) ->
                    new Secreto(
                            rs.getInt(ConstantesDAO.ID),
                            rs.getString(ConstantesDAO.NOMBRE),
                            rs.getString(ConstantesDAO.SECRETO_ENCRIPTADO)));
            resultado = Either.right(c);

        } catch (IncorrectResultSizeDataAccessException e) {
            log.error(e.getMessage());
            resultado = Either.left(ConstantesDAO.EL_SECRETO_NO_EXISTE);
        } catch (Exception e) {
            log.error(e.getMessage());
            resultado = Either.left(ConstantesDAO.ERROR_BBDD);
        }
        return resultado;
    }
}
