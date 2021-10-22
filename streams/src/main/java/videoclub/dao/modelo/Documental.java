package videoclub.dao.modelo;

public class Documental extends Producto{

  private FormatoPelicula formato;
  private String director;
  private String duracion;

  public Documental(String titulo, int cantidad, String genero, FormatoPelicula formato, String director, String duracion) {
    super(titulo, cantidad, genero);
    this.formato = formato;
    this.director = director;
    this.duracion = duracion;
  }

  public FormatoPelicula getFormato() {
    return formato;
  }

  public void setFormato(FormatoPelicula formato) {
   this.formato = formato;
  }

  @Override
  public String toString() {
    return "Documental: (" + super.toString() +
            ", formato=" + formato +
            ", director='" + director +
            ", duracion='" + duracion + ")";
  }
}
