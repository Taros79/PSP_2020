package dao.modelo.ModMovimientos;

import com.google.gson.annotations.SerializedName;

public class ContestCombos {

    @SerializedName("super")
    private JsonMemberSuper jsonMemberSuper;

    @SerializedName("normal")
    private Normal normal;

    public JsonMemberSuper getJsonMemberSuper() {
        return jsonMemberSuper;
    }

    public Normal getNormal() {
        return normal;
    }

    @Override
    public String toString() {
        return
                "ContestCombos{" +
                        "super = '" + jsonMemberSuper + '\'' +
                        ",normal = '" + normal + '\'' +
                        "}";
    }
}