package dao.modelo.ModObjetos;

import com.google.gson.annotations.SerializedName;

public class GameIndicesItem {

    @SerializedName("generation")
    private Generation generation;

    @SerializedName("game_index")
    private int gameIndex;

    public Generation getGeneration() {
        return generation;
    }

    public int getGameIndex() {
        return gameIndex;
    }

    @Override
    public String toString() {
        return
                "GameIndicesItem{" +
                        "generation = '" + generation + '\'' +
                        ",game_index = '" + gameIndex + '\'' +
                        "}";
    }
}