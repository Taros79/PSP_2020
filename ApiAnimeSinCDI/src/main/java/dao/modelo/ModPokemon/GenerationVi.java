package dao.modelo.ModPokemon;

import com.google.gson.annotations.SerializedName;

public class GenerationVi {

    @SerializedName("omegaruby-alphasapphire")
    private OmegarubyAlphasapphire omegarubyAlphasapphire;

    @SerializedName("x-y")
    private XY xY;

    public OmegarubyAlphasapphire getOmegarubyAlphasapphire() {
        return omegarubyAlphasapphire;
    }

    public XY getXY() {
        return xY;
    }

    @Override
    public String toString() {
        return
                "GenerationVi{" +
                        "omegaruby-alphasapphire = '" + omegarubyAlphasapphire + '\'' +
                        ",x-y = '" + xY + '\'' +
                        "}";
    }
}