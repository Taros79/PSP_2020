package dao.modelo.ModMovimientos;

import com.google.gson.annotations.SerializedName;

public class ContestEffect {

    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return
                "ContestEffect{" +
                        "url = '" + url + '\'' +
                        "}";
    }
}