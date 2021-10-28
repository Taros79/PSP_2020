package dao.modelo.ModMovimientos;

import com.google.gson.annotations.SerializedName;

public class EffectEntriesItem {

    @SerializedName("short_effect")
    private String shortEffect;

    @SerializedName("effect")
    private String effect;

    @SerializedName("language")
    private Language language;

    public String getShortEffect() {
        return shortEffect;
    }

    public String getEffect() {
        return effect;
    }

    public Language getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return
                "EffectEntriesItem{" +
                        "short_effect = '" + shortEffect + '\'' +
                        ",effect = '" + effect + '\'' +
                        ",language = '" + language + '\'' +
                        "}";
    }
}