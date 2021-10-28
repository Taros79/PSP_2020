package dao.modelo.ModPokemon;

import com.google.gson.annotations.SerializedName;

public class TypesItem {

    @SerializedName("slot")
    private int slot;

    @SerializedName("type")
    private Type type;

    public int getSlot() {
        return slot;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return
                "TypesItem{" +
                        "slot = '" + slot + '\'' +
                        ",type = '" + type + '\'' +
                        "}";
    }
}