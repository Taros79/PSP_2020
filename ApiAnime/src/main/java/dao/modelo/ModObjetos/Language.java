package dao.modelo.ModObjetos;

import com.google.gson.annotations.SerializedName;

public class Language {

    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return
                "Language{" +
                        "name = '" + name + '\'' +
                        ",url = '" + url + '\'' +
                        "}";
    }
}