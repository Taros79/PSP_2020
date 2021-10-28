package dao.modelo.ModPokemon;

import com.google.gson.annotations.SerializedName;

public class GenerationIi {

    @SerializedName("gold")
    private Gold gold;

    @SerializedName("crystal")
    private Crystal crystal;

    @SerializedName("silver")
    private Silver silver;

    public Gold getGold() {
        return gold;
    }

    public Crystal getCrystal() {
        return crystal;
    }

    public Silver getSilver() {
        return silver;
    }

    @Override
    public String toString() {
        return
                "GenerationIi{" +
                        "gold = '" + gold + '\'' +
                        ",crystal = '" + crystal + '\'' +
                        ",silver = '" + silver + '\'' +
                        "}";
    }
}