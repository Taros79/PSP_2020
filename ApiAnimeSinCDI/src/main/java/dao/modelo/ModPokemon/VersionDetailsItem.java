package dao.modelo.ModPokemon;

import com.google.gson.annotations.SerializedName;

public class VersionDetailsItem {

    @SerializedName("version")
    private Version version;

    @SerializedName("rarity")
    private int rarity;

    public Version getVersion() {
        return version;
    }

    public int getRarity() {
        return rarity;
    }

    @Override
    public String toString() {
        return
                "VersionDetailsItem{" +
                        "version = '" + version + '\'' +
                        ",rarity = '" + rarity + '\'' +
                        "}";
    }
}