package dao.modelo.Pokemoneh;

import com.google.gson.annotations.SerializedName;

public class ResultsItem {

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
                "ResultsItem{" +
                        "name = '" + name + '\'' +
                        ",url = '" + url + '\'' +
                        "}";
    }
}