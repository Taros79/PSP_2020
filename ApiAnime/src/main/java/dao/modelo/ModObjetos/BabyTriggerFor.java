package dao.modelo.ModObjetos;

import com.google.gson.annotations.SerializedName;

public class BabyTriggerFor {

    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return
                "BabyTriggerFor{" +
                        "url = '" + url + '\'' +
                        "}";
    }
}