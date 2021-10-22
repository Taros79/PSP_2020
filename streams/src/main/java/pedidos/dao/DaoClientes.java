package pedidos.dao;

import pedidos.dao.modelo.Cliente;
import pedidos.dao.modelo.Cuenta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaoClientes {
    //los clientes se van a registrar por email, no pudiendose repetir los mismos

    private static Map<String, Cliente> clientes = new HashMap<>();

    public List<Cliente> getTodosClientes() {
        return new ArrayList<>(clientes.values());
    }

    public boolean addCliente(Cliente cliente) {
        boolean socioRegistrado = false;
        if (clientes.put(cliente.getEmail(), cliente) == null) {
            socioRegistrado = true;
        }
        return socioRegistrado;
    }

    public Cliente deleteCliente(String email){
        return clientes.remove(email);
    }

    public Cliente getClientePorEmail(String email){
        return clientes.get(email);
    }

    public boolean addCuenta(Cuenta cuenta, Cliente cliente){
        return cliente.getCuentas().add(cuenta);
    }

    public List<Cuenta>cuentasCliente(Cliente cliente){
        return cliente.getCuentas();
    }
}
