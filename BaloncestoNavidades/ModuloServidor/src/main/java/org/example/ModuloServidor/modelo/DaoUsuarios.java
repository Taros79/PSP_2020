package org.example.ModuloServidor.modelo;
import org.example.Common.modelo.Usuario;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DaoUsuarios {

    private JdbcTemplate jtm = new JdbcTemplate(
            DBConnection.getInstance().getDataSource());

    private static final String INSERT_CUSTOMER =
            "insert into Usuarios (correo, pass, codActivacion, idUsuario, fechaAlta, idTipoUsuario, confirmacion) values (?, ?, ?, ?, ?, ?, ?)";


    /*public String addCustomer(Usuario c) {
        String añadido;

        int resultado = jtm.update(INSERT_CUSTOMER, c.getCorreo(), c.getPass(), c.getCodActivacion(), c.getIdUsuario(), c.getFechaAlta(), c.getIdTipoUsuario(), c.getConfirmacion());

        if (resultado == 1) {
            añadido = "Customer creado";
        } else {
            añadido = "Fallo en la bbdd";
        }

        return añadido;
    }*/

    public List<Usuario> getCustomers() {
        return jtm.query("select * from usuarios",
                BeanPropertyRowMapper.newInstance(Usuario.class));
    }

}
