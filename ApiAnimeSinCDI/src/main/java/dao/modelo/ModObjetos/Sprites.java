package dao.modelo.ModObjetos;

import com.google.gson.annotations.SerializedName;

public class Sprites {

    @SerializedName("default")
    private String jsonMemberDefault;

    public String getJsonMemberDefault() {
        return jsonMemberDefault;
    }

    @Override
    public String toString() {
        return
                "Sprites{" +
                        "default = '" + jsonMemberDefault + '\'' +
                        "}";
    }
}