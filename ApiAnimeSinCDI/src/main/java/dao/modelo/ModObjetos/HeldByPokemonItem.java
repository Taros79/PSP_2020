package dao.modelo.ModObjetos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HeldByPokemonItem {

    @SerializedName("pokemon")
    private Pokemon pokemon;

    @SerializedName("version_details")
    private List<VersionDetailsItem> versionDetails;

    public Pokemon getPokemon() {
        return pokemon;
    }

    public List<VersionDetailsItem> getVersionDetails() {
        return versionDetails;
    }

    @Override
    public String toString() {
        return
                "HeldByPokemonItem{" +
                        "pokemon = '" + pokemon + '\'' +
                        ",version_details = '" + versionDetails + '\'' +
                        "}";
    }
}