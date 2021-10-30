package dao.modeloPropio;

import com.google.gson.annotations.SerializedName;
import dao.modelo.ModPokemon.Move;

public class MovesItemPrp {

    @SerializedName("move")
    private Move move;

    public Move getMove() {
        return move;
    }

}