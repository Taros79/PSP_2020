package dao.modelo.ModMovimientos;

import com.google.gson.annotations.SerializedName;

public class SuperContestEffect {

    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return
                "SuperContestEffect{" +
                        "url = '" + url + '\'' +
                        "}";
    }
}