package dao.modelo.ModObjetos;

import com.google.gson.annotations.SerializedName;

public class FlavorTextEntriesItem {

    @SerializedName("version_group")
    private VersionGroup versionGroup;

    @SerializedName("language")
    private Language language;

    @SerializedName("text")
    private String text;

    public VersionGroup getVersionGroup() {
        return versionGroup;
    }

    public Language getLanguage() {
        return language;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}