package dao.modelo.ModPokemon;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HeldItemsItem {

    @SerializedName("item")
    private Item item;

    @SerializedName("version_details")
    private List<VersionDetailsItem> versionDetails;

    public Item getItem() {
        return item;
    }

    public List<VersionDetailsItem> getVersionDetails() {
        return versionDetails;
    }

    @Override
    public String toString() {
        return
                "HeldItemsItem{" +
                        "item = '" + item + '\'' +
                        ",version_details = '" + versionDetails + '\'' +
                        "}";
    }
}