package pedidos.dao.modelo;

import java.util.Objects;

public class Cuenta {
    private String numeroTarjeta;
    private int saldo;

    public Cuenta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
        this.saldo = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuenta cuenta = (Cuenta) o;
        return saldo == cuenta.saldo &&
                Objects.equals(numeroTarjeta, cuenta.numeroTarjeta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroTarjeta, saldo);
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "numeroTarjeta='" + numeroTarjeta + '\'' +
                ", saldo=" + saldo +
                '}';
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
}
