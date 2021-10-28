package dao.modelo.ModMovimientos;

import com.google.gson.annotations.SerializedName;

public class NamesItem {

    @SerializedName("name")
    private String name;

    @SerializedName("language")
    private Language language;

    public String getName() {
        return name;
    }

    public Language getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return
                "NamesItem{" +
                        "name = '" + name + '\'' +
                        ",language = '" + language + '\'' +
                        "}";
    }
}