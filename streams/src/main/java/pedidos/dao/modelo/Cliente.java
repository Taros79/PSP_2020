package pedidos.dao.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cliente {
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private List<Cuenta> cuentas;

    public Cliente(String nombre, String direccion, String telefono, String email) {
        this.cuentas = new ArrayList<>();
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(nombre, cliente.nombre) &&
                Objects.equals(direccion, cliente.direccion) &&
                Objects.equals(telefono, cliente.telefono) &&
                Objects.equals(email, cliente.email) &&
                Objects.equals(cuentas, cliente.cuentas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, direccion, telefono, email, cuentas);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", cuentas=" + cuentas +
                '}';
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Cuenta> getCuentas() {
        return (ArrayList)cuentas;
    }

}
