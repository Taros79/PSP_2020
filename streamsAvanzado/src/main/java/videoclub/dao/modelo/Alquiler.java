package videoclub.dao.modelo;

import java.time.LocalDateTime;
import java.util.Objects;

public class Alquiler {
    private LocalDateTime fechaAlquiler;
    private Socio socio;
    private Producto productoAlquilado;

    public Alquiler(LocalDateTime fechaAlquiler, Socio socio, Producto productoAlquilado) {
        this.fechaAlquiler = fechaAlquiler;
        this.socio = socio;
        this.productoAlquilado = productoAlquilado;
    }



    public void setFechaAlquiler(LocalDateTime fechaAlquiler) {
        this.fechaAlquiler = fechaAlquiler;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public void setProductoAlquilado(Producto productoAlquilado) {
        this.productoAlquilado = productoAlquilado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Alquiler alquiler = (Alquiler) o;
        return getFechaAlquiler().equals(alquiler.getFechaAlquiler()) &&
            getSocio().equals(alquiler.getSocio()) &&
            getProductoAlquilado().equals(alquiler.getProductoAlquilado());
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Alquiler;
    }


    @Override
    public int hashCode() {
        return Objects.hash(getFechaAlquiler(), getSocio(), getProductoAlquilado());
    }

    public String toString() {
        return "Alquiler(fechaAlquiler=" + this.getFechaAlquiler() + ", socio=" + this.getSocio() + ", productoAlquilado=" + this.getProductoAlquilado() + ")";
    }

    public LocalDateTime getFechaAlquiler() {
        return this.fechaAlquiler;
    }

    public Socio getSocio() {
        return this.socio;
    }

    public Producto getProductoAlquilado() {
        return this.productoAlquilado;
    }
}
