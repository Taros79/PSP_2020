package dao.modelo.ModPokemon;

import com.google.gson.annotations.SerializedName;

public class VersionGroupDetailsItem {

    @SerializedName("level_learned_at")
    private int levelLearnedAt;

    @SerializedName("version_group")
    private VersionGroup versionGroup;

    @SerializedName("move_learn_method")
    private MoveLearnMethod moveLearnMethod;

    public int getLevelLearnedAt() {
        return levelLearnedAt;
    }

    public VersionGroup getVersionGroup() {
        return versionGroup;
    }

    public MoveLearnMethod getMoveLearnMethod() {
        return moveLearnMethod;
    }

    @Override
    public String toString() {
        return
                "VersionGroupDetailsItem{" +
                        "level_learned_at = '" + levelLearnedAt + '\'' +
                        ",version_group = '" + versionGroup + '\'' +
                        ",move_learn_method = '" + moveLearnMethod + '\'' +
                        "}";
    }
}