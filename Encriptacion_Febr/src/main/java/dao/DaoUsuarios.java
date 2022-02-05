package dao;

import dao.jdbc.DBConnectionPool;
import dao.modelo.Usuario;
import gui.utils.HashPassword;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoUsuarios {

    private final DBConnectionPool pool;

    private HashPassword hashP = new HashPassword();
    private Encriptaciones encriptaciones = new Encriptaciones();

    private static final String INSERT_USUARIO =
            "insert into usuarios (nombre, mensaje) values (?, ?)";

    private static final String SELECT_USUARIOS = "select * from usuarios;";

    private static final String GET_USUARIO =
            "select * from usuarios where nombre = ?";

    private static final String GET_SECRETOS_POR_PASS =
            "select * from usuarios where password = ?";


    @Inject
    public DaoUsuarios(DBConnectionPool pool) {
        this.pool = pool;
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
            result = Either.left("Error en la bbdd");
        }
        return result;
    }

    public Either<String, Usuario> addUsuario(Usuario usuario, String pass) {
        Either<String, Usuario> result = null;
        String passwordHasheada = hashP.hashPassword(pass);
        var mensajeEncriptado = encriptaciones.encriptarTexto(usuario, passwordHasheada);
        if (mensajeEncriptado.isRight()) {
            String secreto = mensajeEncriptado.get();
            try {
                JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
                jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(INSERT_USUARIO);
                    preparedStatement.setString(1, usuario.getNombre());
                    preparedStatement.setString(2, secreto);
                    return preparedStatement;
                });

                result = Either.right(usuario);
            } catch (DataIntegrityViolationException e) {
                log.error(e.getMessage(), e);
                result = Either.left("Ya existe");
            } catch (DataAccessException e) {
                log.error(e.getMessage(), e);
                result = Either.left("Error en la bbdd");
            }
        }
        return result;
    }


    public Either<String, String> getSecretos(String nombre, String pass) {
        Usuario usuario;
        String passwordHasheada = hashP.hashPassword(pass);
        Either<String, String> result;

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            usuario = jdbcTemplate.queryForObject(GET_USUARIO, new Object[]{nombre}, (rs, rowNum) ->
                    new Usuario(
                            rs.getString("nombre"),
                            rs.getString("mensaje")));

            var mensajeDesencriptado = encriptaciones.desencriptarTexto(usuario,passwordHasheada);

            if (mensajeDesencriptado.isRight()) {
                result = Either.right(mensajeDesencriptado.get());
            } else {
                result = Either.left(mensajeDesencriptado.getLeft());
            }
        } catch (IncorrectResultSizeDataAccessException e) {
            log.error(e.getMessage(), e);
            result = Either.left("Datos incorrectos");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = Either.left("Error en la bbdd");
        }
        return result;
    }

   public Either<String, List<String>> getSecretosPorPass(String password) {
        List<Usuario> c;
        String passwordHasheada = hashP.hashPassword(password);
        Either<String, List<String>> result = null;
        List<String> listaMensajes = new ArrayList<>();

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());

            c = jdbcTemplate.query(SELECT_USUARIOS, BeanPropertyRowMapper.newInstance(Usuario.class));

            for (int i = 0; i < c.size(); i++) {
                var mensajeDesencriptado = encriptaciones.desencriptarTexto(c.get(i),passwordHasheada);

                if (mensajeDesencriptado.isRight()) {
                    listaMensajes.add(mensajeDesencriptado.get());
                    result = Either.right(listaMensajes);
                } else {
                    return Either.left(mensajeDesencriptado.getLeft());
                }
            }
        } catch (IncorrectResultSizeDataAccessException e) {
            log.error(e.getMessage(), e);
            result = Either.left("Datos incorrectos");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = Either.left("Error en la bbdd");
        }
        return result;
    }
}
