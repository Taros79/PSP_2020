package dao.modelo.ModPokemon;

import com.google.gson.annotations.SerializedName;

public class GameIndicesItem {

    @SerializedName("game_index")
    private int gameIndex;

    @SerializedName("version")
    private Version version;

    public int getGameIndex() {
        return gameIndex;
    }

    public Version getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return
                "GameIndicesItem{" +
                        "game_index = '" + gameIndex + '\'' +
                        ",version = '" + version + '\'' +
                        "}";
    }
}