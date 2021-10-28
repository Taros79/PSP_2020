package dao.modelo.ModMovimientos;

import com.google.gson.annotations.SerializedName;

public class FlavorTextEntriesItem {

    @SerializedName("version_group")
    private VersionGroup versionGroup;

    @SerializedName("language")
    private Language language;

    @SerializedName("flavor_text")
    private String flavorText;

    public VersionGroup getVersionGroup() {
        return versionGroup;
    }

    public Language getLanguage() {
        return language;
    }

    public String getFlavorText() {
        return flavorText;
    }

    @Override
    public String toString() {
        return
                "FlavorTextEntriesItem{" +
                        "version_group = '" + versionGroup + '\'' +
                        ",language = '" + language + '\'' +
                        ",flavor_text = '" + flavorText + '\'' +
                        "}";
    }
}