package videoclub.config;

public class Configuration {

  private static double precioAlquiler = 3;
  private static int diasAlquilerVideojuego = 50;
  private static int diasAlquilerPeliculas = 50;




  public static double getPrecioAlquiler() {
    return precioAlquiler;
  }

  public static void setPrecioAlquiler(double precioAlquiler) {
    // si soy el gerente lo puedo cambiar
    Configuration.precioAlquiler = precioAlquiler;
  }

  public static int getDiasAlquilerVideojuego() {
    return diasAlquilerVideojuego;
  }

  public static void setDiasAlquilerVideojuego(int diasAlquilerVideojuego) {
    Configuration.diasAlquilerVideojuego = diasAlquilerVideojuego;
  }

  public static int getDiasAlquilerPeliculas() {
    return diasAlquilerPeliculas;
  }

  public static void setDiasAlquilerPeliculas(int diasAlquilerPeliculas) {
    Configuration.diasAlquilerPeliculas = diasAlquilerPeliculas;
  }
}
