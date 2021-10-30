package dao.modeloPropio;

import com.google.gson.annotations.SerializedName;

public class SpritesPrp {

    @SerializedName("front_default")
    private String frontDefault;

    public String getFrontDefault() {
        return frontDefault;
    }
}