package dao.modeloPropio;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokemonPrp {

    @SerializedName("sprites")
    private SpritesPrp sprites;

    @SerializedName("moves")
    private List<MovesItemPrp> movimientos;

    @SerializedName("name")
    private String nombre;

    @SerializedName("id")
    private int id;

    public SpritesPrp getSprites() {
        return sprites;
    }

    public List<MovesItemPrp> getMovimientos() {
        return movimientos;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "PokemonPrp{" +
                "sprites = " + sprites +
                ", movimientos = " + movimientos +
                ", nombre = '" + nombre + '\'' +
                ", id = " + id +
                '}';
    }
}