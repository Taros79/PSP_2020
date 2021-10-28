package dao.modelo.ModPokemon;

import com.google.gson.annotations.SerializedName;

public class GenerationIv {

    @SerializedName("platinum")
    private Platinum platinum;

    @SerializedName("diamond-pearl")
    private DiamondPearl diamondPearl;

    @SerializedName("heartgold-soulsilver")
    private HeartgoldSoulsilver heartgoldSoulsilver;

    public Platinum getPlatinum() {
        return platinum;
    }

    public DiamondPearl getDiamondPearl() {
        return diamondPearl;
    }

    public HeartgoldSoulsilver getHeartgoldSoulsilver() {
        return heartgoldSoulsilver;
    }

    @Override
    public String toString() {
        return
                "GenerationIv{" +
                        "platinum = '" + platinum + '\'' +
                        ",diamond-pearl = '" + diamondPearl + '\'' +
                        ",heartgold-soulsilver = '" + heartgoldSoulsilver + '\'' +
                        "}";
    }
}