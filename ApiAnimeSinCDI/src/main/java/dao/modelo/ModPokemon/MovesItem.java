package dao.modelo.ModPokemon;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovesItem {

    @SerializedName("version_group_details")
    private List<VersionGroupDetailsItem> versionGroupDetails;

    @SerializedName("move")
    private Move move;

    public List<VersionGroupDetailsItem> getVersionGroupDetails() {
        return versionGroupDetails;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public String toString() {
        return
                "MovesItem{" +
                        "version_group_details = '" + versionGroupDetails + '\'' +
                        ",move = '" + move + '\'' +
                        "}";
    }
}