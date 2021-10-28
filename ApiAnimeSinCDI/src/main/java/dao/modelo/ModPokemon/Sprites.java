package dao.modelo.ModPokemon;

import com.google.gson.annotations.SerializedName;

public class Sprites {

    @SerializedName("back_shiny_female")
    private String backShinyFemale;

    @SerializedName("back_female")
    private String backFemale;

    @SerializedName("other")
    private Other other;

    @SerializedName("back_default")
    private String backDefault;

    @SerializedName("front_shiny_female")
    private String frontShinyFemale;

    @SerializedName("front_default")
    private String frontDefault;

    @SerializedName("versions")
    private Versions versions;

    @SerializedName("front_female")
    private String frontFemale;

    @SerializedName("back_shiny")
    private String backShiny;

    @SerializedName("front_shiny")
    private String frontShiny;

    public String getBackShinyFemale() {
        return backShinyFemale;
    }

    public String getBackFemale() {
        return backFemale;
    }

    public Other getOther() {
        return other;
    }

    public String getBackDefault() {
        return backDefault;
    }

    public String getFrontShinyFemale() {
        return frontShinyFemale;
    }

    public String getFrontDefault() {
        return frontDefault;
    }

    public Versions getVersions() {
        return versions;
    }

    public String getFrontFemale() {
        return frontFemale;
    }

    public String getBackShiny() {
        return backShiny;
    }

    public String getFrontShiny() {
        return frontShiny;
    }

    @Override
    public String toString() {
        return
                "Sprites{" +
                        "back_shiny_female = '" + backShinyFemale + '\'' +
                        ",back_female = '" + backFemale + '\'' +
                        ",other = '" + other + '\'' +
                        ",back_default = '" + backDefault + '\'' +
                        ",front_shiny_female = '" + frontShinyFemale + '\'' +
                        ",front_default = '" + frontDefault + '\'' +
                        ",versions = '" + versions + '\'' +
                        ",front_female = '" + frontFemale + '\'' +
                        ",back_shiny = '" + backShiny + '\'' +
                        ",front_shiny = '" + frontShiny + '\'' +
                        "}";
    }
}