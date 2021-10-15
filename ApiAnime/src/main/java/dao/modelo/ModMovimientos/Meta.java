package dao.modelo.ModMovimientos;

import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("healing")
    private int healing;

    @SerializedName("min_hits")
    private Object minHits;

    @SerializedName("max_hits")
    private Object maxHits;

    @SerializedName("crit_rate")
    private int critRate;

    @SerializedName("ailment_chance")
    private int ailmentChance;

    @SerializedName("flinch_chance")
    private int flinchChance;

    @SerializedName("min_turns")
    private Object minTurns;

    @SerializedName("ailment")
    private Ailment ailment;

    @SerializedName("category")
    private Category category;

    @SerializedName("max_turns")
    private Object maxTurns;

    @SerializedName("drain")
    private int drain;

    @SerializedName("stat_chance")
    private int statChance;

    public int getHealing() {
        return healing;
    }

    public Object getMinHits() {
        return minHits;
    }

    public Object getMaxHits() {
        return maxHits;
    }

    public int getCritRate() {
        return critRate;
    }

    public int getAilmentChance() {
        return ailmentChance;
    }

    public int getFlinchChance() {
        return flinchChance;
    }

    public Object getMinTurns() {
        return minTurns;
    }

    public Ailment getAilment() {
        return ailment;
    }

    public Category getCategory() {
        return category;
    }

    public Object getMaxTurns() {
        return maxTurns;
    }

    public int getDrain() {
        return drain;
    }

    public int getStatChance() {
        return statChance;
    }

    @Override
    public String toString() {
        return
                "Meta{" +
                        "healing = '" + healing + '\'' +
                        ",min_hits = '" + minHits + '\'' +
                        ",max_hits = '" + maxHits + '\'' +
                        ",crit_rate = '" + critRate + '\'' +
                        ",ailment_chance = '" + ailmentChance + '\'' +
                        ",flinch_chance = '" + flinchChance + '\'' +
                        ",min_turns = '" + minTurns + '\'' +
                        ",ailment = '" + ailment + '\'' +
                        ",category = '" + category + '\'' +
                        ",max_turns = '" + maxTurns + '\'' +
                        ",drain = '" + drain + '\'' +
                        ",stat_chance = '" + statChance + '\'' +
                        "}";
    }
}