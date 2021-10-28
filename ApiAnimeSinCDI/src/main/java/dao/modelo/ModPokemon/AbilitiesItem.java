package dao.modelo.ModPokemon;

import com.google.gson.annotations.SerializedName;

public class AbilitiesItem {

    @SerializedName("is_hidden")
    private boolean isHidden;

    @SerializedName("slot")
    private int slot;

    @SerializedName("ability")
    private Ability ability;

    public boolean isIsHidden() {
        return isHidden;
    }

    public int getSlot() {
        return slot;
    }

    public Ability getAbility() {
        return ability;
    }

    @Override
    public String toString() {
        return
                "AbilitiesItem{" +
                        "is_hidden = '" + isHidden + '\'' +
                        ",slot = '" + slot + '\'' +
                        ",ability = '" + ability + '\'' +
                        "}";
    }
}