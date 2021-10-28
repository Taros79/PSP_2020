package dao.modelo.ModPokemon;

import com.google.gson.annotations.SerializedName;

public class GenerationIii {

    @SerializedName("firered-leafgreen")
    private FireredLeafgreen fireredLeafgreen;

    @SerializedName("ruby-sapphire")
    private RubySapphire rubySapphire;

    @SerializedName("emerald")
    private Emerald emerald;

    public FireredLeafgreen getFireredLeafgreen() {
        return fireredLeafgreen;
    }

    public RubySapphire getRubySapphire() {
        return rubySapphire;
    }

    public Emerald getEmerald() {
        return emerald;
    }

    @Override
    public String toString() {
        return
                "GenerationIii{" +
                        "firered-leafgreen = '" + fireredLeafgreen + '\'' +
                        ",ruby-sapphire = '" + rubySapphire + '\'' +
                        ",emerald = '" + emerald + '\'' +
                        "}";
    }
}