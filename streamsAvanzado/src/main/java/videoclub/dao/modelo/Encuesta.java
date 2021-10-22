package videoclub.dao.modelo;

public class Encuesta {
    private int nota;
    private boolean realquilar;

    public Encuesta(int nota, boolean realquilar) {
        this.nota = nota;
        this.realquilar = realquilar;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public boolean isRealquilar() {
        return realquilar;
    }

    public void setRealquilar(boolean realquilar) {
        this.realquilar = realquilar;
    }
}
