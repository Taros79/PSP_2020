package dao.modelo.ModPokemon;

import com.google.gson.annotations.SerializedName;

public class GenerationV {

    @SerializedName("black-white")
    private BlackWhite blackWhite;

    public BlackWhite getBlackWhite() {
        return blackWhite;
    }

    @Override
    public String toString() {
        return
                "GenerationV{" +
                        "black-white = '" + blackWhite + '\'' +
                        "}";
    }
}