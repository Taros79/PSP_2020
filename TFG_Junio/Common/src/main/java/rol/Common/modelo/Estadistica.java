package rol.Common.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estadistica {

    private int id;
    private int vida;
    private int ac;
    private int fortaleza;
    private int reflejos;
    private int voluntad;
    private int fuerza;
    private int destreza;
    private int constitucion;
    private int inteligencia;
    private int sabiduria;
    private int carisma;

    public Estadistica(int vida, int ac, int fortaleza, int reflejos, int voluntad, int fuerza, int destreza, int constitucion, int inteligencia, int sabiduria, int carisma) {
        this.vida = vida;
        this.ac = ac;
        this.fortaleza = fortaleza;
        this.reflejos = reflejos;
        this.voluntad = voluntad;
        this.fuerza = fuerza;
        this.destreza = destreza;
        this.constitucion = constitucion;
        this.inteligencia = inteligencia;
        this.sabiduria = sabiduria;
        this.carisma = carisma;
    }

    @Override
    public String toString() {
        return "Vida: " + vida +
                "\nAc: " + ac +
                "\nFortaleza: " + fortaleza +
                "\nReflejos: " + reflejos +
                "\nVoluntad: " + voluntad +
                "\nFuerza: " + fuerza +
                "\nDestreza: " + destreza +
                "\nConstitucion: " + constitucion +
                "\nInteligencia: " + inteligencia +
                "\nSabiduria: " + sabiduria +
                "\nCarisma: " + carisma;
    }
}
