package videoclub.dao.modelo;

public class Videojuego extends Producto{

    private String fabricante;

    public Videojuego(String titulo, int cantidad, String genero, String fabricante) {
        super(titulo, cantidad, genero);
        this.fabricante = fabricante;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    @Override
    public String toString() {
        return
                "Videojuego: (" + super.toString() +
        ", fabricante='" + fabricante + ")";
    }
}
