package dao.modelo.ModPokemon;

import com.google.gson.annotations.SerializedName;

public class StatsItem {

    @SerializedName("stat")
    private Stat stat;

    @SerializedName("base_stat")
    private int baseStat;

    @SerializedName("effort")
    private int effort;

    public Stat getStat() {
        return stat;
    }

    public int getBaseStat() {
        return baseStat;
    }

    public int getEffort() {
        return effort;
    }

    @Override
    public String toString() {
        return
                "StatsItem{" +
                        "stat = '" + stat + '\'' +
                        ",base_stat = '" + baseStat + '\'' +
                        ",effort = '" + effort + '\'' +
                        "}";
    }
}