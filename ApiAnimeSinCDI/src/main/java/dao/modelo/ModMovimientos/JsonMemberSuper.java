package dao.modelo.ModMovimientos;

import com.google.gson.annotations.SerializedName;

public class JsonMemberSuper {

    @SerializedName("use_after")
    private Object useAfter;

    @SerializedName("use_before")
    private Object useBefore;

    public Object getUseAfter() {
        return useAfter;
    }

    public Object getUseBefore() {
        return useBefore;
    }

    @Override
    public String toString() {
        return
                "JsonMemberSuper{" +
                        "use_after = '" + useAfter + '\'' +
                        ",use_before = '" + useBefore + '\'' +
                        "}";
    }
}